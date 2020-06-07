package com.mall.item.controller;

import com.mall.common.PageResult;
import com.mall.item.service.GoodsService;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.pojo.SpuDetail;
import com.mall.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品的Controller
 *
 * @author pan
 * @create 2020-04-22-19:45
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询商品结果集
     *
     * @param key      搜索条件
     * @param saleable 是否上架
     * @param page     页数
     * @param rows     行数
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuVo>> querySpuVoByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        PageResult<SpuVo> pageResult = this.goodsService.querySpuVoByPage(key, saleable, page, rows);
        if (CollectionUtils.isEmpty(pageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 新增商品
     *
     * @param spuVo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> insertGoods(@RequestBody SpuVo spuVo) {
        this.goodsService.saveGoods(spuVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 通过spuid查询这个spu的具体属性信息
     *
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * 通过spuid查询所属的具体商品sku
     *
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * 通过id查询spu相关信息
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuById(id);
        if(spu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spu);
    }

    /**
     * 更新商品信息
     *
     * @param spuVo
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuVo spuVo) {
        this.goodsService.updateGoods(spuVo);
        //NO_CONTENT:204,代表更新成功，但是不需要离开当前页面，一般用来返回PUT请求
        //CREATED:代表新增成功，一般用来返回POST请求
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 更新库存相关状态
     * @param skuId
     * @param num
     * @return
     */
    @PutMapping("goods/stock")
    public ResponseEntity<Void> updateStock(Long skuId,Integer num){
        Sku sku = this.goodsService.querySkuById(skuId);
        this.goodsService.updateStock(skuId,num);
        return ResponseEntity.ok().build();
    }

    /**
     * 通过商品id删除掉这个商品（包括子类商品）
     * @param spuId
     * @return
     */
    @DeleteMapping("spu/delete/{spuId}")
    public ResponseEntity<Void> deleteGood(@PathVariable("spuId") Long spuId) {
        this.goodsService.deleteGoodBySpuId(spuId);
        return ResponseEntity.ok().build();
    }

    /**
     * 更改商品信息
     * @param spu
     * @return
     */
    @PutMapping("good/saleable")
    public ResponseEntity<Void> changeSaleable(@RequestBody Spu spu) {
        this.goodsService.changeSaleable(spu);
        return ResponseEntity.ok().build();
    }

    /**
     * 通过sku的id查询sku的相关属性并返回
     * @param id
     * @return
     */
    @GetMapping("sku/{id}")
    public ResponseEntity<Sku> querySkuById(@PathVariable("id")Long id){
        Sku sku = this.goodsService.querySkuById(id);
        if (sku == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sku);
    }
}
