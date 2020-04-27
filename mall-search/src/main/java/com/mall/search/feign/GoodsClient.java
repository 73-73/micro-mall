package com.mall.search.feign;

import com.mall.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-04-26-11:49
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
