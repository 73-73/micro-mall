package com.mall.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-26-11:39
 */
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("names")
    List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);
}
