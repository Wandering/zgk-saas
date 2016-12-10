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
    public CourseResultView getCourseResult(String type,Integer tnId,Map<String,Object> paramsMap) {
        CourseResultView courseResultView = new CourseResultView();
        Map map = new HashMap();
        map.put("tnId", tnId);
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
        if (list != null && list.size() > 0) {
            JwTeachDate jwTeachDate = list.get(0);
            String[] strings = jwTeachDate.getTeachDetail().split(Constant.TIME_INTERVAL);
            for (String s : strings) {
                time += s.length();
            }
            if (strings.length < 3) {
                time += 0;
            }
        }
        courseResultView.setTeachDate(buffer.toString());
        courseResultView.setTeachTime(time);
        java.util.Random random=new java.util.Random();// 定义随机类
        String course = "课程";
        List<List<String>> list1  = new ArrayList<>();
        List<String> list2;
        for (int i = 7;i>0;i--){
            list2 = new ArrayList<>();
            for (int j = 7;j>0;j--){
                list2.add(course+random.nextInt(100));
            }
            list1.add(list2);
        }
        courseResultView.setWeek(list1);
        return courseResultView;
    }
//    private List<String> getClassCourseResult(Integer teachSize,String teachDetail) {
//        for(int i=0;i<teachSize;i++) {
//            List<String> weeks=new ArrayList<>();
//            for(int j=0;j<)
//        }
//    }
}
