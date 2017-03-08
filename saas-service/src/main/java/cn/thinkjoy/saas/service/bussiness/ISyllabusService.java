/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  CityService.java 2016-10-26 10:18:20 $
 */

package cn.thinkjoy.saas.service.bussiness;


import cn.thinkjoy.saas.domain.JwCourseTable;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;

import java.util.List;
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
    CourseResultView getClassSyllabus(int tnId, int taskId, int classId,int classType);

    /**
     * 获取某个班级课程表
     * @param tnId
     * @param taskId
     * @param roomId
     * @return
     */
    CourseResultView getRoomSyllabus(int tnId, int taskId, int roomId);

    /**
     * 获取某个学生课程表
     * @param tnId
     * @param taskId
     * @param studentNo
     * @return
     */
    CourseResultView getStudentSyllabus(int tnId, int taskId, long studentNo);

    /**
     * 班级交换课表
     * @param tnId
     * @param taskId
     * @param classId
     * @param source
     * @param target
     * @return
     */
    boolean classExchange(int tnId,int taskId,int classId,int[] source, int[] target);

    /**
     * 老师交换课表
     * @param tnId
     * @param taskId
     * @param teacherId
     * @param source
     * @param target
     * @return
     */
    boolean teacherExchange(int tnId,int taskId,int teacherId,int[] source, int[] target);


    /**
     * 根据坐标查询课表记录
     * @param tnId
     * @param taskId
     * @param id
     * @param coordinate
     * @param type
     * @return
     */
    List<JwCourseTable> getSyllabusByCoordinate(int tnId, int taskId, int id, int[] coordinate, String type);

    /**
     * 生成并填充课表内容
     * @param tnId
     * @param taskId
     * @param type
     * @param params
     * @return
     */
    CourseResultView genSyllabus(int tnId,int taskId,String type,Map<String, Object> params,List<List<String>> lists,Map<String, Object> timeConfigMap);
    CourseResultView genSyllabus(int tnId,int taskId,boolean hasRoom,String type,Map<String, Object> params,List<List<String>> lists,Map<String, Object> timeConfigMap);

    /**
     * 生成默认课表
     * @param dayCount
     * @param courseCount
     * @return
     */
    List<List<String>> genDefaultSyllabus(int dayCount,int courseCount);

}
