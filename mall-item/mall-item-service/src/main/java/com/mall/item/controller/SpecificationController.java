package com.mall.item.controller;

import com.mall.item.service.SpecificationService;
import com.mall.pojo.SpecGroup;
import com.mall.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 与规格参数相关的controller，提供规格组和规格参数的相关增删改查接口
 * @author pan
 * @create 2020-04-22-12:05
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    SpecificationService specificationService;

    /**
     * 通过分类id查询此分类下的规格组信息
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupInfo(@PathVariable("cid") Long cid){
        List<SpecGroup> specGroups = specificationService.queryGroupsByCid(cid);
        if(!CollectionUtils.isEmpty(specGroups)){
            return ResponseEntity.ok(specGroups);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 新增规格组
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<List<SpecGroup>> insertGroupInfo(@RequestBody SpecGroup specGroup){
        specificationService.insertSpecGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 通过规格组id删除相应记录
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<List<SpecGroup>> deleteGroupInfo(@PathVariable("id") Long id){
        specificationService.deleteGroupById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新规格组
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateGroupInfo(@RequestBody SpecGroup specGroup){
        specificationService.updateGroupInfo(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 通过规格组id查询该组下的规格参数，比如通过主体组id查询组下的参数信息，如品牌，型号，上市年份
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamsInfo(@PathParam("gid") Long gid){
        List<SpecParam> specParams = specificationService.queryParamsByGid(gid);
        if(!CollectionUtils.isEmpty(specParams)){
            return ResponseEntity.ok(specParams);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 添加规格参数记录
     * @param specParam 规格参数实体
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> insertParam(@RequestBody SpecParam specParam){
        specificationService.insertSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * 通过id删除规格参数
     * @param id 规格参数id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParamById(@PathVariable("id") Long id){
        specificationService.deleteParamById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新规格参数
     * @param specParam 待修改的规格参数实体
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam){
        specificationService.updateParam(specParam);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }


}
