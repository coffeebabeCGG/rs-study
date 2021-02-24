package com.cgg.redisstudy.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author cgg
 */
@Data
public class RestResult<T> {

    private String resCode;

    private long timeStamp;

    private String error;

    private T result;


    public static <T> RestResult<T> success() {
        RestResult<T> restResult = new RestResult<>();
        restResult.setResCode("200");
        restResult.setTimeStamp(System.currentTimeMillis());
        return restResult;
    }

    public static <T> RestResult<T> success(T t) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setResCode("200");
        restResult.setTimeStamp(System.currentTimeMillis());
        restResult.setResult(t);
        return restResult;
    }

    public static <T> RestResult<T> error(String msg){
        RestResult<T> restResult = new RestResult<>();
        restResult.setResCode("500");
        restResult.setTimeStamp(System.currentTimeMillis());
        restResult.setError(msg);
        return restResult;
    }

    public static <T> RestResult<T> error(String code, T t, String msg){
        RestResult<T> restResult = new RestResult<>();
        restResult.setResCode(code);
        restResult.setTimeStamp(System.currentTimeMillis());
        restResult.setResult(t);
        restResult.setError(msg);
        return restResult;
    }

}
