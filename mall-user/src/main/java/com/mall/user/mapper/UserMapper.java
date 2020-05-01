package com.mall.user.mapper;

import com.mall.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author pan
 * @create 2020-05-01-9:21
 */
public interface UserMapper extends Mapper<User> {

    /**
     * 通过条件查找符合要求的数据集
     * @param data
     * @return
     */
    @Select("SELECT * FROM `tb_user` WHERE username =#{data} or phone =#{data}")
    List<User> selectCountByInfo(@Param("data") String data);
}
