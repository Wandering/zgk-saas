/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTaskServiceImpl.java 2016-12-06 15:06:24 $
 */
package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwTeachDateDAO;
import cn.thinkjoy.saas.domain.JwTeachDate;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;
import cn.thinkjoy.saas.service.bussiness.IEXJwScheduleTaskService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("EXJwScheduleTaskServiceImpl")
public class EXJwScheduleTaskServiceImpl  implements IEXJwScheduleTaskService {


    @Resource
    IJwTeachDateDAO iJwTeachDateDAO;



    @Override
    public CourseResultView getCourseResult(String type,Integer taskId,Integer tnId,Map<String,Object> paramsMap) {
        CourseResultView courseResultView = new CourseResultView();
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("taskId",taskId);
        List<JwTeachDate> list = iJwTeachDateDAO.queryList(map, "tid", "asc");
        if (list.size() == 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (JwTeachDate jwTeachDate : list) {
            buffer.append(jwTeachDate.getTeachDate()).append("|");
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        String time = "";
        Integer count = 0;
        if (list != null && list.size() > 0) {
            JwTeachDate jwTeachDate = list.get(0);
            String[] strings = jwTeachDate.getTeachDetail().split(Constant.TIME_INTERVAL);
            for (String s : strings) {
                time += s.length();
                count += s.length();
            }
            if (strings.length < 3) {
                time += 0;
            }
        }
        courseResultView.setTeachDate(buffer.toString());
        courseResultView.setTeachTime(time);
        Map<Integer,String > rtnMap = Maps.newHashMap();
        if ("teacher".equals(type)) {

            rtnMap.put(0, "");
            rtnMap.put(1, "化学\n(张丽红)\n高一2班");
            rtnMap.put(2, "化学\n(张丽红)\n高一3班");
            rtnMap.put(3, "化学\n(张丽红)\n高一4班");
            rtnMap.put(4, "化学\n(张丽红)\n高一5班");
            rtnMap.put(5, "化学\n(张丽红)\n高一6班");
            rtnMap.put(6, "");
            rtnMap.put(7, "");
            rtnMap.put(8, "");
            rtnMap.put(10, "");
            rtnMap.put(11, "");
            rtnMap.put(12, "");
        }else if ("student".equals(type)){
            rtnMap.put(0, "");
            rtnMap.put(1, "语文\n(李明伟)\n高一2班");
            rtnMap.put(2, "数学\n(张丽红)\n高一3班");
            rtnMap.put(3, "英语\n(刘晓文)\n高一4班");
            rtnMap.put(4, "化学\n(张红新)\n高一5班");
            rtnMap.put(5, "物理\n(杨国荣)\n高一6班");
            rtnMap.put(6, "政治\n(李丽)\n高一7班");
            rtnMap.put(7, "生物\n(左浩)\n高一8班");
            rtnMap.put(8, "历史\n(田芙蓉)\n高一9班");
            rtnMap.put(10, "通用技术\n(魏彤彤)\n高一10班");
            rtnMap.put(11, "");
            rtnMap.put(12, "");
        }else if ("room".equals(type)){
            rtnMap.put(0, "");
            rtnMap.put(1, "语文\n(李明伟)\n高一2班");
            rtnMap.put(2, "数学\n(张丽红)\n高一3班");
            rtnMap.put(3, "");
            rtnMap.put(4, "化学\n(张红新)\n高一5班");
            rtnMap.put(5, "");
            rtnMap.put(6, "政治\n(李丽)\n高一7班");
            rtnMap.put(7, "");
            rtnMap.put(8, "历史\n(田芙蓉)\n高一9班");
            rtnMap.put(10, "通用技术\n(魏彤彤)\n高一10班");
            rtnMap.put(11, "");
            rtnMap.put(12, "");
        }
        List<List<String>> list1  = new ArrayList<>();
        List<String> list2;
        for (int i = list.size();i>0;i--){
            list2 = new ArrayList<>();
            for (int j = count;j>0;j--){
                list2.add(getCourse(rtnMap));
            }
            list1.add(list2);
        }
        courseResultView.setWeek(list1);
        return courseResultView;
    }

    private String getCourse(Map<Integer,String> map){
        java.util.Random random=new java.util.Random();// 定义随机类
        return map.get(random.nextInt(12));

    }
//    private List<String> getClassCourseResult(Integer teachSize,String teachDetail) {
//        for(int i=0;i<teachSize;i++) {
//            List<String> weeks=new ArrayList<>();
//            for(int j=0;j<)
//        }
//    }
}
