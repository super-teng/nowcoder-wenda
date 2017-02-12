package com.newcoder.controller;

import com.newcoder.model.HostHolder;
import com.newcoder.model.Question;
import com.newcoder.service.QuestionService;
import com.newcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Super腾 on 2017/2/12.
 */
@Controller
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;


    /**
     * 添加一个问题
     * @param title
     * @param content
     * @return
     */
    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam String title,@RequestParam String content){
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            question.setUserId(hostHolder.getUser().getId());
            if(questionService.addQuestion(question)>0) {
                return WendaUtil.getJson(0);
            }
        }catch (Exception e){
            logger.error("增加题目失败："+e.getMessage());
        }
        return WendaUtil.getJson(1,"增加失败");
    }

    @RequestMapping("/question/{qid}")
    public String searchOneQuestion(Model model,@PathVariable Integer qid){
        Question question = questionService.getQuestionById(qid);
        model.addAttribute("question",question);
        return "detail";
    }
}
