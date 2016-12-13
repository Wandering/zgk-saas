/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwCourseGapRuleService.java 2016-12-06 15:51:08 $
 */

package cn.thinkjoy.saas.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

import java.util.List;
import java.util.Map;

public interface IJwCourseGapRuleService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{
    List<Map<String, Object>> queryLinkList(Map<String, String> map);

    List<Map<String, Object>> queryClassList(Map<String, String> map);

    List<Map<String, Object>> queryCourseList(Map<String, String> map);

    List<Map<String, Object>> queryTeacherList(Map<String, String> map);

    List<String> queryTeacherCourseList(Map<String, String> map);
}
