package com.mall.user.service;

import com.mall.user.mapper.UserMapper;
import com.mall.user.pojo.User;
import com.mall.user.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author pan
 * @create 2020-05-01-9:32
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 用于判断用户信息是否已存在
     *
     * @param data 要校验的数据
     * @param type 要校验的数据类型：1，用户名；2，手机；
     * @return <0:参数异常  0：可用  >0: 不可用
     */
    public int checkUserInfoIsUnique(String data, Integer type) {
        if (type == null) {
            List<User> users = userMapper.selectCountByInfo(data);
            return users.size();
        } else {
            User user = new User();
            if (type == 1) {
                user.setUsername(data);
                return userMapper.selectCount(user);
            } else if (type == 2) {
                user.setPhone(data);
                return userMapper.selectCount(user);
            } else {
                return -1;
            }
        }

    }

    /**
     * 注册用户
     * @param user
     */
    public Boolean registerUser(User user) {
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        // 强制设置id为null,防止恶意指定
        user.setId(null);
        user.setCreated(new Date());
        // 添加到数据库
        boolean b = this.userMapper.insertSelective(user) == 1;
        return b;
    }

    /**
     * 查询用户
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) {
        // 查询
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            return null;
        }
        // 用户名密码都正确
        return user;
    }
}
