/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  ExamDAO.java 2016-11-01 11:03:41 $
 */
package cn.thinkjoy.saas.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface IExamDAO extends IBaseDAO<Exam>{

    /**
     * 批量新增
     * @param tableName  表名
     * @param columnList 列名列表
     * @param valueList 值列表
     * @return
     */
    Integer batchInsertData(@Param("tableName") String tableName,
        @Param("columnList") List<String> columnList,
        @Param("valueList") List<LinkedHashMap<String, String>> valueList);
}
