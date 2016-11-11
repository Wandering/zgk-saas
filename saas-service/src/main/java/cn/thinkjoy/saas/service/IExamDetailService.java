/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ExamDetailService.java 2016-11-01 11:03:42 $
 */

package cn.thinkjoy.saas.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

import java.util.List;
import java.util.Map;

public interface IExamDetailService<D extends IBaseDAO<T>, T extends BaseDomain>
    extends IBaseService<D, T>, IPageService<D, T>
{
    List<Map<String, Object>> getOverLineNumberByDate(Map<String, String> paramMap);

    Map<String, Object> getEnrollingNumberInfo(Map<String, String> paramMap);

    List<String> getLastExamIdByGrade(Map<String, Object> paramMap);

    Map<String, Object> getAvgScoresByExamId(Map<String, String> paramMap);

    void deleteWeakScoresByDetailId(Map<String, Object> paramMap);
}
