/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTaskService.java 2016-12-06 15:06:24 $
 */

package cn.thinkjoy.saas.service.bussiness;

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


    /**
     * 初始化排课参数
     * @param taskId
     * @param tnId
     * @return
     */
    boolean InitParmasFile(Integer taskId, Integer tnId) throws IOException;


    /**
     * 排课结果状态   0:正在排课  1:排课成功   -1:排课失败
     * @param taskId
     * @param tnId
     * @return
     */
    String getSchduleResultStatus(Integer taskId, Integer tnId);


    /**
     * 排课失败
     * @param taskId
     * @param tnId
     * @return
     */
    List<String> getSchduleErrorDesc(Integer taskId, Integer tnId);


    JwScheduleTask selectScheduleTaskPath(Map map);

    List<String> getNoNScheduleTaskPliableRule(Integer taskId, Integer tnId);

    /**
     * 获取排课的课时信息
     * @param tnId
     * @param taskId
     * @return
     */
    Map<String,Object> getCourseTimeConfig(int tnId, int taskId);

    /**
     * 获取路径
     * @param taskId
     * @param tnId
     * @return
     */
    String getScheduleTaskPath(Integer taskId, Integer tnId);
}
