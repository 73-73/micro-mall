package com.mall.item.service.imp;

import com.mall.item.mapper.CategoryMapper;
import com.mall.item.service.CategoryService;
import com.mall.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * CategoryService实现类
 *
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

    @Override
    public void addCategory(Category category) {
        //让其可以主键自增
        category.setId(null);
        categoryMapper.insert(category);
        if (category.getParentId() != null) {
            //如果有父类结点，则要把父类节点的isParent字段更新为true
            Category parent = new Category();
            parent.setId(category.getParentId());
            parent.setIsParent(true);
            this.categoryMapper.updateByPrimaryKeySelective(parent);
        }
    }

    @Override
    public void editCategory(Category category) {
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        this.categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryCategoryByBid(bid);
    }

    @Override
    public List<String> queryNamesByIds(List<Long> cid) {
        List<Category> categories = categoryMapper.selectByIdList(cid);
        //集合的流式编程
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }
}

