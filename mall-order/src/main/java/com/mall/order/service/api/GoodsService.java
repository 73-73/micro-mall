package com.mall.order.service.api;

import com.mall.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user-service")
public interface GoodsService extends GoodsApi {
}
