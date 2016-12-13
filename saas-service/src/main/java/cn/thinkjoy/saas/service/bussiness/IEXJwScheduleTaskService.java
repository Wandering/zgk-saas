/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTaskService.java 2016-12-06 15:06:24 $
 */

package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.bussiness.CourseResultView;

import java.util.Map;

public interface IEXJwScheduleTaskService {
    /**
     * 排课结果
     * @param type
     * @return
     */
    public CourseResultView getCourseResult(String type,Integer taskId, Integer tnId, Map<String,Object> paramsMap);
}
