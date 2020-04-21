package com.mall.item.controller;

import com.mall.common.PageResult;
import com.mall.item.service.BrandService;
import com.mall.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 品牌相关的controller
 *
 * @author pan
 * @create 2020-04-17-16:45
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    BrandService brandService;


    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByInfo(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc) {
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 根据品牌id删除品牌
     *
     * @param id
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<Void> deleteBrandById(@PathParam("id") Long id) {
        brandService.deleteBrandById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 添加品牌，并且在关联表中添加相关的映射
     * @param brand 品牌信息
     * @param categories 品牌所属的分类id
     * @return
     */
    @PostMapping()
    public ResponseEntity<Void> insertBrand(Brand brand, @RequestParam("categories") List<Long> categories) {
        brandService.insertBrand(brand, categories);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 通过id更新品牌
     * @param brand 品牌实体
     * @param cids 品牌所属的分类id
     * @return
     */
    @PutMapping()
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids") List<Long> cids) {
        brandService.updateBrand(brand,cids);
        return ResponseEntity.ok().build();
    }
}
