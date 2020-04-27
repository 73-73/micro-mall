package com.mall.item.service;

import com.mall.common.PageResult;
import com.mall.pojo.Brand;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-17-16:23
 */
public interface BrandService {

    /**
     * 通过id删除品牌
     *
     * @param id
     * @return > 0 ：成功  < 0 : 失败
     */
    void deleteBrandById(Long id);

    /**
     * 添加品牌
     *
     * @param brand
     * @param categories
     */
    void insertBrand(Brand brand, List<Long> categories);

    /**
     * 修改品牌,这里涉及到一个更新的关联表的问题，采取的办法是先把关联的记录全部删除再一个个加回去
     *
     * @param brand
     * @param categories
     */
    void updateBrand(Brand brand, List<Long> categories);

    /**
     * 根据查询条件查询品牌
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    /**
     * 通过分类id查询分类下的所有品牌
     * @param cid 分类id
     * @return
     */
    List<Brand> queryBrandsByCid(Long cid);

    /**
     * 通过id查询品牌信息
     * @param id
     * @return
     */
    Brand queryBrandById(Long id);
}
