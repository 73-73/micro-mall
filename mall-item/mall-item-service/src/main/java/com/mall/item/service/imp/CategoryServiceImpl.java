package com.mall.item.service.imp;

import com.mall.item.mapper.CategoryMapper;
import com.mall.item.service.CategoryService;
import com.mall.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * CategoryService实现类
 * @author pan
 * @create 2020-02-02-14:44
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoriesByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }
}

