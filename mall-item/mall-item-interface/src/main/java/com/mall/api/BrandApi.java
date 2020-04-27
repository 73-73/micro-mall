package com.mall.api;

import com.mall.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于提供给外部微服务Feign客户端继承调用
 * @author pan
 * @create 2020-04-26-11:30
 */
@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("{id}")
    Brand queryBrandById(@PathVariable("id") Long id);
}
