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
import cn.thinkjoy.saas.dao.bussiness.ICourseBaseInfoDAO;
import cn.thinkjoy.saas.dao.bussiness.ICourseManageDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
import cn.thinkjoy.saas.dao.bussiness.scheduleRule.MergeClassDAO;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.domain.bussiness.CourseManageVo;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;
import cn.thinkjoy.saas.domain.bussiness.MergeClassInfoVo;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.IGradeService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.bussiness.IEXCourseManageService;
import cn.thinkjoy.saas.service.bussiness.IEXJwScheduleTaskService;
import cn.thinkjoy.saas.service.common.ConvertUtil;
import cn.thinkjoy.saas.service.common.FileOperation;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import cn.thinkjoy.saas.service.common.TimeTabling;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.common.collect.Maps;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


@Service("EXJwScheduleTaskServiceImpl")
public class EXJwScheduleTaskServiceImpl implements IEXJwScheduleTaskService {

    @Resource
    private IJwTeachDateDAO iJwTeachDateDAO;
    @Autowired
    private IJwRoomDAO roomDAO;
    @Autowired
    private IJwScheduleTaskDAO scheduleTaskDAO;

    @Autowired
    private IGradeService gradeService;

    @Autowired
    private EXITenantConfigInstanceService exiTenantConfigInstanceService;

    @Autowired
    private static final Logger LOGGER = Logger.getLogger(EXJwScheduleTaskServiceImpl.class);


    @Autowired
    IJwClassRuleDAO iJwClassRuleDAO;
    @Autowired
    IEXTeantCustomDAO iexTeantCustomDAO;
    @Autowired
    ICourseManageDAO iCourseManageDAO;
    @Autowired
    ICourseBaseInfoDAO courseBaseInfoDAO;
    @Autowired
     IGradeDAO iGradeDAO;
    @Autowired
    IJwCourseRuleDAO jwCourseRuleDAO;
    @Autowired
    MergeClassDAO mergeClassDAO;
    @Autowired
    ICourseBaseInfoDAO iCourseBaseInfoDAO;
    @Autowired
    IJwTeacherRuleDAO iJwTeacherRuleDAO;
    @Autowired
     IJwBaseConRuleDAO iJwBaseConRuleDAO;
    @Autowired
    IJwBaseJaqpRuleDAO iJwBaseJaqpRuleDAO;
    @Autowired
    IJwBaseWeekRuleDAO iJwBaseWeekRuleDAO;
    @Autowired
    IJwBaseDayRuleDAO iJwBaseDayRuleDAO;

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
//        CourseResultView courseResultView = new CourseResultView();
//        Map map = new HashMap();
//        map.put("tnId", tnId);
//        map.put("taskId",taskId);
//        List<JwTeachDate> list = iJwTeachDateDAO.queryList(map, "tid", "asc");
//        if (list.size() == 0) {
//            return null;
//        }
//        StringBuffer buffer = new StringBuffer();
//        for (JwTeachDate jwTeachDate : list) {
//            buffer.append(jwTeachDate.getTeachDate()).append("|");
//        }
//        if (buffer.length() > 0) {
//            buffer.delete(buffer.length() - 1, buffer.length());
//        }
//        String time = "";
//        Integer count = 0;
//        if (list != null && list.size() > 0) {
//            JwTeachDate jwTeachDate = list.get(0);
//            String[] strings = jwTeachDate.getTeachDetail().split(Constant.TIME_INTERVAL);
//            for (String s : strings) {
//                time += s.length();
//                count += s.length();
//            }
//            if (strings.length < 3) {
//                time += 0;
//            }
//        }
//        courseResultView.setTeachDate(buffer.toString());
//        courseResultView.setTeachTime(time);
//
//        Map<String,Object>  roomMap = Maps.newHashMap();
//        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);
//        roomMap.put("tnId",tnId);
//        roomMap.put("taskId",taskId);
//        roomMap.put("grade",jwScheduleTask.getGrade());
//        List<JwRoom> roomList = roomDAO.queryList(roomMap,"id","desc");
//        Map<Integer,String > rtnMap = Maps.newHashMap();
//        Map<String,Object> teacherMap = Maps.newHashMap();
//        teacherMap.put("tnId",tnId);
//        teacherMap.put("grade",jwScheduleTask.getGrade());
//        List<TeacherBaseDto> teacherBaseInfos = iexScheduleBaseInfoService.queryTeacherByTaskId(teacherMap);
//        if ("teacher".equals(type)) {
//            if (!StringUtil.isNulOrBlank(paramsMap.get("teacherId")!=null?paramsMap.get("teacherId").toString():null)) {
//                JwTeacherBaseInfo jwTeacherBaseInfo = (JwTeacherBaseInfo) teacherBaseInfoDAO.fetch(paramsMap.get("teacherId"));
//                Map<String,Object> classQueryMap = Maps.newHashMap();
//                classQueryMap.put("tnId",tnId);
//                classQueryMap.put("grade",jwScheduleTask.getGrade());
//                classQueryMap.put("className",jwTeacherBaseInfo.getTeacherCourse());
//                classQueryMap.put("classType",Constant.SUBJECT_CLASS_TYPE);
//                List<JwClassBaseInfo> classBaseInfos = jwClassBaseInfoDAO.like(classQueryMap,"id","asc");
//                if (classBaseInfos.size()==0){
//                    classQueryMap.put("className",null);
//                    classQueryMap.put("classType",Constant.DEFULT_CLASS_TYPE);
//                    classBaseInfos = jwClassBaseInfoDAO.queryList(classQueryMap,"id","asc");
//                }
//                classBaseInfos = classBaseInfos.size() >= Constant.DEFULT_CLASS_NUM ? classBaseInfos.subList(0,Constant.DEFULT_CLASS_NUM) : classBaseInfos;
//                rtnMap.put(0, "");
//                rtnMap.put(1, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
//                rtnMap.put(2,jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
//                rtnMap.put(3, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
//                rtnMap.put(4, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
//                rtnMap.put(5, jwTeacherBaseInfo.getTeacherCourse() + "  " + getClassRandom(classBaseInfos) + "  " + "  "+getRoomRandom(roomList));
//                rtnMap.put(6, "");
//                rtnMap.put(7, "");
//                rtnMap.put(8, "");
//                rtnMap.put(10, "");
//                rtnMap.put(11, "");
//                rtnMap.put(12, "");
//            }
//        }else if ("student".equals(type)){
//            rtnMap.put(0, "");
//            rtnMap.put(1, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(2, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(3, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(4, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(5, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(6, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(7, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(8, getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(10,getTeacherCourse(teacherBaseInfos)+"\n"+getRoomRandom(roomList));
//            rtnMap.put(11, "");
//            rtnMap.put(12, "");
//        }else if ("room".equals(type)){
//            rtnMap.put(0, "");
//            rtnMap.put(1, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
//            rtnMap.put(2, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
//            rtnMap.put(3, "");
//            rtnMap.put(4, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
//            rtnMap.put(5, "");
//            rtnMap.put(6, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
//            rtnMap.put(7, "");
//            rtnMap.put(8, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
//            rtnMap.put(10, getTeacherCourse(teacherBaseInfos,tnId,jwScheduleTask.getGrade()));
//            rtnMap.put(11, "");
//            rtnMap.put(12, "");
//        }
//        List<List<String>> list1  = new ArrayList<>();
//        List<String> list2;
//        for (int i = list.size();i>0;i--){
//            list2 = new ArrayList<>();
//            for (int j = count;j>0;j--){
//                if (i==5 && j==1){
//                    //星期一第7节不排课
//                    list2.add("");
//                }else {
//                    list2.add(getCourse(rtnMap));
//                }
//            }
//            list1.add(list2);
//        }
//        courseResultView.setWeek(list1);
//        return courseResultView;
        return null;
    }

    @Override
    public Map<String,Object> getAllCourseResult(Integer taskId,Integer tnId) throws IOException, ParseException {

        return getCourseResult(tnId,taskId);
    }


    /**
     * 获取排课结果状态
     * @param taskId
     * @param tnId
     * @return
     */
    @Override
    public String getSchduleResultStatus(Integer taskId, Integer tnId) {
        return FileOperation.readerTxtString(taskId, tnId, FileOperation.SCHEDULE_RESULT);
    }
    /**
     * 初始化排课参数
     * @param taskId
     * @param tnId
     * @return
     */
    @Override
    public boolean InitParmasFile(final Integer taskId, final Integer tnId) throws IOException {
        boolean result = false;

        List<StringBuffer> admClassRuleBuffers = getClassRule(taskId,1);//行政班 不排课

        result = printBuffers(tnId,taskId,admClassRuleBuffers, FileOperation.ADMIN_CLASS_NON_DISPACHING);

        List<StringBuffer> eduClassRuleBuffers = getClassRule(taskId,1);//走读班 不排课

        result = printBuffers(tnId,taskId,eduClassRuleBuffers, FileOperation.CLASS_NON_DISPACHING);

        List<StringBuffer> admClassInfoBuffers = getClassInfo(taskId,tnId);//行政班基础信息

        result = printBuffers(tnId,taskId,admClassInfoBuffers, FileOperation.CLASS_INFO);

        List<StringBuffer> courseRuleBuffers = getCourseRule(taskId);//课程不排课

        result = printBuffers(tnId,taskId,courseRuleBuffers, FileOperation.COURSE_NON_DISPACHING);

        List<StringBuffer> teachDataBuffers = getTeachDateInfo(taskId,tnId);//基础规则设置

        result = printBuffers(tnId,taskId,teachDataBuffers, FileOperation.COURSE_TIMESLOTS);

        List<StringBuffer> gradeNonDisBuffers = getGradeNonDispaching(taskId,1);//年级不排课

        result = printBuffers(tnId,taskId,gradeNonDisBuffers, FileOperation.GRAD_NON_DISPACHING);

        List<StringBuffer> courseInfomationBuffers = courseInfomation(taskId,tnId);//课程信息

        result = printBuffers(tnId,taskId,courseInfomationBuffers, FileOperation.COURSE_INFORMATION);

        List<StringBuffer> teacherSettingBuffers = teachersSetting(taskId,tnId);//教师设置

        result = printBuffers(tnId,taskId,teacherSettingBuffers, FileOperation.TEACHERS_SETTING);
        LOGGER.info("===================参数序列化结果:"+result+"===================");
        if(result) {
            new Thread() {
                public void run() {
                    LOGGER.info("=线程启动=" + System.currentTimeMillis());
                    LOGGER.info("排课状态:正在排课");
                    String path = FileOperation.getParamsPath(tnId, taskId);
                    TimeTabling timeTabling = new TimeTabling();
                    timeTabling.runTimetabling(path, path);
                    LOGGER.info("排课状态:完成排课");
                }
            }.start();
        }
        LOGGER.info("===================已完成异步调用排课===================");

        return result;
    }

    private boolean printBuffers(Integer tnId,Integer taskId ,List<StringBuffer> classRuleBuffers,String fileName) throws IOException {
        boolean flag = FileOperation.creatTxtFile(tnId,taskId,fileName);
        if (flag) {
            String str = "";
            for (StringBuffer stringBuffer : classRuleBuffers)
                str += stringBuffer.toString();
            flag = FileOperation.writeTxtFile(str);
        }
        return flag;
    }

    /**
     * 教师设置
     * @return
     */
    private List<StringBuffer> teachersSetting(Integer taskId,Integer tnId) {

        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String teacherGrade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        String tableName = ParamsUtils.combinationTableName("teacher", tnId);


        Map map = new HashMap();
        map.put("tableName",tableName);
        map.put("searchKey", "teacher_grade");
        map.put("searchValue", teacherGrade);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);

        for (int j = 0; j < linkedHashMaps.size(); j++) {
            LinkedHashMap<String, Object> dataLinkedMap = linkedHashMaps.get(j);
            StringBuffer stringBuffer = new StringBuffer();
            Integer teacherId=0,courseId=0;
            for (Iterator iter = dataLinkedMap.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                if(strKey.equals("id")){
                    teacherId=Integer.valueOf(strObj.toString());
                    stringBuffer.append(teacherId);//ID
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("teacher_name")){
                    stringBuffer.append(" ");//姓名
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("teacher_major_type")){
                    courseId=getCourseId(strObj.toString());
                    stringBuffer.append(courseId);//所授课程编号
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("teacher_max_take_class")){
                    stringBuffer.append(strObj);//最大带班数
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("teacher_class")){
                    stringBuffer.append(1); //指定班级个数
                    stringBuffer.append(FileOperation.STR_SPLIT);
                    stringBuffer.append(getClassId(tnId,strObj.toString(),teacherGrade));//指定所带班级
                    stringBuffer.append(FileOperation.STR_SPLIT);



                    Map teacherMap=new HashMap();
                    teacherMap.put("taskId",taskId);
                    teacherMap.put("teacherId",teacherId);

                    JwTeacherRule jwTeacherRule= iJwTeacherRuleDAO.queryOne(teacherMap,"id","asc");
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(jwTeacherRule.getMon());
                    stringBuilder.append(jwTeacherRule.getTues());
                    stringBuilder.append(jwTeacherRule.getWed());
                    stringBuilder.append(jwTeacherRule.getThur());
                    stringBuilder.append(jwTeacherRule.getFri());
                    stringBuilder.append(jwTeacherRule.getSut());
                    stringBuilder.append(jwTeacherRule.getSun());
                    String str=stringBuilder.toString();

                    String[] arr=str.split("0");

                    stringBuffer.append(arr.length-1); //不排课个数
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    String m=getWeekNonAndDayNon(jwTeacherRule.getMon(),1),//周1不排课节点
                            tu=getWeekNonAndDayNon(jwTeacherRule.getTues(),2),//周2不排课节点
                            w=getWeekNonAndDayNon(jwTeacherRule.getWed(),3),//周3不排课节点
                            th=getWeekNonAndDayNon(jwTeacherRule.getThur(),4),//周4不排课节点
                            f=getWeekNonAndDayNon(jwTeacherRule.getFri(),5),//周5不排课节点
                            s=getWeekNonAndDayNon(jwTeacherRule.getSut(),6),//周6不排课节点
                            su=getWeekNonAndDayNon(jwTeacherRule.getSun(),7);//周7不排课节点
                    if(!StringUtils.isBlank(m)) {
                        stringBuffer.append(m);
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }
                    if(!StringUtils.isBlank(tu)) {
                        stringBuffer.append(tu);
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }
                    if(!StringUtils.isBlank(w)) {
                        stringBuffer.append(w);
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }
                    if(!StringUtils.isBlank(th)) {
                        stringBuffer.append(th);
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }
                    if(!StringUtils.isBlank(f)) {
                        stringBuffer.append(f);
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }
                    if(!StringUtils.isBlank(s)) {
                        stringBuffer.append(s);
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }
                    if(!StringUtils.isBlank(su)) {
                        stringBuffer.append(su);
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }

                    stringBuffer.append(1); //连上规则
                    stringBuffer.append(FileOperation.STR_SPLIT);
                    stringBuffer.append(1); //规则个数
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    Map conRuleMap=new HashMap();
                    conRuleMap.put("tnId",tnId);
                    conRuleMap.put("courseId",courseId);
                    conRuleMap.put("taskId",taskId);
                    conRuleMap.put("teacherId",teacherId);
                    JwBaseConRule jwBaseConRule=iJwBaseConRuleDAO.queryOne(conRuleMap,"id","asc");

                    stringBuffer.append((jwBaseConRule.getDayConType()==1?3:4)); //连上节数
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(jwBaseConRule.getImportantType()); //连上规则权重
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(1); //教案平齐 都是1
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    JwBaseJaqpRule jwBaseJaqpRule=iJwBaseJaqpRuleDAO.queryOne(conRuleMap,"id","asc");

                    stringBuffer.append(jwBaseJaqpRule.getImportantType()); //教案平齐权重
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    JwBaseWeekRule jwBaseWeekRule=iJwBaseWeekRuleDAO.queryOne(conRuleMap,"id","asc");

                    stringBuffer.append(jwBaseWeekRule.getWeekType()); //周任课规则
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(jwBaseWeekRule.getImportantType()); //周任课规则权重
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    JwBaseDayRule jwBaseDayRule=iJwBaseDayRuleDAO.queryOne(conRuleMap,"id","asc");
                    stringBuffer.append(jwBaseDayRule.getDayType()); //日任课规则
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(jwBaseDayRule.getImportantType()); //日任课规则权重
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
            }
            stringBuffer.append(FileOperation.LINE_SPLIT);

            stringBuffers.add(stringBuffer);
        }


        return stringBuffers;
    }

    private String getWeekNonAndDayNon(String str,Integer week) {

        String result = "";

        String[] arr = str.split("0");

        for (Integer i = 0; i < str.length(); i++) {
            String s = str.charAt(i) + "";
            if (s.equals("0"))
                result += week + FileOperation.CHAR_SPLIT + i;
        }
        return result;
    }


    private Integer getCourseId(String courseName) {
        Map map = new HashMap();
        map.put("courseName", courseName);
        CourseBaseInfo courseBaseInfo = iCourseBaseInfoDAO.queryOne(map, "id", "asc");
        Integer courseId = courseBaseInfo == null ? 0 : Integer.valueOf(courseBaseInfo.getId().toString());
        return courseId;
    }

    private Integer getClassId(Integer tnId,String className,String grade) {

        Integer classId = 0;

        String tableName = ParamsUtils.combinationTableName("class_adm", tnId);


        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("searchKey", "class_name");
        map.put("searchValue", className);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);

        for (int j = 0; j < linkedHashMaps.size(); j++) {
            LinkedHashMap<String, Object> dataLinkedMap = linkedHashMaps.get(j);
            for (Iterator iter = dataLinkedMap.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                if (strKey.equals("class_grade")) {
                    if (!grade.equals(strObj))
                        break;
                }
                if (strKey.equals("id")) {
                    classId = Integer.valueOf(strObj.toString());
                    break;
                }
            }
        }
        return classId;
    }
    /**
     * 年级不排课
     * @param taskId
     * @param type
     * @return
     */
    private List<StringBuffer> getGradeNonDispaching(Integer taskId,Integer type) {
        Map map = new HashMap();
        map.put("taskId", taskId);
        map.put("classType", type);
        List<JwClassRule> jwClassRules = iJwClassRuleDAO.queryList(map, "id", "asc");

        List<StringBuffer> stringBuffers = new ArrayList<>();
        StringBuffer stringBuffer1 = new StringBuffer();

        JwClassRule jwClassRule = jwClassRules.get(0);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(converGradeNon(jwClassRule.getMon()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwClassRule.getTues()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwClassRule.getWed()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwClassRule.getTues()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwClassRule.getFri()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwClassRule.getSut()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwClassRule.getSun()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer);

        return stringBuffers;
    }

    private String converGradeNon(String str) {
        String result = "";
        for (Integer i = 0; i < str.length(); i++) {
            result += 0;
            result += FileOperation.CHAR_SPLIT;
        }
        return result;
    }


    private List<StringBuffer> courseInfomation(Integer taskId,Integer tnId){

        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        Map map=new HashMap();
        map.put("tnId",tnId);
        map.put("gradeId",jwScheduleTask.getGrade());
        List<CourseManageVo> courseManageVos=iCourseManageDAO.selectCourseManageInfo(map);


        for(CourseManageVo courseManageVo:courseManageVos) {
            StringBuffer stringBuffer = new StringBuffer();
            CourseBaseInfo courseBaseInfo = iCourseBaseInfoDAO.fetch(courseManageVo.getCourseId());
            stringBuffer.append(courseBaseInfo.getId());
            stringBuffer.append(FileOperation.STR_SPLIT);

//            stringBuffer.append(courseBaseInfo.getCourseName());
            stringBuffer.append(" ");
            stringBuffer.append(FileOperation.STR_SPLIT);

            Map jwCourseMap = new HashMap();
            jwCourseMap.put("tnId", tnId);
            jwCourseMap.put("courseId", courseBaseInfo.getId());
            jwCourseMap.put("taskId", taskId);
            JwCourse jwCourse = jwCourseDAO.queryOne(jwCourseMap, "id", "asc");

            stringBuffer.append(converCourseHour(jwCourse.getCourseHour(), 0));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(converCourseHour(jwCourse.getCourseHour(), 1));
            stringBuffer.append(FileOperation.STR_SPLIT);

            stringBuffer.append(courseManageVo.getCourseType());
            stringBuffer.append(FileOperation.STR_SPLIT);

            Map mergeMap = new HashMap();
            mergeMap.put("tnId", tnId);
            mergeMap.put("taskId", taskId);
            mergeMap.put("courseId", courseBaseInfo.getId());
            List<MergeClassInfoVo> mergeClassInfoVos = mergeClassDAO.selectMergeClassInfo(mergeMap);


            stringBuffer.append((mergeClassInfoVos==null||mergeClassInfoVos.size()<=0?0:mergeClassInfoVos.size()));
            stringBuffer.append(FileOperation.STR_SPLIT);

            for(MergeClassInfoVo mergeClassInfoVo:mergeClassInfoVos) {
                String[] merArr = mergeArr(mergeClassInfoVo.getClassIds());
                stringBuffer.append(merArr.length);
                stringBuffer.append(FileOperation.STR_SPLIT);
                for (String str : merArr) {
                    stringBuffer.append(str);
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
            }
            stringBuffer.append(FileOperation.LINE_SPLIT);
            stringBuffers.add(stringBuffer);
        }
        return stringBuffers;
    }

    private String[] mergeArr(String str){
        return str.split(",");
    }

    private String converCourseHour(String hour,Integer index) {

        String result = "0";

        if (StringUtils.isBlank(hour))
            return result;

        String[] arr = hour.split("\\+");

        if (index < arr.length)
            result = arr[index];

        return result;
    }

    /**
     * 基础信息设置
     * @param taskId
     * @param tnId
     * @return
     */
    private List<StringBuffer> getTeachDateInfo(Integer taskId,Integer tnId) {

        Map map = new HashMap();
        map.put("taskId", taskId);
        map.put("tnId", tnId);
        List<JwTeachDate> jwTeachDates = iJwTeachDateDAO.queryList(map, "tid", "asc");

        List<StringBuffer> stringBuffers = new ArrayList<>();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(jwTeachDates.size());
        stringBuffer1.append(FileOperation.LINE_SPLIT);

        String sw = "";
        for (JwTeachDate jwTeachDate : jwTeachDates) {
            Integer week = ConvertUtil.converWeek(jwTeachDate.getTeachDate());
            sw += week + FileOperation.STR_SPLIT;
        }
        stringBuffer1.append(sw);
        stringBuffer1.append(FileOperation.LINE_SPLIT);

        stringBuffer1.append(9);
        stringBuffer1.append(FileOperation.LINE_SPLIT);

        for (JwTeachDate jwTeachDate : jwTeachDates) {
            String teachDetail = jwTeachDate.getTeachDetail();
            String[] teachDetaArr = teachDetail.split("_");
            String sd="";
            System.out.print(teachDetaArr.length);
            for (String str : teachDetaArr) {
                sd += str.length() + FileOperation.STR_SPLIT;
            }
            for(int i=teachDetaArr.length;i<3;i++){  //空的 补0
                sd += 0 + FileOperation.STR_SPLIT;
            }
            stringBuffer1.append(sd);
            stringBuffer1.append(FileOperation.LINE_SPLIT);
        }
        stringBuffers.add(stringBuffer1);
        return stringBuffers;
    }


    /**
     * 获取年级课程信息
     * @param tnId
     * @param grade
     * @param classType
     * @return
     */
    private StringBuffer  gradeCourseInfo(Integer tnId,Integer grade,Integer classType) {
        StringBuffer stringBuffer = new StringBuffer();

        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("gradeCode", grade);
        Grade gradeObj = iGradeDAO.queryOne(map, "id", "asc");
        if (gradeObj != null) {
            Map courMap = new HashMap();
            courMap.put("tnId", tnId);
            courMap.put("gradeId", grade);
            courMap.put("classType", classType);
            List<CourseManageVo> courseManageVos = iCourseManageDAO.selectCourseManageInfo(courMap);

            //行政班 文科班&理科班
            if (gradeObj.getClassType() == 1 || gradeObj.getClassType() == 3) {
                stringBuffer.append(courseManageVos.size());
                stringBuffer.append(FileOperation.STR_SPLIT);
                for (CourseManageVo courseManageVo : courseManageVos) {
                    stringBuffer.append(courseManageVo.getCourseId());
                    stringBuffer.append(FileOperation.CHAR_SPLIT);
                }
            } else if (gradeObj.getClassType() == 2) {   //走读班

            }
        }
        return stringBuffer;
    }

    /**
     * 课程不排课规则
     * @param taskId
     * @return
     */
    private List<StringBuffer> getCourseRule(Integer taskId) {
        Map map = new HashMap();
        map.put("taskId", taskId);
        List<JwCourseRule> jwCourseRules = jwCourseRuleDAO.queryList(map, "id", "asc");

        List<StringBuffer> stringBuffers = new ArrayList<>();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(jwCourseRules.size());
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer1);
        for (JwCourseRule jwCourseRule : jwCourseRules) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(jwCourseRule.getCourseId());
            stringBuffer.append(FileOperation.LINE_SPLIT);
            stringBuffer.append(getCharStr(jwCourseRule.getMon()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwCourseRule.getTues()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwCourseRule.getWed()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwCourseRule.getTues()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwCourseRule.getFri()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwCourseRule.getSut()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwCourseRule.getSun()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(FileOperation.LINE_SPLIT);
            stringBuffers.add(stringBuffer);
        }

        return stringBuffers;
    }
    /**
     * 班级不排课
     * @param taskId
     * @param tnId
     * @return
     */
    private List<StringBuffer> getClassInfo(Integer taskId,Integer tnId) {

        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String classGrade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        String tableName = ParamsUtils.combinationTableName("class_adm", tnId);

        Map map = new HashMap();
        map.put("tableName",tableName);
        map.put("searchKey", "class_grade");
        map.put("searchValue", classGrade);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);


        for (int j = 0; j < linkedHashMaps.size(); j++) {

            LinkedHashMap<String, Object> dataLinkedMap = linkedHashMaps.get(j);
            StringBuffer stringBuffer = new StringBuffer();
            for (Iterator iter = dataLinkedMap.entrySet().iterator(); iter.hasNext(); ) {

                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                if(strKey.equals("id")){
                    stringBuffer.append(strObj);
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("class_name")){
                    stringBuffer.append(" ");
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("class_type")){
                    stringBuffer.append(ConvertUtil.converClassTypeByTag(strObj.toString()));
                    stringBuffer.append(FileOperation.STR_SPLIT);
                    stringBuffer.append(gradeCourseInfo(tnId,ConvertUtil.converGrade(classGrade),ConvertUtil.converClassTypeByTag(strObj.toString())));
                }
            }
            stringBuffer.append(FileOperation.LINE_SPLIT);

            stringBuffers.add(stringBuffer);
        }

        return stringBuffers;
    }

    /**
     * 行政班&走读班
     * @param taskId
     * @param type
     * @return
     */
    private List<StringBuffer> getClassRule(Integer taskId,Integer type) {
//        Map map=new HashMap();
//        map.put("taskId",taskId);
//        map.put("classType",type);
//        List<JwClassRule> jwClassRules = iJwClassRuleDAO.queryList(map, "id", "asc");
//
        List<StringBuffer> stringBuffers = new ArrayList<>();
//        StringBuffer stringBuffer1 = new StringBuffer();
//        stringBuffer1.append(jwClassRules.size());
//        stringBuffer1.append(FileOperation.LINE_SPLIT);
//        stringBuffers.add(stringBuffer1);
//        for (JwClassRule jwClassRule : jwClassRules) {
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append(jwClassRule.getClassId());
//            stringBuffer.append(FileOperation.LINE_SPLIT);
//            stringBuffer.append(getCharStr(jwClassRule.getMon()));
//            stringBuffer.append(FileOperation.STR_SPLIT);
//            stringBuffer.append(getCharStr(jwClassRule.getTues()));
//            stringBuffer.append(FileOperation.STR_SPLIT);
//            stringBuffer.append(getCharStr(jwClassRule.getWed()));
//            stringBuffer.append(FileOperation.STR_SPLIT);
//            stringBuffer.append(getCharStr(jwClassRule.getTues()));
//            stringBuffer.append(FileOperation.STR_SPLIT);
//            stringBuffer.append(getCharStr(jwClassRule.getFri()));
//            stringBuffer.append(FileOperation.STR_SPLIT);
//            stringBuffer.append(getCharStr(jwClassRule.getSut()));
//            stringBuffer.append(FileOperation.STR_SPLIT);
//            stringBuffer.append(getCharStr(jwClassRule.getSun()));
//            stringBuffer.append(FileOperation.STR_SPLIT);
//            stringBuffer.append(FileOperation.LINE_SPLIT);
//            stringBuffers.add(stringBuffer);
//        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(0);
        stringBuffers.add(stringBuffer);
        return stringBuffers;
    }


    private String getCharStr(String str) {
        String result = "";
        for (Integer i = 0; i < str.length(); i++) {
            result += str.charAt(i);
            result += FileOperation.CHAR_SPLIT;
        }
        return result;
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
//        if (teacherBaseInfos.size()==0)return "";
//        java.util.Random random=new java.util.Random();// 定义随机类
//        TeacherBaseDto teacherBaseDto = teacherBaseInfos.get(random.nextInt(teacherBaseInfos.size()));
//        Map<String,Object> classQueryMap = Maps.newHashMap();
//        classQueryMap.put("tnId",tnId);
//        classQueryMap.put("grade",grade);
//        classQueryMap.put("className",teacherBaseDto.getCourseName());
//        classQueryMap.put("classType",Constant.SUBJECT_CLASS_TYPE);
//        List<JwClassBaseInfo> classBaseInfos = jwClassBaseInfoDAO.like(classQueryMap,"id","asc");
//        if (classBaseInfos.size()==0){
//            classQueryMap.put("className",null);
//            classQueryMap.put("classType",Constant.DEFULT_CLASS_TYPE);
//            classBaseInfos = jwClassBaseInfoDAO.queryList(classQueryMap,"id","asc");
//        }
//        return teacherBaseDto.getCourseName()+"\n("+teacherBaseDto.getTeacherName()+")   "+getClassRandom(classBaseInfos);
        return null;
    }
    private String getRoomRandom(List<JwRoom> rooms){
        java.util.Random random=new java.util.Random();// 定义随机类
        if (rooms.size()==0)return "";
        JwRoom jwRoom = rooms.get(random.nextInt(rooms.size()));
        return jwRoom.getRoomName();

    }
//    private String getClassRandom(List<JwClassBaseInfo> classBaseInfos){
//        java.util.Random random=new java.util.Random();// 定义随机类
//        if (classBaseInfos.size()==0)return "";
//        JwClassBaseInfo jwClassBaseInfo = classBaseInfos.get(random.nextInt(classBaseInfos.size()));
//        return jwClassBaseInfo.getClassName();
//
//    }

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
            Map<Integer,String> classMap = this.getClassByTnIdAndTaskId(tnId,taskId);
            StringBuffer buffer1 = new StringBuffer();
            Iterator<Integer> $i = classMap.keySet().iterator();
            while ($i.hasNext()){
                buffer1.append(classMap.get($i.next())).append("|");
            }
            if (buffer1.length() > 0) {
                buffer1.delete(buffer1.length() - 1, buffer1.length());
            }
            resultMap.put("room",buffer1.toString());
            LOGGER.info("************获取时间设置 E************");

            LOGGER.info("************获取年级信息设置 S************");
            JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);
            List<LinkedHashMap<String, Object>> classes = new ArrayList();
            Map<String, Object> param = new HashMap<>();
            param.put("gradeCode", jwScheduleTask.getGrade());
            param.put("tnId", tnId);
            Grade gradeServiceOne = (Grade) gradeService.queryOne(param);
            String grade = gradeServiceOne.getGrade();
            LOGGER.info("************获取年级信息设置 E************");

            LOGGER.info("************获取课表 S************");
            Map<Integer,String> courses = getCourseByTnIdAndTaskId(tnId,taskId);
            CharSource main = Files.asCharSource(new File("classpath:config/admin_course_0.txt"), Charset.defaultCharset());
            List<String> allCourseList =  main.readLines();
            List<List<String>> weekCourseList;
            for (String courseLine : allCourseList){
                String[] courseInfo = courseLine.split(Constant.COURSE_TABLE_LINE_SPLIT_CLASS);
                String courseStr = courseInfo[1];
                Integer classId = Integer.valueOf(courseInfo[0]);
                String className = classMap.get(classId);
                String[] weekCourses = courseStr.split(Constant.COURSE_LINE_TABLE_SPLIT_T);

                weekCourseList = new LinkedList<>();
                List<String> dayCourseList;
                for (String dayCourseStr : weekCourses){
                    dayCourseList = new LinkedList<>();
                    String[] dayCourses = dayCourseStr.split(Constant.COURSE_TABLE_LINE_SPLIT_CHAR);
                    for (int i = 0 ; i < dayCourses.length ; i++){
                        String course = courses.get(Integer.valueOf(dayCourses[i]));
                        String teacherName = this.getTeacherByCourseAndClass(course,grade,className,tnId,taskId);
                        //课程转换
                        course = course + teacherName;
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

    /**
     * 获取班级信息
     * @param tnId
     * @param taskId
     * @return
     */
    private Map<Integer,String> getClassByTnIdAndTaskId(int tnId,int taskId){
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
//        boolean isExistTeaching = GradeTypeEnum.Teaching.getCode().equals(gradeType);
//        if (isExistTeaching) {
            //是教学班+是走班课程
            classes = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, grade,Constant.CLASS_EDU);
//        } else {
//            //是教学班+不是走班课程
//            classes = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, grade,Constant.CLASS_ADM);
//            //不教学班+是走班课程
//        }
        Map<Integer,String> rtnMap = new HashMap<>();
        for (LinkedHashMap<String, Object> ss : classes){
            rtnMap.put(Integer.valueOf(ss.get("id").toString()),ss.get("class_name").toString());
        }
        return rtnMap;
    }

    /**
     * 获取课程信息
     * @param tnId
     * @param taskId
     * @return
     */
    private Map<Integer,String> getCourseByTnIdAndTaskId(int tnId,int taskId){

        List<CourseBaseInfo> list = courseBaseInfoDAO.findAll("id","asc");
        Map<Integer,String> courseMap = new LinkedHashMap();
        for (CourseBaseInfo courseBaseInfo  : list){
            courseMap.put(Integer.valueOf(courseBaseInfo.getId().toString()),courseBaseInfo.getCourseName());
        }
        //0的时候是没有课程
        courseMap.put(0,"没课");
        return courseMap;
    }

    /**
     * 获取教师
     * @return
     */
    private String getTeacherByCourseAndClass(String course,String grade,String className,int tnId,int taskId){
        List<Map<String,Object>> params = new ArrayList<>();
        Map<String,Object> param = new HashMap<>();
        param.put("key","tnId");
        param.put("eq","=");
        param.put("value",tnId);
        params.add(param);
        param = new HashMap<>();
        param.put("key","teacher_grade");
        param.put("eq","=");
        param.put("value",grade);
        params.add(param);
        param = new HashMap<>();
        param.put("key","teacher_class");
        param.put("eq","like");
        param.put("value","%"+className+"%");
        params.add(param);
        param = new HashMap<>();
        param.put("key","teacher_major_type");
        param.put("eq","=");
        param.put("value",course);
        params.add(param);
        List<Map<String,Object>> rtnList = exiTenantConfigInstanceService.likeTeacherByParams(params);
        return rtnList.size()>0?rtnList.get(0).get("teacher_name").toString():null;
    }

    public static void main(String[] args) throws IOException {
        CharSource main = Files.asCharSource(new File("/Users/yangyongping/Desktop/yqhc/zgk-saas/saas-service/src/main/resources/config/admin_course_0.txt"), Charset.defaultCharset());
        List<String> list = main.readLines();
        for (String ss : list){
            ss.split(Constant.COURSE_TABLE_LINE_SPLIT_CLASS);
        }
    }

}
