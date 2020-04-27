package com.mall.search.feign;

import com.mall.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-04-26-11:50
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
