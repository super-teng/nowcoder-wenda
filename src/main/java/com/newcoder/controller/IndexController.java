package com.newcoder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页控制层
 *
 * Created by Super腾 on 2017/2/8.
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    /**
     * 通过这两个映射路径均可以访问到主页
     * @return
     */
//    @RequestMapping(path = {"/","/index"})
//    @ResponseBody
//    public String toIndex(){
//        return "hello world";
//    }

    /**
     * 跳转到用户登录页
     * @param userId
     * @return
     */
    @RequestMapping("/index/{userId}")
    public String toPage(@PathVariable("userId") Integer userId, Model model){
        if(userId == 1){
            model.addAttribute("username","yt");
            model.addAttribute("userId",userId);
            List<String> list = new ArrayList<String>();
            list.add("hello");
            list.add("world");
            list.add("it's");
            list.add("me");
            model.addAttribute("list",list);
            return "successful";
        }else{
            return "failure";
        }
    }

    /**
     * 跳转到家模板中的类型
     * @return
     */
    @RequestMapping("/home")
    public String toHome(){
        System.out.println("进入啦");
        return "home";
    }

    /**
     * 跳至其他页面的测试方法
     * @return
     */
    @RequestMapping("/test")
    public String toTest(){
        System.out.println("进入");
        return "test";
    }



}
