package com.mall.search.feign;

import com.mall.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-04-26-11:48
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
