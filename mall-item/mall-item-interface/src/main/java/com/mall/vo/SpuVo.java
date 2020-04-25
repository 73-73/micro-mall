package com.mall.vo;

import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.pojo.SpuDetail;

import java.util.List;

/**
 * 商品实体的拓展用于接收前端商品的信息
 *
 * @author pan
 * @create 2020-04-22-20:31
 */
public class SpuVo extends Spu {
    /**
     * 分类名称
     */
    private String cname;
    /**
     * 品牌名称
     */
    private String bname;

    /**
     * 4.23-新增用于接受前端商品新增的商品详情
     */
    SpuDetail spuDetail;
    /**
     * 4.23-新增属性用于接收前端商品的sku列表
     */
    List<Sku> skus;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }
}
