package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 增加文章
     * @param article
     */
    public void add(Article article){
        article.setId(idWorker.nextId() + "");
        articleSearchDao.save(article);
    }

    public Page<Article> findByTitleLike(String keywords, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleSearchDao.findByTitleOrContentLike(keywords, keywords, pageRequest);
    }
}
