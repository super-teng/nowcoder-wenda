package com.newcoder.dao;

import com.newcoder.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 用户
 * Created by Super腾 on 2017/2/9.
 */
@Mapper
@Repository
public interface UserDao {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name , password , salt , head_url";
    String SEARCH_FIELDS = " id , name , password , salt , head_url";


    @Insert({"insert into", TABLE_NAME ,"(", INSERT_FIELDS , " ) " +
            "values(#{name},#{password},#{salt},#{headUrl})" })
    int addUser(User user);

    @Select({"select ",SEARCH_FIELDS,"from",TABLE_NAME,"where id  = #{id}"})
    User selectUserById(Integer id);

    @Update({"update",TABLE_NAME,"set password = #{password} where id = #{id}"})
    void updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME,"where id = #{id}"})
    void deleteById(Integer id);

    @Select({"select",SEARCH_FIELDS,"from",TABLE_NAME,"where name = #{username}"})
    User selectUserByUsername(String username);

}
