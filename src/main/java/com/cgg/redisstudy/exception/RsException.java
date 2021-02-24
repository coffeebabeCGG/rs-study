package com.cgg.redisstudy.exception;

public class RsException extends RuntimeException {

    private int code;

    public RsException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public RsException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
    }

}
