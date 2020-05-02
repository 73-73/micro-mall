package com.mall.cart.client;

import com.mall.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-05-02-10:49
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
