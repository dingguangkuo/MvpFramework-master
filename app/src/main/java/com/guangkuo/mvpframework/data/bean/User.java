package com.guangkuo.mvpframework.data.bean;

/**
 * User
 * <p>
 * Created by Guangkuo on 2018/12/25.
 */
public class User {
    private int id;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
