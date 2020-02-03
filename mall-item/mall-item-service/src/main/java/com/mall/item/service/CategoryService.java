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
}
