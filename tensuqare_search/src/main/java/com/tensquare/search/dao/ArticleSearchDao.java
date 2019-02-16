package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/16 0016
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article,String> {

    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
