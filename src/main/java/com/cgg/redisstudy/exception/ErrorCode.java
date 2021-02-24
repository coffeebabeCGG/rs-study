package com.cgg.redisstudy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    HTTP_REQ_EXCEPTION(1000, "请求异常");

    private int code;

    private String msg;

}
