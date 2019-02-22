package com.tensquare.qa.client;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/22 0022
 */
@FeignClient(name = "tensquare-base")
public interface BaseClient {

    /**
     * 根据ID查询标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
    Result fingById(@PathVariable("id") String id);
}
