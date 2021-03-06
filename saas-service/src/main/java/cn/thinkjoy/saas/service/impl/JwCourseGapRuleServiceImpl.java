/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseGapRuleServiceImpl.java 2016-12-06 15:51:08 $
 */
package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwCourseGapRuleDAO;
import cn.thinkjoy.saas.dao.IJwScheduleTaskDAO;
import cn.thinkjoy.saas.domain.JwCourseGapRule;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.service.IJwCourseGapRuleService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("JwCourseGapRuleServiceImpl")
public class JwCourseGapRuleServiceImpl extends AbstractPageService<IBaseDAO<JwCourseGapRule>, JwCourseGapRule> implements IJwCourseGapRuleService<IBaseDAO<JwCourseGapRule>,JwCourseGapRule>{
    @Autowired
    private IJwCourseGapRuleDAO jwCourseGapRuleDAO;

    @Autowired
    private IJwScheduleTaskDAO jwScheduleTaskDAO;

    @Override
    public IBaseDAO<JwCourseGapRule> getDao() {
        return jwCourseGapRuleDAO;
    }

    @Override
    public List<Map<String, Object>> queryLinkList(Map<String, String> map)
    {
        return jwCourseGapRuleDAO.queryLinkList(map);
    }

    @Override
    public List<Map<String, Object>> queryClassList(int taskId)
    {
        JwScheduleTask task = jwScheduleTaskDAO.fetch(taskId);
        int tnId = task.getTnId();
        int grade = Integer.valueOf(task.getGrade());

        Map<String,String> map = Maps.newHashMap();
        map.put("tableName",ParamsUtils.combinationTableName(Constant.CLASS_ADM,tnId));
        map.put("searchKey","class_grade");
        map.put("searchValue",Constant.GRADES[Integer.valueOf(grade-1)]);
        List<Map<String, Object>> resultMap=new ArrayList<>();
        resultMap=jwCourseGapRuleDAO.queryClassList(map);
        map.put("tableName",ParamsUtils.combinationTableName(Constant.CLASS_EDU,tnId));
        resultMap.addAll(jwCourseGapRuleDAO.queryClassList(map));
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> queryCourseList(int taskId)
    {
        JwScheduleTask task = jwScheduleTaskDAO.fetch(taskId);
        int tnId = task.getTnId();
        int grade = Integer.valueOf(task.getGrade());

        Map<String,Integer> map = Maps.newHashMap();
        map.put("tnId",tnId);
        map.put("grade",grade);
        return jwCourseGapRuleDAO.queryCourseList(map);
    }


//    @Override
//    public void insert(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void update(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void updateNull(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void deleteById(Long id) {
//
//    }
//
//    @Override
//    public void deleteByProperty(String property, Object value) {
//
//    }
//
//    @Override
//    public BaseDomain fetch(Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain findOne(@Param("property") String property, @Param("value") Object value) {
//        return null;
//    }
//
//    @Override
//    public List findList(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public void deleteByCondition(Map condition) {
//
//    }
//
//    @Override
//    public void updateMap(@Param("map") Map entityMap) {
//
//    }
//
//    @Override
//    public List<JwCourseGapRule> findAll() {
//        return jwCourseGapRuleDAO.findAll();
//    }
//
//    @Override
//    public List like(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public Long selectMaxId() {
//        return null;
//    }
//
//    @Override
//    public BaseDomain updateOrSave(BaseDomain baseDomain, Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain selectOne(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public List selectList(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public Class getEntityClass() {
//        return null;
//    }
//
//    @Override
//    public int count(Map condition) {
//        return 0;
//    }
//
//    @Override
//    public BaseDomain queryOne(Map condition) {
//        return null;
//    }
//
//    @Override
//    public List queryList(@Param("condition") Map condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy) {
//        return null;
//    }
//
//    @Override
//    public List queryPage(@Param("condition") Map condition, @Param("offset") int offset, @Param("rows") int rows) {
//        return null;
//    }
//
//    @Override
//    protected JwCourseGapRuleDAO getDao() {
//        return jwCourseGapRuleDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }


}
