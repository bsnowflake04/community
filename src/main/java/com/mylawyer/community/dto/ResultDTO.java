package com.mylawyer.community.dto;

import com.mylawyer.community.exception.CustomizeErrorCode;
import com.mylawyer.community.exception.CustomizeException;
import lombok.Data;

/**
 * @Author bsnowflake04
 * Date on 2020/3/9  9:59
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e){
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("success");
        return resultDTO;
    }
    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("success");
        resultDTO.setData(t);
        return resultDTO;
    }
}
