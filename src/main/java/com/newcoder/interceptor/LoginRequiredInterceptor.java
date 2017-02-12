package com.newcoder.interceptor;

import com.newcoder.model.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Super腾 on 2017/2/11.
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginRequiredInterceptor.class);
    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("进入------------------------------");
        String url = httpServletRequest.getRequestURI();
        if(hostHolder.getUser() == null){
            //跳转让其登录并在登录完自动跳转回来传给他回调函数
            httpServletResponse.sendRedirect("/toLogin?next="+url);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
