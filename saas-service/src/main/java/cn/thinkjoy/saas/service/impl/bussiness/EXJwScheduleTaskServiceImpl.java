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
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.IGradeService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.bussiness.IEXJwScheduleTaskService;
import cn.thinkjoy.saas.service.common.ConvertUtil;
import cn.thinkjoy.saas.service.common.FileOperation;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import cn.thinkjoy.saas.timetable.TimeTabling;
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
    IJwCourseGapRuleDAO iJwCourseGapRuleDAO;
//    @Autowired
//    private

    @Autowired
    private RedisRepository<String,Object> redis;



    @Override
    public CourseResultView getCourseResult(String type,Integer taskId,Integer tnId,Map<String,Object> paramsMap) {

        switch (type){
            case Constant.TABLE_TYPE_TEACHER:
                break;
            case Constant.STUDENT:
                break;
            case Constant.TABLE_TYPE_CLASS:
                return this.getClassCourseTable(tnId,taskId,paramsMap);
            default:
                break;
        }
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

        List<StringBuffer> parametersBuffers =getParameters();

        result = printBuffers(tnId,taskId,parametersBuffers, FileOperation.PARMETERS);

        List<StringBuffer> admClassRuleBuffers = getClassRule(taskId,1);//行政班 不排课

        result = printBuffers(tnId,taskId,admClassRuleBuffers, FileOperation.ADMIN_CLASS_NON_DISPACHING);

        List<StringBuffer> eduClassRuleBuffers = getClassRule(taskId,0);//走读班 不排课

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
                    try {
                        String result = getSchduleResultStatus(taskId, tnId);
                        if (Integer.valueOf(result) == 1)
                            getAllCourseResult(taskId, tnId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    LOGGER.info("排课状态:完成排课");
                }
            }.start();
        }
        LOGGER.info("===================已完成异步调用排课===================");

        return result;
    }


    private String converTag(String str){
        str = str.replace("1", "@").replace("0", "|");

        str = str.replace("@", "0").replace("|", "1");


        return str;
    }

    private boolean printBuffers(Integer tnId,Integer taskId ,List<StringBuffer> classRuleBuffers,String fileName) throws IOException {
        boolean flag = FileOperation.creatTxtFile(tnId,taskId,fileName);
        if (flag) {
            String str = "";
            for (StringBuffer stringBuffer : classRuleBuffers)
                str += stringBuffer.toString();
            if (str.lastIndexOf(FileOperation.LINE_SPLIT) > 0)
                str = str.substring(0, str.lastIndexOf(FileOperation.LINE_SPLIT));

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
                    stringBuffer.append(strObj.toString());//姓名
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

                    String[] classStr=strObj.toString().split("、");

                    stringBuffer.append(classStr.length); //指定班级个数
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    for(String str:classStr) {
                        stringBuffer.append(getClassId(tnId, str.toString(), teacherGrade));//指定所带班级
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }

                    Map teacherMap=new HashMap();
                    teacherMap.put("taskId",taskId);
                    teacherMap.put("teacherId",teacherId);

                    JwTeacherRule jwTeacherRule= iJwTeacherRuleDAO.queryOne(teacherMap,"id","asc");

                    if(jwTeacherRule==null){
                        stringBuffer.append(0);
                        stringBuffer.append(FileOperation.STR_SPLIT);
//                        stringBuffer.append(0);
//                        stringBuffer.append(FileOperation.STR_SPLIT);
//                        stringBuffer.append(0);
//                        stringBuffer.append(FileOperation.STR_SPLIT);
//                        stringBuffer.append(0);
//                        stringBuffer.append(FileOperation.STR_SPLIT);
//                        stringBuffer.append(0);
//                        stringBuffer.append(FileOperation.STR_SPLIT);
//                        stringBuffer.append(0);
//                        stringBuffer.append(FileOperation.STR_SPLIT);
//                        stringBuffer.append(0);
//                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(jwTeacherRule.getMon());
                        stringBuilder.append(jwTeacherRule.getTues());
                        stringBuilder.append(jwTeacherRule.getWed());
                        stringBuilder.append(jwTeacherRule.getThur());
                        stringBuilder.append(jwTeacherRule.getFri());
                        stringBuilder.append(jwTeacherRule.getSut());
                        stringBuilder.append(jwTeacherRule.getSun());
                        String str = stringBuilder.toString();

                        String[] arr = str.split("0");

                        stringBuffer.append(arr.length - 1); //不排课个数
                        stringBuffer.append(FileOperation.STR_SPLIT);

                        String m = getWeekNonAndDayNon(jwTeacherRule.getMon(), 1),//周1不排课节点
                                tu = getWeekNonAndDayNon(jwTeacherRule.getTues(), 2),//周2不排课节点
                                w = getWeekNonAndDayNon(jwTeacherRule.getWed(), 3),//周3不排课节点
                                th = getWeekNonAndDayNon(jwTeacherRule.getThur(), 4),//周4不排课节点
                                f = getWeekNonAndDayNon(jwTeacherRule.getFri(), 5),//周5不排课节点
                                s = getWeekNonAndDayNon(jwTeacherRule.getSut(), 6),//周6不排课节点
                                su = getWeekNonAndDayNon(jwTeacherRule.getSun(), 7);//周7不排课节点
                        if (!StringUtils.isBlank(m)) {
                            stringBuffer.append(m);
                            stringBuffer.append(FileOperation.STR_SPLIT);
                        }
                        if (!StringUtils.isBlank(tu)) {
                            stringBuffer.append(tu);
                            stringBuffer.append(FileOperation.STR_SPLIT);
                        }
                        if (!StringUtils.isBlank(w)) {
                            stringBuffer.append(w);
                            stringBuffer.append(FileOperation.STR_SPLIT);
                        }
                        if (!StringUtils.isBlank(th)) {
                            stringBuffer.append(th);
                            stringBuffer.append(FileOperation.STR_SPLIT);
                        }
                        if (!StringUtils.isBlank(f)) {
                            stringBuffer.append(f);
                            stringBuffer.append(FileOperation.STR_SPLIT);
                        }
                        if (!StringUtils.isBlank(s)) {
                            stringBuffer.append(s);
                            stringBuffer.append(FileOperation.STR_SPLIT);
                        }
                        if (!StringUtils.isBlank(su)) {
                            stringBuffer.append(su);
                            stringBuffer.append(FileOperation.STR_SPLIT);
                        }
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
                result += week + FileOperation.CHAR_SPLIT + (i+1);
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
        StringBuffer stringBuffer = new StringBuffer();
            if(jwClassRules==null||jwClassRules.size()<=0)
                return ObjIsNothing();

        JwClassRule jwClassRule = jwClassRules.get(0);


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
            stringBuffer.append(courseBaseInfo.getCourseName());
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
                            Integer maxNum=14;
        stringBuffer1.append(maxNum);
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

        Map params = new HashMap<>();
        params.put("taskId", taskId);
        JwCourseGapRule rule = (JwCourseGapRule)iJwCourseGapRuleDAO.queryOne(params,"id","asc");
        String[] rules = rule.getRule().split("@@");
        String rul="";
        int len = rules.length;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                String[] detail = rules[i].split(":");
                if (detail.length < 2)
                    continue;
                rul+=detail[1]+FileOperation.STR_SPLIT;
            }
        }
        for(int i=rules.length;i<maxNum;i++) {  //空的 补0
            rul += 0 + FileOperation.STR_SPLIT;
        }
        stringBuffer1.append(rul+FileOperation.LINE_SPLIT);
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
                    stringBuffer.append(strObj);
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("class_type")){
                    stringBuffer.append(FileOperation.STR_SPLIT);
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

    private List<StringBuffer> ObjIsNothing(){
        List<StringBuffer> stringBuffers = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(0);
        stringBuffers.add(stringBuffer);
        return stringBuffers;
    }

    private List<StringBuffer> getParameters() {
        List<StringBuffer> stringBuffers = new ArrayList<>();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(101);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffer1.append(20);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffer1.append(200);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffer1.append(10);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer1);
        return stringBuffers;
    }
    /**
     * 行政班&走读班
     * @param taskId
     * @param type
     * @return
     */
    private List<StringBuffer> getClassRule(Integer taskId,Integer type) {
        List<StringBuffer> stringBuffers = new ArrayList<>();
        if(type==0)
            return ObjIsNothing();

        Map map=new HashMap();
        map.put("taskId",taskId);
        map.put("classType",type);
        List<JwClassRule> jwClassRules = iJwClassRuleDAO.queryList(map, "id", "asc");

        if(jwClassRules==null||jwClassRules.size()<=0)
            return ObjIsNothing();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(jwClassRules.size());
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer1);
        for (JwClassRule jwClassRule : jwClassRules) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(jwClassRule.getClassId());
            stringBuffer.append(FileOperation.LINE_SPLIT);
            stringBuffer.append(getCharStr(jwClassRule.getMon()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwClassRule.getTues()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwClassRule.getWed()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwClassRule.getThur()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwClassRule.getFri()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwClassRule.getSut()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(getCharStr(jwClassRule.getSun()));
            stringBuffer.append(FileOperation.STR_SPLIT);
            stringBuffer.append(FileOperation.LINE_SPLIT);
            stringBuffers.add(stringBuffer);
        }

        return stringBuffers;
    }


    private String getCharStr(String str) {
        String result = "";
        for (Integer i = 0; i < str.length(); i++) {
            result += str.charAt(i);
            result += FileOperation.CHAR_SPLIT;
        }
        result = converTag(result);
        return result;
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
//            List timeList = (List) timeConfigMap.get("list");
//            int time = Integer.valueOf(timeConfigMap.get("time").toString());
            int everyDaySection = Integer.valueOf(timeConfigMap.get("count").toString());
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
            String roomStr = buffer1.toString();
            resultMap.put("room",roomStr);
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
            List<String> allCourseList = null;
            Map<Integer,String> courses = getCourseByTnIdAndTaskId(tnId,taskId);
            try {
                String path = FileOperation.getParamsPath(tnId, taskId);
//                String path = "/Users/yangyongping/Desktop/yqhc/zgk-saas/saas-service/src/main/resources/config/admin_course_0.txt";
                CharSource main = Files.asCharSource(new File(path), Charset.defaultCharset());
                allCourseList = main.readLines();
            }catch (Exception e){
                throw new BizException("error","排课数据获取失败");
            }
            List<List<String>> weekCourseList;
            List<String> dayCourseList;
            for (String courseLine : allCourseList){
                String[] courseInfo = courseLine.split(Constant.COURSE_TABLE_LINE_SPLIT_CLASS);
                String courseStr = courseInfo[1];
                Integer classId = null;
                try {
                    classId = Integer.valueOf(courseInfo[0]);
                }catch (Exception e){
                    throw new BizException("error","班级不存在");
                }
                String className = classMap.get(classId);
                List<List<String>> weekTempCourses = spiltDay(everyDaySection,courseStr);
                weekCourseList = new LinkedList<>();

                for (List<String> dayCourseTempList : weekTempCourses){
                    dayCourseList = new LinkedList<>();

                    for (String dayCourse : dayCourseTempList){
                        String course = courses.get(Integer.valueOf(dayCourse));
                        String teacherName = this.getTeacherByCourseAndClass(course,grade,className,tnId,taskId);
                        //课程转换
                        dayCourseList.add(mergeTeacherAndCourse(course,teacherName));
                    }
                    weekCourseList.add(dayCourseList);
                }
                courseTables.add(weekCourseList);
            }
            resultMap.put("roomData",courseTables);
            LOGGER.info("************获取课表 E************");

            LOGGER.info("************存redis S************");
            this.redis.set(redisKey, JSON.json(resultMap));
            LOGGER.info("************存redis E************");
            return resultMap;
        } else {
            return JSON.parse(redis.get(redisKey).toString(), HashMap.class);
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
            classes = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, grade,Constant.CLASS_ADM);
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
        String tableName = "saas"+Constant.TIME_INTERVAL+tnId+Constant.TIME_INTERVAL+Constant.TABLE_TYPE_TEACHER+Constant.TIME_INTERVAL+"excel";
        List<Map<String,Object>> params = new ArrayList<>();
        Map<String,Object> param = new HashMap<>();
        param = new HashMap<>();
        param.put("key","teacher_grade");
        param.put("op","=");
        param.put("value",grade);
        params.add(param);
        param = new HashMap<>();
        param.put("key","teacher_class");
        param.put("op","like");
        param.put("value","%"+className+"%");
        params.add(param);
        param = new HashMap<>();
        param.put("key","teacher_major_type");
        param.put("op","=");
        param.put("value",course);
        params.add(param);
        List<Map<String,Object>> rtnList = exiTenantConfigInstanceService.likeTeacherByParams(tableName,params);
        return rtnList.size()>0?rtnList.get(0).get("teacher_name").toString():null;
    }

    /**
     * 获取教师
     * @return
     */
    private Map<String,Object> getTeacherByTeacherId(Integer teacherId,int tnId){
        String tableName = "saas"+Constant.TIME_INTERVAL+tnId+Constant.TIME_INTERVAL+Constant.TABLE_TYPE_TEACHER+Constant.TIME_INTERVAL+"excel";
        List<Map<String,Object>> params = new ArrayList<>();
        Map<String,Object> param = new HashMap<>();
        param = new HashMap<>();
        param.put("key","id");
        param.put("op","=");
        param.put("value",teacherId);
        params.add(param);
        List<Map<String,Object>> rtnList = exiTenantConfigInstanceService.likeTeacherByParams(tableName,params);
        return rtnList.size()>0?rtnList.get(0):null;
    }

    private List<List<String>> spiltDay(int everyDaySection,String tt){
        String[] tts = tt.split(Constant.COURSE_TABLE_LINE_SPLIT_CHAR);
        List<List<String>> lists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (int i = 0;i<tts.length;i++){
            if (i%everyDaySection == 0) {
                list = new ArrayList<>(everyDaySection);
            }
            list.add(tts[i]);
            if (i%everyDaySection == everyDaySection-1){
                lists.add(list);
            }
        }
        return lists;
    }

    private Object getMapKey(Map map,Object value) {
        Set set = map.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 获取班级课表
     * @param tnId
     * @param taskId
     * @param paramsMap
     * @return
     */
    private CourseResultView getClassCourseTable(int tnId,int taskId,Map<String,Object> paramsMap){
        CourseResultView courseResultView = new CourseResultView();

        Map<String, Object> courseTimeConfig = this.getCourseTimeConfig(tnId, taskId);
        courseResultView.setTeachDate(courseTimeConfig.get("teachDate").toString());
        courseResultView.setTeachTime(courseTimeConfig.get("time").toString());
        LOGGER.info("********学生课程表 S********");
        //获取学生的班级信息
        //参数校验
        if (!paramsMap.containsKey("classId")) {
            throw new BizException(ErrorCode.PARAN_NULL.getCode(), ErrorCode.PARAN_NULL.getMessage());
        }
        int classId = 0;
        try {
            classId = Integer.valueOf(paramsMap.get("classId").toString());
        } catch (Exception e) {
            throw new BizException(ErrorCode.PARAN_NULL.getCode(), ErrorCode.PARAN_NULL.getMessage());
        }
        Map<String, Object> allCourseTable = null;
        try {
            allCourseTable = this.getAllCourseResult(taskId, tnId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<List<List<String>>> roomData = (List<List<List<String>>>) allCourseTable.get("roomData");
        List<List<String>> classCourseList = roomData.get(classId);
        courseResultView.setWeek(classCourseList);
        LOGGER.info("********学生课程表 E********");
        return courseResultView;
    }

    /**
     * 获取教师课表
     * @param tnId
     * @param taskId
     * @param paramsMap
     * @return
     */
    private CourseResultView getTeacherCourseTable(int tnId,int taskId,Map<String,Object> paramsMap){
        CourseResultView courseResultView = new CourseResultView();

        Map<String,Object> courseTimeConfig = this.getCourseTimeConfig(tnId,taskId);
        courseResultView.setTeachDate(courseTimeConfig.get("teachDate").toString());
        courseResultView.setTeachTime(courseTimeConfig.get("time").toString());
        LOGGER.info("********学生课程表 S********");
        //获取学生的班级信息
        //参数校验
        if (!paramsMap.containsKey("teacherId") && !paramsMap.containsKey("classId")){
            throw new BizException(ErrorCode.PARAN_NULL.getCode(),ErrorCode.PARAN_NULL.getMessage());
        }
        int teacherId = 0;
        try {
            teacherId = Integer.valueOf(paramsMap.get("teacherId").toString());
        }catch (Exception e){
            throw new BizException(ErrorCode.PARAN_NULL.getCode(),ErrorCode.PARAN_NULL.getMessage());
        }
        Map<String,Object> teacherMap =  this.getTeacherByTeacherId(teacherId,tnId);
        Map<String,Object> allCourseTable = null;
        try {
            allCourseTable = this.getAllCourseResult(taskId,tnId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<List<List<String>>> roomData = (List<List<List<String>>>) allCourseTable.get("roomData");

        courseResultView.setWeek(getCourseByTeacher(tnId,taskId,roomData,teacherMap));
        LOGGER.info("********学生课程表 E********");
        return courseResultView;
    }

    /**
     *
     * @param tnId
     * @param taskId
     * @param roomData
     * @param teacherMap
     * @return
     */
    private List<List<String>> getCourseByTeacher(int tnId,int taskId,List<List<List<String>>> roomData,Map<String,Object> teacherMap){
        List<List<String>> rtnLists = new ArrayList<>();
        List<String> rtnList;
        Map<Integer, String> classMap = this.getClassByTnIdAndTaskId(tnId, taskId);
        String teacherCourse = mergeTeacherAndCourse(teacherMap.get("teacher_name").toString(),teacherMap.get("teacher_major_type").toString());
        List<List<String>> tmpLists;
        List<String> tmpList;
        for (int i = 0; i < roomData.size(); i++) {
            tmpLists = roomData.get(i);
            rtnList = new ArrayList<>();
            /**同一时间教师只能去一个教师 eg:星期一的第一节教师只能去某一个班级 即使去两个班级 那么班级一定是合班**/
            for (int j = 0; j < tmpLists.size(); j++) {
                tmpList = tmpLists.get(j);
                for (int m = 0 ; m < tmpList.size();m ++ ){
                     String tmpCourse = tmpList.get(m);
                     String ss;
                     StringBuilder sb;
                    if (rtnList.size()<m){
                        ss = rtnList.get(m);
                    }else{
                        ss = "";
                    }
                    sb = new StringBuilder(ss);
                     if (teacherCourse.equals(tmpCourse)){
                         if (sb.length()>0){
                             sb.append(Constant.COURSE_TABLE_LINE_SPLIT_CHAR).append(classMap.get(j));
                         }else {
                             rtnList.add(sb.append(tmpCourse).toString());
                         }
                     }else {
                         rtnList.add(sb.append("").toString());
                     }
                }

            }
            rtnLists.add(rtnList);
        }
        return rtnLists;
    }

    private String mergeTeacherAndCourse(String course,String teacher){
        StringBuilder sb = new StringBuilder();
        sb.append(course).append(Constant.COURSE_TABLE_LINE_SPLIT_CHAR).append(teacher);
        return sb.toString();
    }
}
