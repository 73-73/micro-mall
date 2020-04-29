package com.mall.web.feign;

import com.mall.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-04-28-22:03
 */
@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationApi {
}
