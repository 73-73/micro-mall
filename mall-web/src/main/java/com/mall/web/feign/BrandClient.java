package com.mall.web.feign;

import com.mall.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-04-28-22:03
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
