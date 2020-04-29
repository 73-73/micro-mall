package com.mall.api;

import com.mall.pojo.SpecGroup;
import com.mall.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-26-11:38
 */
@RequestMapping("spec")
public interface SpecificationApi {

    /**
     * 根据相关查询条件查询规格参数
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    @GetMapping("params")
    List<SpecParam> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    /**
     * 通过分类id查询该分类下所有的规格参数组信息
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    ResponseEntity<List<SpecGroup>> querySpecGroups(@PathVariable("cid") Long cid);


    /**
     * 查询规格参数组，及组内参数
     */
    @GetMapping("{cid}")
    List<SpecGroup> querySpecsByCid(@PathVariable("cid") Long cid);

}
