package com.mylawyer.community.dto;

import lombok.Data;

/**
 * @Author bsnowflake04
 * Date on 2020/4/10  21:19
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private String typeName;
}
