/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ExamDetailDAO.java 2016-11-01 11:03:42 $
 */
package cn.thinkjoy.saas.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.ExamDetail;

import java.util.List;
import java.util.Map;

public interface IExamDetailDAO extends IBaseDAO<ExamDetail>{
	
    List<Map<String, Object>> getOverLineNumberByDate(Map<String, String> paramMap);

    Map<String, Object> getEnrollingNumberInfo(Map<String, String> paramMap);

    Map<String, Object> getAvgScoresByExamId(Map<String, String> paramMap);

    List<String> getLastExamIdByGrade(Map<String, Object> paramMap);

    void deleteWeakScoresByDetailId(Map<String, Object> paramMap);
}
