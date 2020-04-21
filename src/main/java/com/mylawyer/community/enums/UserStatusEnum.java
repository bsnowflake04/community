package com.mylawyer.community.enums;

/**
 * @Author bsnowflake04
 * Date on 2020/4/10  17:51
 */
public enum UserStatusEnum {
    USER(0, "用户"),
    ADMIN(1, "管理员");
    private Integer status;
    private String name;



    public static String nameOfStatus(Integer status) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getStatus() == status) {
                return userStatusEnum.getName();
            }
        }
        return null;
    }

    UserStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
