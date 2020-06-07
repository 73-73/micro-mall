package com.mall.client;

import com.mall.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-06-05-15:53
 */
@FeignClient("item-service")
public interface GoodClient extends GoodsApi {
}