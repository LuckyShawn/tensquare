package com.tensquare.qa.client.impl;

import com.tensquare.entity.Result;
import com.tensquare.entity.ResultEnum;
import com.tensquare.qa.client.BaseClient;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/25 0025
 */
@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result fingById(String id) {
        return new Result(false,ResultEnum.ERROR.getCode(),"熔断器触发了！");
    }
}
