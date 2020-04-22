package com.mall.item.service;

import com.mall.pojo.SpecGroup;
import com.mall.pojo.SpecParam;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-22-12:09
 */
public interface SpecificationService {

    /**
     * 通过分类id查询此分类下的商品规格组信息
     * @param cid
     * @return
     */
    List<SpecGroup> queryGroupsByCid(Long cid);

    /**
     * 新增规格组
     * @param specGroup
     */
    void insertSpecGroup(SpecGroup specGroup);

    /**
     * 通过规格组id删除相应的规格组
     * @param id
     */
    void deleteGroupById(Long id);

    /**
     * 更新商品规格组信息
     * @param specGroup
     */
    void updateGroupInfo(SpecGroup specGroup);

    /**
     * 通过规格组id查询在该组下所有的规格参数
     * @param gid
     * @return
     */
    List<SpecParam> queryParamsByGid(Long gid);

    /**
     * 添加规格参数
     * @param specParam
     */
    void insertSpecParam(SpecParam specParam);

    /**
     * 通过规格参数id删除相应的规格参数记录
     * @param id
     */
    void deleteParamById(Long id);

    /**
     * 通过id更新规格参数信息
     * @param specParam
     */
    void updateParam(SpecParam specParam);
}
