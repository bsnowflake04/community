package com.mylawyer.community.mapper;

import com.mylawyer.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//mapper:路由数据库
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);//mybatis自动将user类的属性放进${}

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//参数不是对象需要@param

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);
}
