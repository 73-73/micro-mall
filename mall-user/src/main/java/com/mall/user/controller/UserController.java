package com.mall.user.controller;

import com.mall.user.pojo.User;
import com.mall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
}

