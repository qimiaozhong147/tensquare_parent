package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/label")
@CrossOrigin //跨域处理
public class LabelController {

    @Autowired
    LabelService labelService;

    /**
     * 查询所有标签
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Label> list = labelService.findAll();
        return new Result(true, StatusCode.OK, "查询所有标签成功！", list);
    }

    /**
     * 根据id查找标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK, "根据id查找标签成功！", labelService.findById(id));
    }

    /**
     * 添加标签
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功！");
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除标签成功！");
    }

    /**
     * 修改标签
     * @param label
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Label label, @PathVariable String id){
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功！");
    }

    /**
     * 条件查询
     * @param map
     * @return
     */

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map map){
        return new Result(true, StatusCode.OK, "查詢成功", labelService.findSearch(map));
    }

    /**
     * 分页查询
     * @param label
     * @param page 页码
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size){//这里不用integer防止空指针，size没值，int默认是0
        Page<Label> pageLabel = labelService.pageQuery(label,page, size);
        return new Result(true, StatusCode.OK, "查詢成功", new PageResult<Label>(pageLabel.getTotalElements(), pageLabel.getContent()));
    }
}
