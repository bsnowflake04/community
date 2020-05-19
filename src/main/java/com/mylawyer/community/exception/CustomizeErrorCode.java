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
    COMMENT_IS_EMPTY(2007,"评论为空"),
    READ_NOTIFICATION_FAIL(2008,"通知人不匹配"),
    NOTIFICATION_NOT_FOUND(2009,"通知不存在"),
    USER_OR_QUESTION_NOT_EXIST(2010,"用户不存在"),
    OPERATION_FAILED(2011,"操作失败"),
    UPLOADING_FAILED_CAUSE_NAME(2012,"上传失败：文件名错误"),
    UPLOADING_FAILED(2013,"文件上传失败"),

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
