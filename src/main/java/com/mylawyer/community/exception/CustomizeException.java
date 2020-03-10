package com.mylawyer.community.exception;

/**
 * @Author bsnowflake04
 * Date on 2020/3/4  14:55
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(CustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
