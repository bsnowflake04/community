package com.mylawyer.community.mapper;

import com.mylawyer.community.model.User;
import org.apache.ibatis.annotations.*;

//mapper:路由数据库
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);//mybatis自动将user类的属性放进${}

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//参数不是对象需要@param

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set name = #{name}, token = #{token}, avatar_url = #{avatarUrl}, gmt_modified = #{gmtModified} where id = #{id}")
    void update(User user);
}
