package com.mylawyer.community.enums;

/**
 * @Author bsnowflake04
 * Date on 2020/4/10  18:22
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }
}
