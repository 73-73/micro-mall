package com.mall.search.controller;

import com.mall.common.PageResult;
import com.mall.search.pojo.Goods;
import com.mall.search.pojo.SearchRequest;
import com.mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;

/**
 *
 * @author pan
 * @create 2020-04-26-11:23
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 前端页面写的post，不好改了
     * 搜索商品
     * @param request
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request) {
        PageResult<Goods> result = this.searchService.search(request);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}
