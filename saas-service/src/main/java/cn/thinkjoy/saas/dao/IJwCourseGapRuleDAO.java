/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseGapRuleDAO.java 2016-12-06 15:51:08 $
 */
package cn.thinkjoy.saas.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.JwCourseGapRule;

import java.util.List;
import java.util.Map;

public interface IJwCourseGapRuleDAO extends IBaseDAO<JwCourseGapRule>{
	
    List<Map<String, Object>> queryLinkList(Map<String, String> map);

    List<Map<String, Object>> queryClassList(Map<String, String> map);

    List<Map<String, Object>> queryCourseList(Map<String, Integer> map);

}
