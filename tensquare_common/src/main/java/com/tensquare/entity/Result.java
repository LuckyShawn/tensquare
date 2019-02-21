package com.tensquare.entity;

import lombok.Data;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/11 0011
 */
@Data
public class Result {
    private boolean flag;   //是否成功
    private Integer code;   // 返回码
    private String message; //返回信息
    private Object data;    // 返回数据

    public Result() {
    }

    public Result(boolean flag, Integer code, String message) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
