package com.mall.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.PageResult;
import com.mall.user.mapper.UserMapper;
import com.mall.user.pojo.User;
import com.mall.user.utils.CodecUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
        //查询未被冻结的用户
        record.setUsable(true);
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

    /**
     * 通过搜索条件取得用户信息
     * @param key
     * @param usable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<User> queryUserByPage(String key, Boolean usable, Integer page, Integer rows) {
        //构建查询所需的example
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //判断搜索条件是否为空，加上条件查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("username", "%" + key + "%");
        }
        //如果有选择上架下架作为搜索条件的话
        if (usable != null) {
            //设计的时候数据库是以1和0作为是否上架的区分，数据类型是int，但是接收参数是以Boolean来接收的，但是仿佛并不需要转化，mysql好像能识别
            criteria.andEqualTo("usable", usable);
        }
        //因为存在逻辑删除，所以只要搜出valid字段为true的数据即可
        //执行分页
        PageHelper.startPage(page, rows);
        List<User> users = userMapper.selectByExample(example);
        //封装成结果集
        PageInfo pageInfo = new PageInfo(users);
        return new PageResult(pageInfo.getTotal(), pageInfo.getPages(), users);
    }

    public int changeUsable(Long userId, Boolean usable) {
        User user = new User();
        user.setId(userId);
        user.setUsable(usable);
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
