package com.newcoder.controller;

import com.newcoder.model.LoginTicket;
import com.newcoder.model.User;
import com.newcoder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by Super腾 on 2017/2/10.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    /**
     * 注册
     * @param model
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model,
                           HttpServletResponse response,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String next){
        logger.info(username+" : "+password);
        Map<String,String> map = userService.register(username, password);
        try {
            //如果存在异常
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            } else {
                //登录成功
                String ticket = map.get("ticket");
                logger.info("注册的ticket是:"+ticket);
                Cookie cookie = new Cookie("ticket",ticket);
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:"+next;
            }
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return "login";
        }
    }

    /**
     * 登录
     * @param model
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Model model,
                        HttpServletResponse response,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String next){
        Map<String,String> map = userService.login(username, password);
        try {
            //如果存在异常
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            } else {
                //登录正确
                String ticket = map.get("ticket");
                logger.info("登录的ticket是");
                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                //设置当前cookie在多个应用中共享
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:"+next;
            }
        }catch (Exception e){
            logger.error("登录异常"+e.getMessage());
            return "login";
        }
    }

    /**
     * 登出
     * @param ticket
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }


}
