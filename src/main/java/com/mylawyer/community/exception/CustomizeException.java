package com.mylawyer.community.exception;

/**
 * @Author bsnowflake04
 * Date on 2020/3/4  14:55
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }
    public CustomizeException(CustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
