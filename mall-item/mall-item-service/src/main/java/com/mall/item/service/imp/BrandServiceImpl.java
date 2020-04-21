package com.mall.item.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.PageResult;
import com.mall.item.mapper.BrandMapper;
import com.mall.item.service.BrandService;
import com.mall.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-17-16:23
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    @Override
    public void deleteBrandById(Long id) {
        brandMapper.deleteByPrimaryKey(id);
        //再删除与此bid相关的关联表记录
        brandMapper.deleteCategoryBrand(id);
    }

    @Override
    public void insertBrand(Brand brand, List<Long> categories) {
        brand.setId(null);
        brandMapper.insertSelective(brand);
        //再更新categories
        for (Long cid : categories) {
            this.brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    @Override
    public void updateBrand(Brand brand, List<Long> categories) {
        brandMapper.updateByPrimaryKeySelective(brand);
        //维护品牌和分类中间表
        //1、先全部删除
        brandMapper.deleteCategoryBrand(brand.getId());
        //2、再全部重建
        for (Long cid : categories) {
            this.brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        //如果key不为空，则加入查询条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        //添加分页
        PageHelper.startPage(page, rows);
        //如果sortBy不为空，则加入排序规则
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + "  " + (desc ? "desc" : "asc"));
        }
        //查询
        List<Brand> brands = this.brandMapper.selectByExample(example);
        //封装
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return new PageResult<>(brandPageInfo.getTotal(), brandPageInfo.getPageSize(), brandPageInfo.getList());
    }
}
