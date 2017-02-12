package com.newcoder.controller;

import com.newcoder.model.News;
import com.newcoder.model.Question;
import com.newcoder.model.ViesObject;
import com.newcoder.service.NewsService;
import com.newcoder.service.QuestionService;
import com.newcoder.service.UserService;
import com.sun.javafx.sg.prism.NGShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Super腾 on 2017/2/9.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/","/index"})
    public String toIndex(ModelMap modelMap){
        List<Question> list = questionService.getLatestQuestions(0,0,10);
        List<ViesObject> vo = new ArrayList<>();
        for(Question question : list ){
            ViesObject vv = new ViesObject();
            vv.set("question",question);
            vv.set("user",userService.getUser(question.getUserId()));
            vo.add(vv);
        }

        modelMap.addAttribute("vos",vo);
        return "index";
    }

    @RequestMapping("/user/{id}")
    public String toUserNews(ModelMap modelMap,@PathVariable Integer id){
        logger.info("查看 "+id+" 的资讯");
        List<Question> list = questionService.getLatestQuestions(id,0,10);
        List<ViesObject> vo = new ArrayList<>();
        for(Question question : list ){
            ViesObject vv = new ViesObject();
            vv.set("question",question);
            vv.set("user",userService.getUser(question.getUserId()));
            vo.add(vv);
        }
        modelMap.addAttribute("vos",vo);
        return "index"; 
    }

    @RequestMapping("/toLogin")
    public String toLoginIn(Model model,@RequestParam(required = false) String next){
        model.addAttribute("next",next);
        return "login";
    }
}
