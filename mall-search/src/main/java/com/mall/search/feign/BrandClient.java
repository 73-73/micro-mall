package com.mall.search.feign;

import com.mall.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-04-26-11:45
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
