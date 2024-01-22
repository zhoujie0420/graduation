package com.gra.backend.common.result;

/**
 * @author: elk
 * @create: 2024-01-19 10:18
 **/


import com.gra.backend.common.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    private boolean status;


    private Result(ResultCode resultCode, T data, boolean status) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
        this.status = status;
    }

    /**
     * 无数据成功返回
     *
     * @return
     */
    public static <T> Result success() {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null, true);
    }

    /**
     * 带数据返回
     */
    public static <T> Result success(T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, true);
    }

    /**
     * 失败
     */
    public static <T> Result fail() {
        return new Result<T>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage(), null, false);
    }

    public static <T> Result fail(String message) {
        return new Result<T>(ResultCode.FAIL.getCode(), message, null, false);
    }

    /**
     * 失败
     */
    public static <T> Result fail(T data) {
        return new Result<T>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage(), data, false);
    }


    @Override
    public String toString() {
        return "ResultUtils [code=" + code + ", message=" + message + ", data=" + data + "]";
    }
}
