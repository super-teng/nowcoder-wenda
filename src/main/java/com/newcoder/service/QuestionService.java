package com.newcoder.service;

import com.newcoder.dao.QuestionDAO;
import com.newcoder.model.HostHolder;
import com.newcoder.model.Question;
import com.newcoder.util.WendaUtil;
import com.sun.deploy.net.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Super腾 on 2016/7/15.
 */
@Service
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    SensitiveService sensitiveService;


    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    /**
     * 添加问题
     * @param question
     * @return
     */
    public Integer addQuestion(Question question){
        //当正确插入的话会返回大于0
        //进行问题内容的过滤
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    /**
     * 通过ID来获取问题
     * @param qid
     * @return
     */
    public Question getQuestionById(Integer qid){
        return questionDAO.getById(qid);
    }

}
