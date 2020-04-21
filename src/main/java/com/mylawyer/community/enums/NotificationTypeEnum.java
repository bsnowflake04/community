package com.mylawyer.community.enums;

/**
 * @Author bsnowflake04
 * Date on 2020/4/10  17:51
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(0, "回复了问题"),
    REPLY_COMMENT(1, "回复了评论");
    private Integer type;
    private String name;

    NotificationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(Integer type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
