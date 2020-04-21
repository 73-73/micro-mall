package com.mall.item.controller;

import com.mall.item.service.CategoryService;
import com.mall.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用于处理商品分类管理的controller
 *
 * @author pan
 * @create 2020-02-02-14:35
 */
@RestController
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

    /**
     * 根据结点信息新增分类类目
     *
     * @param category
     * @return 新增的id
     */
    @PostMapping("add")
    public ResponseEntity<Void> addCategory(Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据结点id删除分类类目
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteCategory(@RequestParam("id") Long id) {
        this.categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据结点id修改分类类目
     *
     * @param category
     * @return
     */
    @PutMapping("edit")
    public ResponseEntity<Void> editCategory(Category category) {
        this.categoryService.editCategory(category);
        return ResponseEntity.status(200).build();
    }

    /**
     * 通过品牌id查询该品牌所属的分类
     * @param bid
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable("bid") Long bid){
        List<Category> list = this.categoryService.queryByBrandId(bid);
        if(list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }
}
