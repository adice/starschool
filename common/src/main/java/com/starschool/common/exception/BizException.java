package com.starschool.common.exception;

/**
 * BizException
 * <P>
 * 异常信息封装
 * </p>
 *
 * @author adi
 * @since 2023/2/28 9:33
 */
public class BizException extends RuntimeException {
    private Integer code;
    private String message;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
        this.message = message;
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public BizException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
