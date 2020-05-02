package com.mall.item.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.PageResult;
import com.mall.item.mapper.*;
import com.mall.item.service.CategoryService;
import com.mall.item.service.GoodsService;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.pojo.SpuDetail;
import com.mall.pojo.Stock;
import com.mall.vo.SpuVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pan
 * @create 2020-04-22-20:34
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    SpuMapper spuMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    SpuDetailMapper spuDetailMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    StockMapper stockMapper;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public PageResult<SpuVo> querySpuVoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        //构建查询所需的example
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //判断搜索条件是否为空，加上条件查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        //如果有选择上架下架作为搜索条件的话
        if (saleable != null) {
            //设计的时候数据库是以1和0作为是否上架的区分，数据类型是int，但是接收参数是以Boolean来接收的，但是仿佛并不需要转化，mysql好像能识别
            //("saleable", saleable);和("saleable", saleable ? 1 : 0);的效果是一样的，都能正确查询出结果
            criteria.andEqualTo("saleable", saleable);
        }
        //因为存在逻辑删除，所以只要搜出valid字段为true的数据即可
        criteria.andEqualTo("valid", true);
        //执行分页
        PageHelper.startPage(page, rows);
        List<Spu> spus = spuMapper.selectByExample(example);
        //封装成结果集
        PageInfo pageInfo = new PageInfo(spus);

        //因为spus中就只有cid和bid，前端页面显示需要的是名字而不是id，所以把Spu转化成SpuVo对象，通过cid和bid进行分类名称和品牌名称的查询

        List<Object> spuVos = spus.stream().map((spu) -> {
            SpuVo spuVo = new SpuVo();
            //设置品牌名称
            spuVo.setBname(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
            //查询出三个分类id对应的类目名称，以/分隔拼接到一起
            List<String> names = categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuVo.setCname(StringUtils.join(names, "/"));
            //再把其他属性都迁移到SpuVo上
            BeanUtils.copyProperties(spu, spuVo);
            return spuVo;
        }).collect(Collectors.toList());
        return new PageResult(pageInfo.getTotal(), pageInfo.getPages(), spuVos);
    }

    @Override
    public void saveGoods(SpuVo spuVo) {
        //先对这个参数设置默认的值
        //避免恶意注入
        spuVo.setId(null);
        spuVo.setSaleable(true);
        spuVo.setValid(true);
        spuVo.setCreateTime(new Date());
        spuVo.setLastUpdateTime(spuVo.getCreateTime());
        this.spuMapper.insertSelective(spuVo);

        //在spuDetail表中插入数据
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);

        saveSkuAndStock(spuVo);
        //像消息队列发送消息，让其他模块同步
        sendMessage(spuVo.getId(),"insert");
    }

    private void saveSkuAndStock(SpuVo spuVo) {
        spuVo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setSpuId(spuVo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);

            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        //对这些sku添加库存属性
        skus.forEach(s -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(s.getId());
            s.setStock(stock.getStock());
        });
        return skus;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGoods(SpuVo spuvo) {
        // 查询以前sku
        List<Sku> skus = this.querySkusBySpuId(spuvo.getId());
        // 如果以前存在，则删除
        if (!CollectionUtils.isEmpty(skus)) {
            List<Long> ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
            // 删除以前库存
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId", ids);
            this.stockMapper.deleteByExample(example);

            // 删除以前的sku
            Sku record = new Sku();
            record.setSpuId(spuvo.getId());
            this.skuMapper.delete(record);
        }
        // 新增sku和库存
        saveSkuAndStock(spuvo);

        // 更新spu
        spuvo.setLastUpdateTime(new Date());
        spuvo.setCreateTime(null);
        spuvo.setValid(null);
        spuvo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuvo);
        // 更新spu详情
        this.spuDetailMapper.updateByPrimaryKeySelective(spuvo.getSpuDetail());
        sendMessage(spuvo.getId(),"update");
    }

    @Override
    public void deleteGoodBySpuId(Long spuId) {
        Spu spu = new Spu();
        spu.setId(spuId);
        //修改数据库中的valid字段，使其被删除
        spu.setValid(false);
        //更新修改时间
        spu.setLastUpdateTime(new Date());
        this.spuMapper.updateByPrimaryKeySelective(spu);
        sendMessage(spuId,"delete");
    }

    @Override
    public void changeSaleable(Spu spu) {
        spu.setLastUpdateTime(new Date());
        this.spuMapper.updateByPrimaryKeySelective(spu);
        sendMessage(spu.getId(),"update");
    }

    @Override
    public Spu querySpuById(Long id) {
        return this.spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过mq发送消息，让其他模块响应这个变更，更新其数据库
     *
     * @param id
     * @param type
     */
    private void sendMessage(Long id, String type) {
        // 发送消息
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            System.err.println(type + "商品消息发送异常，商品id：" + id + "   异常信息：" + e.getMessage());
        }
    }

    @Override
    public Sku querySkuById(Long id) {
        return this.skuMapper.selectByPrimaryKey(id);
    }
}
