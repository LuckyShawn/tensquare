package com.tensquare.article.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @Description 文章评论（mongoDB）
 * @Author shawn
 * @create 2019/2/14 0014
 */
@Data
public class Comment {
    @Id
    private String _id;
    private String articleid;
    private String content;
    private String userid;
    private String parentid;
    private Date publishdate;

}
