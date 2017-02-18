/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTaskService.java 2016-12-06 15:06:24 $
 */

package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.bussiness.CourseResultView;

import java.io.IOException;
import java.util.Map;

public interface IEXJwScheduleTaskService {
    /**
     * 排课结果
     * @param type
     * @return
     */
    public CourseResultView getCourseResult(String type,Integer taskId, Integer tnId, Map<String,Object> paramsMap);

    /**
     * 总课表
     * @param taskId
     * @param tnId
     * @return
     */
    Map<String,Object> getAllCourseResult(Integer taskId, Integer tnId);


    /**
     * 初始化排课参数
     * @param taskId
     * @param tnId
     * @return
     */
    boolean InitParmasFile(Integer taskId, Integer tnId) throws IOException;
}
