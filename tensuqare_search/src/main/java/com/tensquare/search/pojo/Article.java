package com.tensquare.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @Description 文章文档实体类
 * @Author shawn
 * @create 2019/2/16 0016
 */
@Data
@Document(indexName = "tensquare_article",type = "article")
public class Article {
    @Id
    private String id;//ID

    //是否索引：看改域是否能被搜索
    //是否分词：搜索的时候是分词匹配还是整体匹配
    //是否储存：是否在页面上显示
    @Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word") //对应数据库的列

    private String title;//标题

    @Field(index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;//文章正文

    private String state;//审核状态
}
