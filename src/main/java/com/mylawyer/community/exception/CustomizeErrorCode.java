package com.mylawyer.community.exception;

/**
 * @Author bsnowflake04
 * Date on 2020/3/4  15:25
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或回复"),
    NO_LOGIN(2003,"未登录"),
    SYS_ERROR(2004,"服务端异常"),
    COMMENT_TYPE_ERROR(2005,"评论类型错误"),
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    ;

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
