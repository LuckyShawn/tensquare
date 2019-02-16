package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/16 0016
 */
@Service
@Transactional
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     * 添加到类型
     * @param article
     */
    public void add(Article article){
        articleSearchDao.save(article);
    }

    /**
     * 检索
     * @param key
     * @param page
     * @param size
     */
    public Page findByKey(String key, Integer page, Integer size) {
        return articleSearchDao.findByTitleOrContentLike(key, key, PageRequest.of(page-1,size));
    }
}
