package com.mall.search.pojo;

import java.util.Map;

/**
 * 用于接收前端的请求参数
 *
 * @author pan
 * @create 2020-04-26-17:22
 */
public class SearchRequest {
    private String key;

    private Integer page;


    /**
     * 过滤字段
     */
    private Map<String, String> filter;

    /**
     * 每页大小
     */
    private static final Integer DEFAULT_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 1;

    public SearchRequest() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
