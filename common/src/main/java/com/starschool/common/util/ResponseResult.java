package com.starschool.common.util;

import java.io.Serial;
import java.io.Serializable;

/**
 * ResponseResult
 * <P>
 * 响应结果的封装类
 * </p>
 *
 * @author adi
 * @since 2023/2/28 9:53
 */
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5700614521005465381L;

    private Integer code;
    private String message;
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(ResponseResultStatus responseResultStatus, T data) {
        this.code = responseResultStatus.getCode();
        this.message = responseResultStatus.getMessage();
        this.data = data;
    }

    public static ResponseResult<Void> success() {
        return new ResponseResult<>(ResponseResultStatus.SUCCESS, null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResponseResultStatus.SUCCESS, data);
    }

    public static ResponseResult<Void> failure(Integer code, String message) {
        return new ResponseResult<>(code, message);
    }

    public static <T> ResponseResult<T> failure(Integer code, String message, T data) {
        return new ResponseResult<>(code, message, data);
    }

    public static ResponseResult<Void> failure(ResponseResultStatus responseResultStatus) {
        return new ResponseResult<>(responseResultStatus, null);
    }

    public static <T> ResponseResult<T> failure(ResponseResultStatus responseResultStatus, T data) {
        return new ResponseResult<>(responseResultStatus, data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
