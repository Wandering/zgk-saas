/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  CityService.java 2016-10-26 10:18:20 $
 */

package cn.thinkjoy.saas.service.bussiness;


import cn.thinkjoy.saas.domain.bussiness.CourseResultView;

import java.util.Map;

public interface ISyllabusService{

    /**
     * 获取所有课程表
     * @param tnId
     * @param taskId
     * @return
     */
    Map<String,Object> getAllSyllabus(int tnId, int taskId);

    /**
     * 获取某个老师课程表
     * @param tnId
     * @param taskId
     * @return
     */
    CourseResultView getTeacherSyllabus(int tnId, int taskId, int teacherId);

    /**
     * 获取某个班级课程表
     * @param tnId
     * @param taskId
     * @param classId
     * @return
     */
    CourseResultView getClassSyllabus(int tnId, int taskId, int classId);

}
