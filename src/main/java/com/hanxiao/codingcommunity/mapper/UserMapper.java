package com.hanxiao.codingcommunity.mapper;

import com.hanxiao.codingcommunity.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where token = #{token}")
    User selectByToken(@Param("token") String token);

    @Insert("insert into user (account_id, name, token, gmt_create, gmt_modified, avatar_url)" +
            "values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") Integer id);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl}" +
            "where id = #{id}")
    void update(User user1);

    @Select("select * from user where account_id = #{accountId}")
    User selectByAccountId(String accountId);
}
