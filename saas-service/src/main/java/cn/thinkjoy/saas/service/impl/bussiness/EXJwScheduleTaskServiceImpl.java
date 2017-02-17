/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: saas
 * $Id:  JwScheduleTaskServiceImpl.java 2016-12-06 15:06:24 $
 */
package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.*;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;
import cn.thinkjoy.saas.dto.CourseManageDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeTypeEnum;
import cn.thinkjoy.saas.service.IGradeService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.bussiness.IEXCourseManageService;
import cn.thinkjoy.saas.service.bussiness.IEXJwScheduleTaskService;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
import cn.thinkjoy.saas.service.common.EduClassUtil;
import cn.thinkjoy.zgk.common.StringUtil;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.common.collect.Maps;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


@Service("EXJwScheduleTaskServiceImpl")
public class EXJwScheduleTaskServiceImpl  implements IEXJwScheduleTaskService {

    @Autowired
    private IJwTeacherBaseInfoDAO teacherBaseInfoDAO;
    @Resource
    private IJwTeachDateDAO iJwTeachDateDAO;
    @Autowired
    private IJwClassBaseInfoDAO classBaseInfoDAO;
    @Autowired
    private IJwRoomDAO roomDAO;
    @Autowired
    private IJwScheduleTaskDAO scheduleTaskDAO;
    @Autowired
    private IJwClassBaseInfoDAO jwClassBaseInfoDAO;

    @Autowired
    private IGradeService gradeService;

    @Autowired
    private EXITenantConfigInstanceService exiTenantConfigInstanceService;

    @Autowired
    private static final Logger LOGGER = Logger.getLogger(EXJwScheduleTaskServiceImpl.class);

    @Autowired
    private IEXScheduleBaseInfoService iexScheduleBaseInfoService;

    @Autowired
    private IJwCourseDAO jwCourseDAO;

    @Autowired
    private IEXCourseManageService iexCourseManageService;

//    @Autowired
//    private

    @Autowired
    private RedisRepository<String,Object> redis;



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

        Map<String,Object>  roomMap = Maps.newHashMap();
        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);
        roomMap.put("tnId",tnId);
        roomMap.put("taskId",taskId);
        roomMap.put("grade",jwScheduleTask.getGrade());
        List<JwRoom> roomList = roomDAO.queryList(roomMap,"id","desc");
        Map<Integer,String > rtnMap = Maps.newHashMap();
        Map<String,Object> teacherMap = Maps.newHashMap();
        teacherMap.put("tnId",tnId);
        teacherMap.put("grade",jwScheduleTask.getGrade());
        List<TeacherBaseDto> teacherBaseInfos = iexScheduleBaseInfoService.queryTeacherByTaskId(teacherMap);
        if ("teacher".equals(type)) {
            if (!StringUtil.isNulOrBlank(paramsMap.get("teacherId")!=null?paramsMap.get("teacherId").toString():null)) {
                JwTeacherBaseInfo jwTeacherBaseInfo = (JwTeacherBaseInfo) teacherBaseInfoDAO.fetch(paramsMap.get("teacherId"));
                Map<String,Object> classQueryMap = Maps.newHashMap();
                classQueryMap.put("tnId",tnId);
                classQueryMap.put("grade",jwScheduleTask.getGrade());
                classQueryMap.put("className",jwTeacherBaseInfo.getTeacherCourse());
                classQueryMap.put("classType",Constant.SUBJECT_CLASS_TYPE);
                List<JwClassBaseInfo> classBaseInfos = jwClassBaseInfoDAO.like(classQueryMap,"id","asc");
                if (classBaseInfos.size()==0){
                    classQueryMap.put("className",null);
                    classQueryMap.put("classType",Constant.DEFULT_CLASS_TYPE);
                    classBaseInfos = jwClassBaseInfoDAO.queryList(classQueryMap,"id","asc");
                }
                classBaseInfos = classBaseInfos.size() >= Constant.DEFULT_CLASS_NUM ? classBaseInfos.subList(0,Constant.DEFULT_CLASS_NUM) : classBaseInfos;
                rtnMap.put(0, "");
                rtnMap.put(1, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
                rtnMap.put(2,jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
                rtnMap.put(3, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
                rtnMap.put(4, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
                rtnMap.put(5, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
                rtnMap.put(6, "");
                rtnMap.put(7, "");
                rtnMap.put(8, "");
                rtnMap.put(10, "");
                rtnMap.put(11, "");
                rtnMap.put(12, "");
            }
        }else if ("student".equals(type)){
            rtnMap.put(0, "");
            rtnMap.put(1, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(2, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(3, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(4, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(5, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(6, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(7, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(8, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(10,getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
            rtnMap.put(11, "");
            rtnMap.put(12, "");
        }else if ("room".equals(type)){
            rtnMap.put(0, "");
            rtnMap.put(1, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
            rtnMap.put(2, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
            rtnMap.put(3, "");
            rtnMap.put(4, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
            rtnMap.put(5, "");
            rtnMap.put(6, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
            rtnMap.put(7, "");
            rtnMap.put(8, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
            rtnMap.put(10, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
            rtnMap.put(11, "");
            rtnMap.put(12, "");
        }
        List<List<String>> list1  = new ArrayList<>();
        List<String> list2;
        for (int i = list.size();i>0;i--){
            list2 = new ArrayList<>();
            for (int j = count;j>0;j--){
                if (i==5 && j==1){
                    //星期一第7节不排课
                    list2.add("");
                }else {
                    list2.add(getCourse(rtnMap));
                }
            }
            list1.add(list2);
        }
        courseResultView.setWeek(list1);
        return courseResultView;
    }

    @Override
    public Map<String,Object> getAllCourseResult(Integer taskId,Integer tnId) throws IOException, ParseException {

        return getCourseResult(tnId,taskId);
    }

    private String getCourse(Map<Integer,String> map){
        java.util.Random random=new java.util.Random();// 定义随机类
        return map.get(random.nextInt(12));

    }

    private String getTeacherCourse(List<TeacherBaseDto> teacherBaseInfos){
        java.util.Random random=new java.util.Random();// 定义随机类
        if (teacherBaseInfos.size()==0)return "";
        TeacherBaseDto teacherBaseDto = teacherBaseInfos.get(random.nextInt(teacherBaseInfos.size()));
        return teacherBaseDto.getCourseName()+"\n("+teacherBaseDto.getTeacherName()+")";

    }

    private String getTeacherCourse(List<TeacherBaseDto> teacherBaseInfos,Integer tnId,String grade){
        if (teacherBaseInfos.size()==0)return "";
        java.util.Random random=new java.util.Random();// 定义随机类
        TeacherBaseDto teacherBaseDto = teacherBaseInfos.get(random.nextInt(teacherBaseInfos.size()));
        Map<String,Object> classQueryMap = Maps.newHashMap();
        classQueryMap.put("tnId",tnId);
        classQueryMap.put("grade",grade);
        classQueryMap.put("className",teacherBaseDto.getCourseName());
        classQueryMap.put("classType",Constant.SUBJECT_CLASS_TYPE);
        List<JwClassBaseInfo> classBaseInfos = jwClassBaseInfoDAO.like(classQueryMap,"id","asc");
        if (classBaseInfos.size()==0){
            classQueryMap.put("className",null);
            classQueryMap.put("classType",Constant.DEFULT_CLASS_TYPE);
            classBaseInfos = jwClassBaseInfoDAO.queryList(classQueryMap,"id","asc");
        }
        return teacherBaseDto.getCourseName()+"\n("+teacherBaseDto.getTeacherName()+")   "+getClassRandom(classBaseInfos);

    }
    private String getRoomRandom(List<JwRoom> rooms){
        java.util.Random random=new java.util.Random();// 定义随机类
        if (rooms.size()==0)return "";
        JwRoom jwRoom = rooms.get(random.nextInt(rooms.size()));
        return jwRoom.getRoomName();

    }
    private String getClassRandom(List<JwClassBaseInfo> classBaseInfos){
        java.util.Random random=new java.util.Random();// 定义随机类
        if (classBaseInfos.size()==0)return "";
        JwClassBaseInfo jwClassBaseInfo = classBaseInfos.get(random.nextInt(classBaseInfos.size()));
        return jwClassBaseInfo.getClassName();

    }

    /**
     * 获取并翻译课程表
     *
     * @param tnId
     * @param taskId
     * @return
     */
    private Map<String,Object> getCourseResult(int tnId, int taskId)
            throws ParseException, IOException {
        String redisKey = new StringBuilder()
                .append(Constant.COURSE_TABLE_REDIS_KEY)
                .append(Constant.COURSE_TABLE_REDIS_SPLIT)
                .append(tnId)
                .append(Constant.COURSE_TABLE_REDIS_SPLIT)
                .append(taskId)
                .toString();
        if (!this.redis.exists(redisKey)) {
            Map<String,Object> resultMap = Maps.newHashMap();
            List<List<List<String>>> courseTables = new ArrayList<>();
            ;
            CourseResultView courseResultView = new CourseResultView();
            LOGGER.info("************获取时间设置 S************");
            Map<String,Object> timeConfigMap = this.getCourseTimeConfig(tnId,taskId);
            resultMap.put("teachDate",timeConfigMap.get("teachDate").toString());
            resultMap.put("teachTime",timeConfigMap.get("time").toString());
            LOGGER.info("************获取时间设置 E************");

            LOGGER.info("************获取时间设置 S************");
            List<LinkedHashMap<String,Object>> classList = this.getClassByTnIdAndTaskId(tnId,taskId);
            StringBuffer buffer1 = new StringBuffer();
            for (LinkedHashMap<String,Object> class_:classList){
                buffer1.append(class_.get("class_name")).append("|");
            }
            if (buffer1.length() > 0) {
                buffer1.delete(buffer1.length() - 1, buffer1.length());
            }
            resultMap.put("room",buffer1.toString());
            LOGGER.info("************获取时间设置 E************");

            LOGGER.info("************获取课表 S************");
            Map<Integer,String> courses = getCourseByTnIdAndTaskId(tnId,taskId);
            CharSource main = Files.asCharSource(new File("classpath:config/admin_course_0.txt"), Charset.defaultCharset());
            List<String> allCourseList =  main.readLines();
            List<List<String>> weekCourseList;
            for (String courseLine : allCourseList){
                String[] weekCourses  =  courseLine.split("\t");

                weekCourseList = new LinkedList<>();
                List<String> dayCourseList;
                for (String dayCourseStr : weekCourses){
                    dayCourseList = new LinkedList<>();
                    String[] dayCourses = dayCourseStr.split("  ");
                    for (int i = 0 ; i < dayCourses.length ; i++){
                        //课程转换
                        String course = courses.get(Integer.valueOf(dayCourses[i]));
                        dayCourseList.add(course);
                    }
                    weekCourseList.add(dayCourseList);
                }
                courseTables.add(weekCourseList);
            }
            resultMap.put("roomData",courses);
            LOGGER.info("************获取课表 E************");

            LOGGER.info("************存redis S************");
            this.redis.set(redisKey, JSON.json(resultMap));
            LOGGER.info("************存redis E************");
            return resultMap;
        } else {
            return JSON.parse(redisKey, HashMap.class);
        }
    }

    private Map<String,Object> getCourseTimeConfig(int tnId, int taskId){


        LOGGER.info("************获取时间设置 S************");
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("taskId", taskId);
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

        Map<String,Object > rtnMap = new HashMap<>();
        rtnMap.put("teachDate",buffer.toString());
        rtnMap.put("time",time);
        rtnMap.put("list",list);
        rtnMap.put("count",count);

        LOGGER.info("************获取时间设置 E************");
        return rtnMap;
    }

    private List<LinkedHashMap<String,Object>> getClassByTnIdAndTaskId(int tnId,int taskId){
        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);
        List<LinkedHashMap<String, Object>> classes = new ArrayList();
        Map<String, Object> param = new HashMap<>();
        param.put("gradeCode", jwScheduleTask.getGrade());
        param.put("tnId", tnId);
        Grade gradeServiceOne = (Grade) gradeService.queryOne(param);
        if (gradeServiceOne == null)
            throw new BizException(ErrorCode.GRADE_FORMAT_ERROR.getCode(), ErrorCode.GRADE_FORMAT_ERROR.getMessage());
        //判定是否存在教学班
        Integer gradeType = gradeServiceOne.getClassType();
        if (gradeType == null)
            throw new BizException(ErrorCode.GRADE_FORMAT_ERROR.getCode(), ErrorCode.GRADE_FORMAT_ERROR.getMessage());
        String grade = gradeServiceOne.getGrade();
        boolean isExistTeaching = GradeTypeEnum.Teaching.getCode().equals(gradeType);
        if (isExistTeaching) {
            //是教学班+是走班课程
            classes = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, grade,Constant.CLASS_EDU);
        } else {
            //是教学班+不是走班课程
            classes = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, grade,Constant.CLASS_ADM);
            //不教学班+是走班课程
        }
        return classes;
    }

    private Map<Integer,String> getCourseByTnIdAndTaskId(int tnId,int taskId){
        Map<String,Object> map = new HashMap<>();
        map.put("tnId",tnId);
        map.put("taskId",taskId);
        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);
        List<CourseManageDto> courses = iexCourseManageService.getCourseByTnIdAndGrade(tnId,Integer.valueOf(jwScheduleTask.getGrade()));
        List<JwCourse> list = jwCourseDAO.queryList(map,"id","desc");
        Map<Integer,String> courseMap = new LinkedHashMap();
        for (int i = 1 ; i < list.size()+1 ; i++){
            courseMap.put(i,courses.get(list.get(i-1).getCourseId()).getCourseBaseName());
        }
        return courseMap;
    }

//    public static void main(String[] args) {
//        CharSource main = Files.asCharSource(new File("/Users/yangyongping/Desktop/yqhc/zgk-saas/saas-service/src/main/resources/config/admin_course_0.txt"), Charset.defaultCharset());
////        CharSource main = Files.asCharSource(new File("classpath:config/admin_course_0.txt"), Charset.defaultCharset());
//        try {
//
//            main.readFirstLine().split("\t")[0];
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
