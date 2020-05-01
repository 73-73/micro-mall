package com.mall.auth.client;

import com.mall.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author pan
 * @create 2020-05-01-12:00
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
