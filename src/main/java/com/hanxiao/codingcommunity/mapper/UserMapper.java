package com.hanxiao.codingcommunity.mapper;

import com.hanxiao.codingcommunity.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where token = #{token}")
    User selectByToken(@Param("token") String token);

    @Insert("insert into user (account_id, name, token, gmt_create, gmt_modified, avatar_url)" +
            "values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") Integer id);

}
