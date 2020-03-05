package com.mylawyer.community.exception;

/**
 * @Author bsnowflake04
 * Date on 2020/3/4  15:25
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("问题不见了");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
