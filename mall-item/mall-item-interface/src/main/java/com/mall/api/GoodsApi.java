package com.mall.api;

import com.mall.common.PageResult;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.pojo.SpuDetail;
import com.mall.vo.SpuVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-26-11:38
 */
public interface GoodsApi {

    /**
     * 分页查询商品
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("spu/page")
    PageResult<SpuVo> querySpuVoByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**
     * 根据spu商品id查询详情
     * @param id
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    SpuDetail querySpuDetailById(@PathVariable("spuId") Long id);

    /**
     * 根据spu的id查询sku
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long id);

    /**
     * 根据spu的id查询spu
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    Spu querySpuById(@PathVariable("id") Long id);

    /**
     * 根据skuid查询sku具体信息
     * @param id
     * @return
     */
    @GetMapping("sku/{id}")
    Sku querySkuById(@PathVariable("id")Long id);

    /**
     * 更新库存
     * @param skuId
     * @param num
     * @return
     */
    @PutMapping("goods/stock")
    void updateStock(@RequestParam("skuId") Long skuId,@RequestParam("num")  Integer num);
}
