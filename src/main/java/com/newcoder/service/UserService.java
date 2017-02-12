package com.newcoder.service;

import com.newcoder.WendaApplication;
import com.newcoder.dao.LoginTicketDao;
import com.newcoder.dao.NewsDao;
import com.newcoder.dao.UserDao;
import com.newcoder.model.LoginTicket;
import com.newcoder.model.User;
import com.newcoder.util.WendaUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Super腾 on 2017/2/9.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    public User getUser(Integer id){
        return userDao.selectUserById(id);
    }


    public Map<String,String> register(String username,String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            map.put("msg","用户名或密码不能为空");
            return map;
        }
        if(userDao.selectUserByUsername(username) != null){
            map.put("msg","用户名或密码已经存在");
            return map;
        }
        //插入用户数据并对密码进行md5加密
        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,String> login(String username,String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            map.put("msg","用户名或密码不能为空");
            return map;
        }
        User user = userDao.selectUserByUsername(username);
        if(user == null){
            map.put("msg","用户名或密码错误");
            return map;
        }
        //登录成功
        if(user.getPassword().equals(WendaUtil.MD5(password+user.getSalt()))){
            //获取到ticket返回到前台
            map.put("ticket",addLoginTicket(user.getId()));
            return map;
        }else{
            map.put("msg","用户名或密码错误");
            return map;
        }
    }

    public String addLoginTicket(Integer userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        //设置token的过期时间为10天
        Date date = new Date();
        //两小时免登陆
        date.setTime(100*3600*24 + date.getTime());
        loginTicket.setExpired(date);
        loginTicketDao.insertLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDao.updateLoginTicket(ticket);
    }

}
