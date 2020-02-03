package com.mall.item.controller;

import com.mall.item.service.CategoryService;
import com.mall.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用于处理商品分类管理的controller
 *
 * @author pan
 * @create 2020-02-02-14:35
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 根据父节点id查询子节点下的分类类目
     *
     * @param pid 父节点id
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        if (pid == null || pid < 0) {
            //参数无效，返回badRequest：400
            return ResponseEntity.badRequest().build();
        }
        List<Category> categoryList = categoryService.queryCategoriesByPid(pid);
        if (CollectionUtils.isEmpty(categoryList)) {
            //集合为空，返回notFound：404
            return ResponseEntity.notFound().build();
        }
        //成功：200
        return ResponseEntity.ok(categoryList);
    }
}
