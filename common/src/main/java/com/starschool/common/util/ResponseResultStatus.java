package com.starschool.common.util;

/**
 * ResponseResultStatus
 * <P>
 * 响应结果状态
 * </p>
 *
 * @author adi
 * @since 2023/2/28 10:09
 */
public enum ResponseResultStatus {
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private Integer code;
    private String message;

    ResponseResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
