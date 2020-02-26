package com.mylawyer.community.model;

import lombok.Data;

//model:数据库传输的JavaBean
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
