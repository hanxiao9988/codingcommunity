package com.hanxiao.codingcommunity.dto;

import com.hanxiao.codingcommunity.exception.CustomizeErrorCode;
import com.hanxiao.codingcommunity.exception.CustomizeException;
import lombok.Data;

import javax.xml.transform.Result;

@Data
public class ResultDTO {
    Integer code;
    String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

}
