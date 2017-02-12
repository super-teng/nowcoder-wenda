package com.newcoder.interceptor;

import com.newcoder.dao.LoginTicketDao;
import com.newcoder.dao.UserDao;
import com.newcoder.model.HostHolder;
import com.newcoder.model.LoginTicket;
import com.newcoder.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Super腾 on 2017/2/11.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(PassportInterceptor.class);

    @Autowired
    LoginTicketDao loginTicketDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //请求之前先验证当前token是否是一个正确的token，如果正确通过token来找到所属用户
        String ticket = new String();
        for(Cookie cookie : httpServletRequest.getCookies()){
            if(cookie.getName().equals("ticket")){
                ticket = cookie.getValue();
                break;
            }
        }
        logger.info("ticket: "+ticket);
        //如果ticket存在
        if(ticket != null){
            //对其进行查找
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            //判断当前的ticket是否合理
            if(loginTicket == null || loginTicket.getStatus()!=0 || loginTicket.getExpired().before(new Date())){
                //不做任何处理对当前请求直接放行，并且用户的信息没有存储起来
                return true;
            }else{
                User user = userDao.selectUserById(loginTicket.getUserId());
                hostHolder.setuser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
