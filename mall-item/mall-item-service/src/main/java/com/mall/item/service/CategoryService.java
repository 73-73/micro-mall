package com.mall.item.service;

import com.mall.pojo.Category;

import java.util.List;

/**
 * 类目表service接口
 * @author pan
 * @create 2020-02-02-14:42
 */
public interface CategoryService {
    /**
     * 根据父类id查找子类目
     * @param pid
     * @return
     */
    List<Category> queryCategoriesByPid(Long pid);

    /**
     * 添加类目
     * @param category
     */
    void addCategory(Category category);

    /**
     * 修改类目
     * @param category 待修改实体对象
     */
    void editCategory(Category category);

    /**
     * 通过id删除类目
     * @param id 类目id
     */
    void deleteCategoryById(Long id);
}
