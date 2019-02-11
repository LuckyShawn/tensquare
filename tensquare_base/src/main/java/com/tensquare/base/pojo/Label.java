package com.tensquare.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description 标签实体类
 * @Author shawn
 * @create 2019/2/11 0011
 */
@Entity
@Table(name = "tb_label")
@Data
public class Label implements Serializable {
    @Id
    private String id;//
    private String labelname;//标签名称
    private String state;//状态
    private Long count;//使用数量
    private Long fans;//关注数
    private String recommend;//是否推荐

}
