package com.mylawyer.community.dto;

import lombok.Data;

//DTO:类类，类与网络之间传输的JavaBean
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;

}
