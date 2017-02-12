package com.newcoder.model;

import org.springframework.stereotype.Component;

/**
 * 存储用户
 * Created by Super腾 on 2017/2/11.
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setuser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }

}
