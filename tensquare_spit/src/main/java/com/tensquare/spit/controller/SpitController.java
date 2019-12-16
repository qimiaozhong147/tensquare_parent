package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 点赞
     * @param id
     * @return
     */
    @RequestMapping(value = "/thumbup/{id}", method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String id){
        //判断用户是否点过赞了，还没有做用户认证，所以这里id写死
        String userid = "2023";
        if(redisTemplate.opsForValue().get(userid) != null && !"".equals(redisTemplate.opsForValue().get(userid))){
            return new Result(false, StatusCode.REPERROR, "你已经点过赞了");
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set( "thumbup_"+userid+"_"+ id,"1");
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 根据上级ID吐槽列表
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        Page<Spit> pageList = spitService.findByParentid(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(String id){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    /**
     * 添加
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 修改
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{id}" ,method = RequestMethod.POST)
    public Result update(@RequestBody Spit spit, @PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

}
