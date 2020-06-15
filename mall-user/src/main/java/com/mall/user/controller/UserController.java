package com.mall.user.controller;

import com.mall.common.PageResult;
import com.mall.user.pojo.User;
import com.mall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pan
 * @create 2020-05-01-9:19
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用于校验用户注册时用户名是否已存在
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserInfoIsUnique(@PathVariable("data") String data,@PathVariable("type") Integer type){
        int result = userService.checkUserInfoIsUnique(data,type);
        if(result == 0){
            return ResponseEntity.ok(true);
        }
        else if(result >0){
            return ResponseEntity.ok(false);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> registerUser(@Valid User user){
        Boolean boo = this.userService.registerUser(user);
        if (boo == null || !boo) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        User user = this.userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 通过参数获取用户数据信息
     * @param key
     * @param usable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<User>> userList(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "usable", required = false) Boolean usable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ){
        PageResult<User> pageResult = this.userService.queryUserByPage(key, usable, page, rows);
        if (CollectionUtils.isEmpty(pageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }


    /**
     * 改变用户状态
     * @param id
     * @param usable
     * @return
     */
    @PutMapping("usable")
    public ResponseEntity<Void> changeUsable(Long id,Boolean usable){
        if(this.userService.changeUsable(id,usable)>0){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

