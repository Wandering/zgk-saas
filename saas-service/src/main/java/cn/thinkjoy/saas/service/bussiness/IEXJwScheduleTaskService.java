/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTaskService.java 2016-12-06 15:06:24 $
 */

package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.JwCourseTable;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;
import com.alibaba.dubbo.common.json.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IEXJwScheduleTaskService {
    /**
     * 排课结果
     * @param type
     * @return
     */
    @Deprecated
    CourseResultView getCourseResult(String type,Integer taskId, Integer tnId, Map<String,Object> paramsMap,Map<String, Object> courseTimeConfig);

    /**
     * 总课表
     * @param taskId
     * @param tnId
     * @return
     */
    @Deprecated
    Map<String,Object> getAllCourseResult(Integer taskId, Integer tnId) throws IOException, ParseException;


    String  getClsssTypeTagByTaskId(Integer taskId,  Integer tnId);
    /**
     * 初始化参数
     * @param taskId
     * @param tnId
     * @return
     */
    boolean initParmasFile(final Integer taskId, final Integer tnId) throws IOException;
//    /**
//     * 初始化教学班排课参数
//     * @param taskId
//     * @param tnId
//     * @return
//     */
//    boolean eduParmas(Integer taskId, Integer tnId) throws IOException;
//    /**
//     * 初始化行政班排课参数
//     * @param taskId
//     * @param tnId
//     * @return
//     */
//    boolean admParmas(Integer taskId, Integer tnId) throws IOException;


    /**
     * 排课结果状态   0:正在排课  1:排课成功   -1:排课失败
     * @param taskId
     * @param tnId
     * @return
     */
    String getSchduleResultStatus(Integer taskId, Integer tnId);


    /**
     * 调课结果  1:调课成功  -1:调课失败 -2 调课失败
     * @param taskId
     * @param tnId
     * @return
     */
    String getAdjustmentSchduleResult(Integer taskId, Integer tnId);


    /**
     * 排课失败
     * @param taskId
     * @param tnId
     * @return
     */
    List<String> getSchduleErrorDesc(Integer taskId, Integer tnId);

//    List<String>


    JwScheduleTask selectScheduleTaskPath(Map map);

    List<String> getAdjustmentInfo(Integer taskId, Integer tnId);

    List<String> getNoNScheduleTaskPliableRule(Integer taskId, Integer tnId);

    /**
     * 获取排课的课时信息
     * @param tnId
     * @param taskId
     * @return
     */
    Map<String,Object> getCourseTimeConfig(int tnId, int taskId);

    /**
     * 获取教室信息
     * @param taskId
     * @param tnId
     * @return
     */
    List<StringBuffer> getClassRoom(Integer taskId,Integer tnId);
    /**
     * 获取路径
     * @param taskId
     * @param tnId
     * @return
     */
    String getScheduleTaskPath(Integer taskId, Integer tnId);

    /**
     * 调课
     * @param jwCourseTable   调课对象
     * @param adjustmentType  0:老师 1:行政
     * @return
     */
    boolean SerializableAdjustmentSchedule(JwCourseTable jwCourseTable, Integer adjustmentType);

    public Map<String,Object> getConfigRooms(String taskId);

    public Map<String,Object> updateClassRoom(String classRoomId,int scheduleNumber);
}
