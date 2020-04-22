package com.mall.item.service.imp;

import com.mall.item.mapper.SpecGroupMapper;
import com.mall.item.mapper.SpecParamMapper;
import com.mall.item.service.SpecificationService;
import com.mall.pojo.SpecGroup;
import com.mall.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pan
 * @create 2020-04-22-12:09
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.groupMapper.select(specGroup);
    }

    @Override
    public void insertSpecGroup(SpecGroup specGroup) {
        this.groupMapper.insert(specGroup);
    }

    @Override
    public void deleteGroupById(Long id) {
        //删除规格组中相关的记录
        this.groupMapper.deleteByPrimaryKey(id);
        //删除规格参数组中与此规格组id相关的记录
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(id);
        this.paramMapper.delete(specParam);
    }

    @Override
    public void updateGroupInfo(SpecGroup specGroup) {
        this.groupMapper.updateByPrimaryKeySelective(specGroup);
    }

    @Override
    public List<SpecParam> queryParamsByGid(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        return paramMapper.select(specParam);
    }

    @Override
    public void insertSpecParam(SpecParam specParam) {
        paramMapper.insertSelective(specParam);
    }

    @Override
    public void deleteParamById(Long id) {
        paramMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateParam(SpecParam specParam) {
        paramMapper.updateByPrimaryKeySelective(specParam);
    }
}
