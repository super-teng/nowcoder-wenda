package com.newcoder.dao;

import com.newcoder.model.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Min;

/**
 * Created by Super腾 on 2017/2/11.
 */
@Mapper
@Repository
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELD = "user_id, ticket , expired , status";
    String SELECT_FIELD = "id , user_id, ticket , expired , status";

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{userId},#{ticket},#{expired},#{status});"})
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where ticket = #{ticket};"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update",TABLE_NAME,"set status = 1 where ticket = #{ticket}"})
    int updateLoginTicket(String ticket);

}
