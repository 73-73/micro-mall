package com.mall.item.service;

import com.mall.common.PageResult;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.pojo.SpuDetail;
import com.mall.vo.SpuVo;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-22-20:34
 */
public interface GoodsService {

    /**
     * 通过查询条件搜索商品信息
     * @param key 搜索关键字
     * @param saleable 是否上架
     * @param page 第几页
     * @param rows 行数
     * @return
     */
    PageResult<SpuVo> querySpuVoByPage(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 新增商品信息
     * @param spuVo
     */
    void saveGoods(SpuVo spuVo);

    /**
     * 通过spu的id查找关于这个spu的细节属性，如商品描述，售后服务等比较大的描述信息
     * @param spuId
     * @return
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * 通过spuId查询所属的Sku信息
     * @param spuId
     * @return
     */
    List<Sku> querySkusBySpuId(Long spuId);

    /**
     * 更新商品信息
     * @param spuVo
     * @return
     */
    void updateGoods(SpuVo spuVo);

    /**
     * 通过spu假删商品记录(可恢复)
     * @param spuId
     */
    void deleteGoodBySpuId(Long spuId);

    /**
     * 上下架
     * @param spu
     */
    void changeSaleable(Spu spu);
}
