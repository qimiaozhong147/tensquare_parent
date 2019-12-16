package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.add(article);
        return new Result(true, StatusCode.OK, "添加成功");

    }

    /**
     * 检索
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method = RequestMethod.GET) //想要这里不用{}接收参数方法内用@RequestParam String keywords
    public Result findByTitleLike(@PathVariable String keywords, @PathVariable int page, @PathVariable int size){
        Page<Article> articlePage = articleService.findByTitleLike(keywords, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(articlePage.getTotalElements(), articlePage.getContent()));
    }

}
