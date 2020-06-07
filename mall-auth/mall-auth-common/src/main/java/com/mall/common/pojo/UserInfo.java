package com.mall.common.pojo;

/**
 * 载荷对象
 */
public class UserInfo {

    private Long id;

    private String username;

    private Integer permission;

    public UserInfo() {
    }

    public UserInfo(Long id, String username, Integer permission) {
        this.id = id;
        this.username = username;
        this.permission = permission;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}