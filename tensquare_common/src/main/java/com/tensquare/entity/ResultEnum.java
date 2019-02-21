package com.tensquare.entity;

import lombok.Getter;

/**
 * @Description 返回信息状态枚举
 * @Author shawn
 * @create 2019/2/20 0020
 */
@Getter
public enum ResultEnum {
    OK(20000,"success"),
    ERROR(20001,"失败"),
    LOGIN_ERROR(20002,"用户名或密码错误"),
    ACCESS_ERROR(20003,"权限不足"),
    REMOTE_ERROR(20004,"远程调用失败"),
    REP_ERROR(20005,"重复操作"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
