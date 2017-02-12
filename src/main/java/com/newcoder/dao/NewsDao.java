package com.newcoder.dao;

import com.newcoder.model.News;
import com.newcoder.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Super腾 on 2017/2/9.
 */
@Mapper
@Repository
public interface NewsDao {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = "title , link , image , like_count , comment_count , create_date, user_id";
    String SEARCH_FIELDS = " id , title , link , image , like_count , comment_count , create_date, user_id";

    @Insert({"insert into", TABLE_NAME ,"(", INSERT_FIELDS , " ) " +
            "values(#{title},#{link},#{image},#{likeCount},#{commentCount},#{createDate},#{userId})" })
    int addNews(News news);


    List<News> selectByUserIdAndOffset(@Param("userId") Integer userId,@Param("offset") Integer offset,@Param("limit") Integer limit);

}
