/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ExamService.java 2016-11-01 11:03:41 $
 */

package cn.thinkjoy.saas.service;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.saas.domain.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface IExamService<D extends IBaseDAO<T>, T extends Exam> extends IBaseService<D, T>, IPageService<D, T>
{
    Integer batchInsertData(String tableName, List<String> columnList,
        List<LinkedHashMap<String, String>> valueList);
}
