package com.newcoder.service;

import com.newcoder.dao.NewsDao;
import com.newcoder.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Super腾 on 2017/2/9.
 */
@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    NewsDao newsDao;

    public List<News> searchByList(Integer userId,Integer offset,Integer limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }

}
