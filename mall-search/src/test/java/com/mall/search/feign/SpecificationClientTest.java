package com.mall.search.feign;

import com.mall.common.PageResult;
import com.mall.pojo.Spu;
import com.mall.search.SearchApplication;
import com.mall.search.pojo.Goods;
import com.mall.search.repository.GoodsRepository;
import com.mall.search.service.SearchService;
import com.mall.vo.SpuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author pan
 * @create 2020-04-26-11:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SpecificationClientTest {
    @Autowired
    BrandClient brandClient;

    @Autowired
    CategoryClient categoryClient;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private SearchService searchService;

    @Autowired
    GoodsClient goodsClient;


    @Test
    public void myTest1() throws IOException {
        //获取spu集合
        PageResult<SpuVo> spuVoPageResult = goodsClient.querySpuVoByPage("", true, 1, 5);
        SpuVo spuVo = spuVoPageResult.getItems().get(1);
        //获取封装后的goods集合
        Goods goods = searchService.buildGoods(spuVo);
        System.out.println(goods.toString());
    }
    @Test
    public void myTest2(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }
    @Test
    public void createIndex(){
        // 创建索引
        this.template.createIndex(Goods.class);
        // 配置映射
        this.template.putMapping(Goods.class);
        Integer page = 1;
        Integer rows = 100;

        do {
            // 分批查询spuBo
            PageResult<SpuVo> pageResult = this.goodsClient.querySpuVoByPage(null, true, page, rows);
            // 遍历spubo集合转化为List<Goods>
            List<Goods> goodsList = pageResult.getItems().stream().map(spuBo -> {
                try {
                    return this.searchService.buildGoods((Spu) spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            this.goodsRepository.saveAll(goodsList);

            // 获取当前页的数据条数，如果是最后一页，没有100条
            rows = pageResult.getItems().size();
            // 每次循环页码加1
            page++;
        } while (rows == 100);
    }
}