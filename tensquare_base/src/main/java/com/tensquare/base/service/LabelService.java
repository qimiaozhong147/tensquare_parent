package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签业务逻辑层
 */
@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 通过id查找标签
     * @param id
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();//?
    }

    /**
     * 增加标签
     * @param label
     */
    public void add(Label label){
        label.setId(idWorker.nextId() + "");//设置id
        labelDao.save(label);
    }

    /**
     * 修改标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }



    /**
     * 条件查询
     * @param map
     * @return
     */
    public List<Label> findSearch(Map map){
        Specification<Label> specification = createSpecification(map);
        return labelDao.findAll(specification);
    }

    public Page<Label> pageQuery(Label label, int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size);
        return labelDao.findAll(create(label), pageable);

    }

    private Specification<Label> create(Label label){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if(label.getlabelname() != null && !"".equals(label.getlabelname())){
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getlabelname() + "%");
                    list.add(predicate);
                }
                if(label.getState() != null && !"".equals(label.getState())){
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
    }

    /**
     * 构建查询条件
     * @param map
     * @return
     */
    private Specification<Label> createSpecification(Map map) {

        return new Specification<Label>() {
            /**
             *
             * @param root 根对象， 也就是要把条件封装到哪个对象中，where 类名 = label.getid
             * @param query 封装的都是查询关键字， 比如group by order by 等 很少用
             * @param criteriaBuilder 用来封装条件对象的， 如果直接返回null, 表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //new一个list集合，来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if(map.get("labelname")!=null && !"".equals("labelname")){
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%"+ map.get("labelname") +"%");
                    list.add(predicate);
                }
                if(map.get("state")!=null && !"".equals("state")){
                    //这里用是的equal比较两个字符串是否相等
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), (String)map.get("state"));
                    list.add(predicate);
                }

                Predicate[] parr = new Predicate[list.size()];//new一个数组作为最终返回值的条件
                return criteriaBuilder.and(list.toArray(parr));//把list转成数组返回
            }
        };

    }
}
