package com.cgg.redisstudy.exception;

import com.cgg.redisstudy.dto.RestResult;
import com.cgg.redisstudy.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @author cgg
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
            MaxUploadSizeExceededException.class,
            MultipartException.class,
            //业务异常
            RsException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object clientException(HttpServletRequest request, Exception e) {
        log.error("{}请求{}异常", IpUtil.getIpAddress(request), request.getRequestURI(), e);
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Object clientException2(HttpServletRequest request, Exception e) {
        log.error("{}请求{}异常", IpUtil.getIpAddress(request), request.getRequestURI(), e);
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object serverException(HttpServletRequest request, Exception e) {
        log.error("{} 请求 {} 异常", IpUtil.getIpAddress(request), request.getRequestURI(), e);
        if (e instanceof NullPointerException) {
            return RestResult.error("NPE");
        }
        return RestResult.error(e.getMessage() == null ? "服务内部异常" : e.getMessage());
    }


}
