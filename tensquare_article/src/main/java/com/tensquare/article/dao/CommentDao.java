package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/14 0014
 */
public interface CommentDao extends MongoRepository<Comment,String> {

}
