/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ExamDetailServiceImpl.java 2016-11-01 11:03:42 $
 */
package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.dao.IExamDetailDAO;
import cn.thinkjoy.saas.domain.ExamDetail;
import cn.thinkjoy.saas.service.IExamDetailService;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("ExamDetailServiceImpl")
public class ExamDetailServiceImpl extends AbstractPageService<IBaseDAO<ExamDetail>, ExamDetail> implements IExamDetailService<IBaseDAO<ExamDetail>,ExamDetail>{
    @Autowired
    private IExamDetailDAO examDetailDAO;

    @Override
    public IBaseDAO<ExamDetail> getDao() {
        return examDetailDAO;
    }

    @Override
    public List<Map<String, Object>> getOverLineNumberByDate(Map<String, String> paramMap)
    {
        return examDetailDAO.getOverLineNumberByDate(paramMap);
    }

    @Override
    public Map<String, Object> getEnrollingNumberInfo(Map<String, String> paramMap)
    {
        return examDetailDAO.getEnrollingNumberInfo(paramMap);
    }

    @Override
    public List<String> getLastExamIdByGrade(Map<String, Object> paramMap)
    {
        return examDetailDAO.getLastExamIdByGrade(paramMap);
    }

    @Override
    public Map<String, Object> getAvgScoresByExamId(Map<String, String> paramMap)
    {
        return examDetailDAO.getAvgScoresByExamId(paramMap);
    }

    @Override
    public void deleteWeakScoresByDetailId(Map<String, Object> paramMap)
    {
        examDetailDAO.deleteWeakScoresByDetailId(paramMap);
    }

    @Override
    public List<Map<String, Object>> getMostAttentionNumberChart(Map<String, String> paramMap)
    {
        return examDetailDAO.getMostAttentionNumberChart(paramMap);
    }

    @Override
    public List<Map<String, Object>> getMostAttentionCourseChart(Map<String, String> paramMap)
    {
        return examDetailDAO.getMostAttentionCourseChart(paramMap);
    }

    @Override
    public List<Map<String, Object>> getMostAttentionPage(Map<String, String> paramMap)
    {
        return examDetailDAO.getMostAttentionPage(paramMap);
    }

    @Override
    public List<String> getClassesNameByGrade(Map<String, String> paramMap)
    {
        return examDetailDAO.getClassesNameByGrade(paramMap);
    }

    @Override
    public List<Map<String, Object>> getAvgScoresForClass(Map<String, String> paramMap)
    {
        return examDetailDAO.getAvgScoresForClass(paramMap);
    }

    @Override
    public List<Map<String, Object>> getAvgScoresForClassStudent(Map<String, String> paramMap)
    {
        return examDetailDAO.getAvgScoresForClassStudent(paramMap);
    }

    @Override
    public List<Map<String, Object>> getMostAttentionListForClass(Map<String, String> paramMap)
    {
        return examDetailDAO.getMostAttentionListForClass(paramMap);
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
//    public List<ExamDetail> findAll() {
//        return examDetailDAO.findAll();
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
//    protected ExamDetailDAO getDao() {
//        return examDetailDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }


}
