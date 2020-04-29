package com.mall.web.feign;

import com.mall.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-04-28-19:01
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
