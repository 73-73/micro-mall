package com.mall.search.pojo;

import com.mall.common.PageResult;
import com.mall.pojo.Brand;
import com.mall.pojo.Category;

import java.util.List;
import java.util.Map;

/**
 * 用于前端结果返回，比PageResult包含了更多的信息，如品牌集合，分类集合，规格参数集合等
 *
 * @author pan
 * @create 2020-04-27-17:10
 */
public class SearchResult<Goods> extends PageResult<Goods> {

    /**
     * 分类的集合
     */
    private List<Category> categories;

    /**
     * 品牌的集合
     */
    private List<Brand> brands;

    /**
     * 规格参数的过滤条件
     */
    private List<Map<String, Object>> specs;


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(List<Goods> items, Long total, Integer totalPage,List<Category> categories, List<Brand> brands) {
        super(total,totalPage,items);
        this.categories = categories;
        this.brands = brands;
    }

    public SearchResult(Long total, Long totalPage, List<com.mall.search.pojo.Goods> item, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, item);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}