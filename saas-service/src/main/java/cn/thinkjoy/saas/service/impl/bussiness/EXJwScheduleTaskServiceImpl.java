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
import cn.thinkjoy.saas.dao.bussiness.*;
import cn.thinkjoy.saas.dao.bussiness.scheduleRule.MergeClassDAO;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.domain.bussiness.ClassRoomView;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.domain.bussiness.CourseManageVo;
import cn.thinkjoy.saas.domain.bussiness.MergeClassInfoVo;
import cn.thinkjoy.saas.enums.GradeTypeEnum;
import cn.thinkjoy.saas.service.IGradeService;
import cn.thinkjoy.saas.service.bussiness.*;
import cn.thinkjoy.saas.service.common.ConvertUtil;
import cn.thinkjoy.saas.service.common.FileOperation;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import cn.thinkjoy.saas.service.common.ReadCmdLine;
import com.alibaba.dubbo.common.json.JSON;
import com.google.common.collect.Maps;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service("EXJwScheduleTaskServiceImpl")
public class EXJwScheduleTaskServiceImpl implements IEXJwScheduleTaskService {

    @Resource
    private IJwTeachDateDAO iJwTeachDateDAO;

    @Autowired
    private IJwScheduleTaskDAO scheduleTaskDAO;

    @Autowired
    private IGradeService gradeService;

    @Resource
    private EXIGradeService exiGradeService;


    @Autowired
    private IJwCourseTableDAO courseTableDAO;

    @Autowired
    private EXITenantConfigInstanceService exiTenantConfigInstanceService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(EXJwScheduleTaskServiceImpl.class);


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
    @Autowired
    IJwScheduleTaskDAO iJwScheduleTaskDAO;
    @Autowired
    IEXJwScheduleTaskDAO iexJwScheduleTaskDAO;

    @Autowired
    IEXTeacherService iexTeacherService;

    @Autowired
    IJwCourseTableDAO jwCourseTableDAO;

    @Autowired
    private IEXJwCourseTableDAO iexJwCourseTableDAO;

    @Autowired
    private IJwSyllabusDAO jwSyllabusDAO;

    @Autowired
    EXIClassRoomService exiClassRoomService;

    @Autowired
    IClassRoomsDAO iClassRoomsDAO;

    @Resource
    IJwGradeRuleDAO iJwGradeRuleDAO;
//    @Autowired
//    private

    @Autowired
    private RedisRepository<String,Object> redis;



    @Override
    public String getScheduleTaskPath(Integer taskId, Integer tnId) {

//        String result = "/Users/dengshaofei/0221_test/";
        String result = "";
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("id", taskId);
        JwScheduleTask jwScheduleTask = selectScheduleTaskPath(map);
        if (jwScheduleTask == null || StringUtils.isBlank(jwScheduleTask.getPath())) {
            String type=getClsssTypeTag(Integer.valueOf(jwScheduleTask.getGrade()),tnId);
            result = FileOperation.getParamsPath(tnId,taskId,type);
        }
        else
            result = jwScheduleTask.getPath();

        return result;
    }
    /**
     * 获取排课结果状态
     * @param taskId
     * @param tnId
     * @return
     */
    @Override
    public String getSchduleResultStatus(Integer taskId, Integer tnId) {

        String path = getScheduleTaskPath(taskId, tnId);

        String result = FileOperation.readerTxtString(path, FileOperation.SCHEDULE_RESULT + ".txt");
//
        if (result.equals("1")) {
            updateScheduleTask(taskId, 4);
        } else if (result.equals("-1"))
            updateScheduleTask(taskId, 2);//排课失败(规则异常)
        else if (result.equals("-2"))
            updateScheduleTask(taskId, 5);//排课失败(系统异常)
//
        return result;
    }
    /**
     * 获取调课结果状态
     * @param taskId
     * @param tnId
     * @return
     */
    @Override
    public String getAdjustmentSchduleResult(Integer taskId, Integer tnId) {

        String path = getScheduleTaskPath(taskId, tnId);

        String result = FileOperation.readerTxtString(path, FileOperation.AD_RESULT_TXT + ".txt");

        return result;
    }

    @Override
    public List<String> getAdjustmentInfo(Integer taskId, Integer tnId) {

        List<String> failMsg = new ArrayList<>();

        String path = getScheduleTaskPath(taskId, tnId);

        String filenPath = path + FileOperation.ADJUSTMENT_TXT;

        File file = new File(filenPath);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            Integer i = 0;
            String errMsg = "", errTag = "0";
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] arr = result.toString().split(FileOperation.STR_SPLIT);

        failMsg = Arrays.asList(arr);

        return failMsg;
    }
    /**
     * 硬性规则违反描述
     * @param taskId
     * @param tnId
     * @return
     */
    @Override
    public List<String> getSchduleErrorDesc(Integer taskId, Integer tnId) {

        List<String> failMsg=new ArrayList<>();

        String path=getScheduleTaskPath(taskId,tnId);

        String filenPath =path + FileOperation.ERROR_TXT;
//        String filenPath ="/Users/douzy/0221_test/" + FileOperation.ERROR_TXT;

        File file = new File(filenPath);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            Integer i=0;
            String errMsg="",errTag="0";
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                i++;
                if (i == 1) {
                    errTag = s;
                    continue;
                }
                switch (errTag) {
                    case "2":
                        Map<String, String> map = JSON.parse(s, HashMap.class);
                        String regex = "\"data_error\":\"(.*?)\",";//别忘了使用非贪婪模式！
                        Matcher matcher = Pattern.compile(regex).matcher(s);
                        while (matcher.find()) {
                            String ret = matcher.group(1);
                            failMsg.add(getRuleMessage(map, ret, taskId, tnId));
                        }
                        break;
                    case "-1":
                        failMsg.add("排课系统异常,请联系管理员!" + s);
                        break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return failMsg;
    }

    /**
     * 硬性规则违反描述
     * @param map
     * @param ret
     * @param taskId
     * @param tnId
     * @return
     */
    private String  getRuleMessage(Map<String,String> map,String ret,Integer taskId,Integer tnId) {
        String msg = "";
        switch (ret) {
            case "01":
                String adm01 = map.get("adm"),
                        time01 = map.get("time");
                String className01 = getClassName(taskId, tnId, adm01);
                msg = String.format(FileOperation.NON_ADM_ERROR_MSG, className01, time01);
                break;
            case "02":
                String teacher02 = map.get("teacher"),
                        time02 = map.get("time");
                String teacherName02 = getTeacherName(taskId, tnId, teacher02);
                msg = String.format(FileOperation.NON_TEACHER_ERROR_MSG, teacherName02, time02);
                break;
            case "03":
                String adm03 = map.get("adm"),
                        course03 = map.get("course");
                String className03 = getClassName(taskId, tnId, adm03);
                CourseBaseInfo courseBaseInfo = courseBaseInfoDAO.fetch(course03);
                msg = String.format(FileOperation.NON_COURSE_ERROR_MSG, className03, courseBaseInfo.getCourseName());
                break;
        }
        return msg;
    }

    /**
     * 软性规则违反描述
     * @return
     */
    @Override
    public List<String> getNoNScheduleTaskPliableRule(Integer taskId, Integer tnId) {

        List<String> failMsg=new ArrayList<>();

        String path = getScheduleTaskPath(taskId, tnId);

//        String filenPath = "/Users/douzy/0221_test/" + FileOperation.FAIL_TXT;
        String filenPath = path + FileOperation.FAIL_TXT;

        File file = new File(filenPath);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                Map<String, String> map = JSON.parse(s, HashMap.class);
                String regex = "\"imperf\":\"(.*?)\",";//别忘了使用非贪婪模式！
                Matcher matcher = Pattern.compile(regex).matcher(s);
                while (matcher.find()) {
                    String ret = matcher.group(1);
                    failMsg.add(getPliableRuleMessage(map, ret, taskId, tnId));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return failMsg;
    }

    private String  getPliableRuleMessage(Map<String,String> map,String ret,Integer taskId,Integer tnId) {
        String msg = "";
        switch (ret) {
            //合班失败  x天x班合班失败
            case "11":
                String courseId = map.get("course"),
                        classStr = map.get("class"),
                        week = map.get("week");

                CourseBaseInfo courseBaseInfo = courseBaseInfoDAO.fetch(courseId);
                String[] arr = classStr.split(",");
                String cn = "";
                for (String str : arr) {
                    cn += getClassName(taskId, tnId, str) + "、";
                }
                msg = String.format(FileOperation.MERGE_CLASS_FAIL_MSG, week, cn, courseBaseInfo.getCourseName());
                break;
            //连堂  数目与设定数据不同
            case "12":
                String course12 = map.get("course"),
                        class12 = map.get("class"),
                        B1 = map.get("B1"),
                        B2 = map.get("B2_set");
                CourseBaseInfo courseBaseInfo12 = courseBaseInfoDAO.fetch(course12);
                String className12 = getClassName(taskId, tnId, class12);
                msg = String.format(FileOperation.CON_NUMBER_FAIL_MSG, className12, courseBaseInfo12.getCourseName(), B1, B2);
                break;
            //不连堂 x课程时段出现连堂
            case "13":
                String course13 = map.get("course");
                msg = String.format(FileOperation.NON_CON_NUMBER_FAIL_MSG, course13);
                break;
            //老师连上  不满足连上规则
            case "14":
                String course14 = map.get("course"),
                        week14 = map.get("week"),
                        teacher14 = map.get("teacher");
                CourseBaseInfo courseBaseInfo14 = courseBaseInfoDAO.fetch(course14);
                String teacherName14 = getTeacherName(taskId, tnId, teacher14);
                msg = String.format(FileOperation.CON_TEACHER_FAIL_MSG, week14, teacherName14, courseBaseInfo14.getCourseName());
                break;
            //教案平齐 x老师在x天不满足教案平齐
            case "15":
                String course15 = map.get("course"),
                        week15= map.get("week"),
                        teacher15 = map.get("teacher");
                CourseBaseInfo courseBaseInfo15 = courseBaseInfoDAO.fetch(course15);
                String teacherName15 = getTeacherName(taskId, tnId, teacher15);
                msg = String.format(FileOperation.NO_JAPQ_FAIL_MSG, week15, teacherName15, courseBaseInfo15.getCourseName());
                break;
        }
        return msg;
    }

    private String getTeacherName(Integer taskId,Integer tnId,String teacherId) {

        String className =null;

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String classGrade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        String tableName = ParamsUtils.combinationTableName("teacher", tnId);

        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("searchKey", "id");
        map.put("searchValue", teacherId);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);
        for (int j = 0; j < linkedHashMaps.size(); j++) {
            LinkedHashMap<String, Object> dataLinkedMap = linkedHashMaps.get(j);
            for (Iterator iter = dataLinkedMap.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                if (strKey.equals("teacher_name"))
                    className = strObj.toString();
            }
        }
        return className;
    }
    private String getClassName(Integer taskId,Integer tnId,String classId) {

        String className =null;

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String classGrade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        String tableName = ParamsUtils.combinationTableName("class_adm", tnId);

        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("searchKey", "id");
        map.put("searchValue", classId);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);
        for (int j = 0; j < linkedHashMaps.size(); j++) {
            LinkedHashMap<String, Object> dataLinkedMap = linkedHashMaps.get(j);
            for (Iterator iter = dataLinkedMap.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                if (strKey.equals("class_name"))
                    className = strObj.toString();
            }
        }
        return className;
    }
    @Override
    public JwScheduleTask selectScheduleTaskPath(Map map) {
        return iexJwScheduleTaskDAO.selectScheduleTaskPath(map);
    }

    /**
     * 调课
     * @param jwCourseTable
     * @param adjustmentType 0:老师 1:行政
     * @return
     */
    @Override
    public boolean SerializableAdjustmentSchedule( JwCourseTable jwCourseTable, Integer adjustmentType) {
        boolean flag = false;
        if (jwCourseTable == null)
            return flag;

        final Integer tnId = jwCourseTable.getTnId(),
                taskId = jwCourseTable.getTaskId();

        String path = this.getScheduleTaskPath(taskId, tnId);

        String shCmd = String.format(ReadCmdLine.SHELL_TIMETABLE_ADJUST, path, adjustmentType,(adjustmentType==0?jwCourseTable.getTeacherId():jwCourseTable.getClassId()), jwCourseTable.getWeek()+1, jwCourseTable.getSort()+1);

        Integer r = ReadCmdLine.run(path, shCmd);

        flag = r > 0;

        return flag;
    }

    /**
     * 初始化排课参数
     * @param taskId   任务ID
     * @param tnId     租户ID
     * @return
     * @throws IOException
     */
    @Override
    public boolean initParmasFile(final Integer taskId, final Integer tnId) throws IOException {

        boolean flag = false;

        if (taskId <= 0 || tnId <= 0)
            return false;

        JwScheduleTask jwScheduleTask = iJwScheduleTaskDAO.fetch(taskId);
        if (jwScheduleTask == null)
            return false;

        //1:行政班  2:行政班+教学班  3:文科+理科
        Integer classType = exiGradeService.getGradeType(tnId, Integer.valueOf(jwScheduleTask.getGrade()));

        String type="adm";

        switch (classType) {
            case 1://行政班
                flag = admParmas(taskId, tnId,type);
                break;
            case 2://教学班
                type="edu";
                flag = eduParmas(taskId, tnId,type);
                break;
            case 3: //行政班
                flag = admParmas(taskId, tnId,type);
                break;
        }
        return flag;
    }

    /**
     * 行政班||教学班标签
     * @param grade
     * @param tnId
     * @return
     */
    private String getClsssTypeTag(Integer grade,Integer tnId) {

        String type = "adm";

        if (tnId <= 0 || grade<=0)
            return null;

        //1:行政班  2:行政班+教学班  3:文科+理科
        Integer classType = exiGradeService.getGradeType(tnId, grade);

        type = classType == 2 ? "edu" : type;

        return type;
    }
    @Override
    public String getClsssTypeTagByTaskId(Integer taskId,Integer tnId) {


        String type = "adm";

        if (tnId <= 0 || taskId <= 0)
            return null;

        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("id", taskId);
//        JwScheduleTask jwScheduleTask = selectScheduleTaskPath(map);
        JwScheduleTask jwScheduleTask = iJwScheduleTaskDAO.queryOne(map,"id","asc");

        if (jwScheduleTask == null)
            return null;

        //1:行政班  2:行政班+教学班  3:文科+理科
        Integer classType = exiGradeService.getGradeType(tnId, Integer.valueOf(jwScheduleTask.getGrade()));

        type = classType == 2 ? "edu" : type;

        return type;
    }
    /**
     * 教学班排课参数
     * @param taskId
     * @param tnId
     * @return
     */
    private boolean eduParmas(final Integer taskId, final Integer tnId,final String type) throws IOException {
        boolean flag = false;

        List<StringBuffer> parametersBuffers = getParameters("edu");

        flag = printBuffers(tnId, taskId, parametersBuffers, FileOperation.PARMETERS,type);

        List<StringBuffer> admClassRuleBuffers = getClassRule(taskId, 0);//行政班 不排课

        flag = printBuffers(tnId, taskId, admClassRuleBuffers, FileOperation.ADMIN_CLASS_NON_DISPACHING,type);

        List<StringBuffer> eduClassRuleBuffers = ObjIsNothing();//班级 不排课

        flag = printBuffers(tnId, taskId, eduClassRuleBuffers, FileOperation.CLASS_NON_DISPACHING,type);

        List<StringBuffer> admClassInfoBuffers = getClassInfo(taskId, tnId,"adm");//行政班基础信息

        flag = printBuffers(tnId, taskId, admClassInfoBuffers, FileOperation.CLASS_INFO,type);

        List<StringBuffer> courseRuleBuffers = getCourseRule(taskId);//课程不排课

        flag = printBuffers(tnId, taskId, courseRuleBuffers, FileOperation.COURSE_NON_DISPACHING,type);

        List<StringBuffer> teachDataBuffers = getTeachDateInfo(taskId, tnId);//基础规则设置

        flag = printBuffers(tnId, taskId, teachDataBuffers, FileOperation.COURSE_TIMESLOTS,type);

        List<StringBuffer> gradeNonDisBuffers = getGradeNonDispaching(taskId);//年级不排课

        flag = printBuffers(tnId, taskId, gradeNonDisBuffers, FileOperation.GRAD_NON_DISPACHING,type);

        List<StringBuffer> courseInfomationBuffers = courseInfomation(taskId, tnId,type);//课程信息

        flag = printBuffers(tnId, taskId, courseInfomationBuffers, FileOperation.COURSE_INFORMATION,type);

        List<StringBuffer> teacherSettingBuffers = teachersSetting(taskId, tnId,"edu");//教师设置

        flag = printBuffers(tnId, taskId, teacherSettingBuffers, FileOperation.TEACHERS_SETTING,type);

        List<StringBuffer> studentSelectionBuffers = studentSelection(taskId, tnId);//学生设置

        flag = printBuffers(tnId, taskId, studentSelectionBuffers, FileOperation.STUDENT_SELECTION,type);

        List<StringBuffer> classRooms=getClassRoom(taskId, tnId);

        flag = printBuffers(tnId, taskId, classRooms, FileOperation.CLASS_ROOM,type);

        if (flag) {
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String path = FileOperation.getParamsPath(tnId, taskId,type);
                    String shellCMD=ReadCmdLine.timetableEduShellCmd(path);
                    Integer c = ReadCmdLine.run(path,shellCMD);
                    String result = getSchduleResultStatus(taskId, tnId);
                    //翻译课表并存入数据库
                    getCourseResult(tnId,taskId);

                    if (result.equals("1")) {
                        updateScheduleTask(taskId, 4);
                    } else if (result.equals("-1"))
                        updateScheduleTask(taskId, 2);//排课失败(规则异常)
                    else if (result.equals("-2"))
                        updateScheduleTask(taskId, 5);//排课失败(系统异常)
                }
            });
        }

        return flag;
    }

    /**
     * 初始化行政班排课参数
     * @param taskId
     * @param tnId
     * @return
     */
    private boolean admParmas(final Integer taskId, final Integer tnId,final String type) throws IOException {
        boolean result = false;

        List<StringBuffer> parametersBuffers = getParameters("adm");

        result = printBuffers(tnId, taskId, parametersBuffers, FileOperation.PARMETERS,type);

        List<StringBuffer> admClassRuleBuffers = getClassRule(taskId, 1);//行政班 不排课

        result = printBuffers(tnId, taskId, admClassRuleBuffers, FileOperation.ADMIN_CLASS_NON_DISPACHING,type);

        List<StringBuffer> eduClassRuleBuffers = ObjIsNothing();//班级 不排课

        result = printBuffers(tnId, taskId, eduClassRuleBuffers, FileOperation.CLASS_NON_DISPACHING,type);

        List<StringBuffer> admClassInfoBuffers = getClassInfo(taskId, tnId,type);//行政班基础信息

        result = printBuffers(tnId, taskId, admClassInfoBuffers, FileOperation.CLASS_INFO,type);

        List<StringBuffer> courseRuleBuffers = getCourseRule(taskId);//课程不排课

        result = printBuffers(tnId, taskId, courseRuleBuffers, FileOperation.COURSE_NON_DISPACHING,type);

        List<StringBuffer> teachDataBuffers = getTeachDateInfo(taskId, tnId);//基础规则设置

        result = printBuffers(tnId, taskId, teachDataBuffers, FileOperation.COURSE_TIMESLOTS,type);

        List<StringBuffer> gradeNonDisBuffers = getGradeNonDispaching(taskId);//年级不排课

        result = printBuffers(tnId, taskId, gradeNonDisBuffers, FileOperation.GRAD_NON_DISPACHING,type);

        List<StringBuffer> courseInfomationBuffers = courseInfomation(taskId, tnId,type);//课程信息

        result = printBuffers(tnId, taskId, courseInfomationBuffers, FileOperation.COURSE_INFORMATION,type);

        List<StringBuffer> teacherSettingBuffers = teachersSetting(taskId, tnId,"adm");//教师设置

        result = printBuffers(tnId, taskId, teacherSettingBuffers, FileOperation.TEACHERS_SETTING,type);
        LOGGER.info("===================参数序列化结果:" + result + "===================");
        if (result) {
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String path = FileOperation.getParamsPath(tnId, taskId,type);
                    String shellCMD=ReadCmdLine.timetableShellCmd(path);
                    Integer c = ReadCmdLine.run(path,shellCMD);
                    String result = getSchduleResultStatus(taskId, tnId);
                    //翻译课表并存入数据库
                    getCourseResult(tnId,taskId);

                    if (result.equals("1")) {
                        updateScheduleTask(taskId, 4);
                    } else if (result.equals("-1"))
                        updateScheduleTask(taskId, 2);//排课失败(规则异常)
                    else if (result.equals("-2"))
                        updateScheduleTask(taskId, 5);//排课失败(系统异常)
                }
            });
        }
        LOGGER.info("===================已完成异步调用排课===================");

        return result;
    }

    private boolean updateScheduleTask(Integer taskId,Integer status) {
        JwScheduleTask jwScheduleTask = new JwScheduleTask();
        jwScheduleTask.setId(taskId);
        jwScheduleTask.setStatus(status);
        return iJwScheduleTaskDAO.update(jwScheduleTask) > 0;
    }


    private String converTag(String str){
        str = str.replace("1", "@").replace("0", "|");

        str = str.replace("@", "0").replace("|", "1");


        return str;
    }

    /**
     * 参数写入txt
     * @param tnId 租户ID
     * @param taskId  任务ID
     * @param classRuleBuffers 数据集
     * @param fileName 文件名
     * @param type 班级类型   行政班||教学班
     * @return
     * @throws IOException
     */
    private boolean printBuffers(Integer tnId,Integer taskId ,List<StringBuffer> classRuleBuffers,String fileName,String type) throws IOException {
        boolean flag = FileOperation.creatTxtFile(tnId,taskId,fileName,type);
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
     * 教室信息
     * @param taskId
     * @param tnId
     * @return
     */
       @Override
    public List<StringBuffer> getClassRoom(Integer taskId,Integer tnId) {

        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String grade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("gradeId", jwScheduleTask.getGrade());
        ClassRooms classRooms = iClassRoomsDAO.queryOne(map, "id", "asc");

        Integer maxRoomNumber = classRooms == null ? 0 :classRooms.getScheduleNumber()==null?classRooms.getDayNumber() + classRooms.getExecutiveNumber():classRooms.getScheduleNumber();

        if (maxRoomNumber > 0) {
            Integer admNum=0;
            StringBuffer stringBuffer1 = new StringBuffer();
            stringBuffer1.append(maxRoomNumber);
            stringBuffer1.append(FileOperation.LINE_SPLIT);
            stringBuffers.add(stringBuffer1);

            String tableName = ParamsUtils.combinationTableName("class_adm", tnId);

            Map class_map = new HashMap();
            class_map.put("tableName", tableName);
            class_map.put("searchKey", "class_grade");
            class_map.put("searchValue", grade);

            List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(class_map);

            for (int j = 0; j < linkedHashMaps.size(); j++) {

                LinkedHashMap<String, Object> dataLinkedMap = linkedHashMaps.get(j);
                StringBuffer stringBuffer = new StringBuffer();
                for (Iterator iter = dataLinkedMap.entrySet().iterator(); iter.hasNext(); ) {

                    Map.Entry element = (Map.Entry) iter.next();
                    Object strKey = element.getKey();
                    Object strObj = element.getValue();
                    if (strKey.equals("id")) {
                        stringBuffer.append(strObj);
                        stringBuffer.append(FileOperation.LINE_SPLIT);
                        admNum++;
                    }
                }
                stringBuffers.add(stringBuffer);
            }
            if(admNum<maxRoomNumber){

                for(int i=0;i<(maxRoomNumber-admNum);i++){
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("-"+(i+1));
                    stringBuffer2.append(FileOperation.LINE_SPLIT);
                    stringBuffers.add(stringBuffer2);
                }

            }
        }
        return stringBuffers;
    }

    /**
     * 学生信息
     * @param taskId
     * @param tnId
     * @return
     */
    private List<StringBuffer> studentSelection(Integer taskId,Integer tnId){
        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String studentGrade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        String tableName = ParamsUtils.combinationTableName("student", tnId);


        Map map = new HashMap();
        map.put("tableName",tableName);
        map.put("searchKey", "student_grade");
        map.put("searchValue", studentGrade);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);

        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(linkedHashMaps.size());
        stringBuffer1.append(FileOperation.STR_SPLIT);
        stringBuffer1.append(3);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer1);

        for (int j = 0; j < linkedHashMaps.size(); j++) {
            LinkedHashMap<String, Object> dataLinkedMap = linkedHashMaps.get(j);
            StringBuffer stringBuffer = new StringBuffer();
            Integer studentId = 0, courseId = 0;
            for (Iterator iter = dataLinkedMap.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                if (strKey.equals("id")) {
                    studentId=Integer.valueOf(strObj.toString());
                    stringBuffer.append(studentId);//ID
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("student_class")) {
                    String[] classStr = strObj.toString().split("、");

                    for (String str : classStr) {
                        stringBuffer.append(getClassId(tnId, str.toString(), studentGrade,"adm"));//指定所带班级
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }
                }
                if (strKey.equals("student_no")) {
                    stringBuffer.append(strObj.toString());//ID
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("student_check_major1")) {

                    stringBuffer.append(getCourseID(strObj.toString()));//ID
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("student_check_major_class1")) {
                    stringBuffer.append(getClassId(tnId, strObj.toString(), studentGrade,"edu"));//指定所带班级
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("student_check_major2")) {
                    stringBuffer.append(getCourseID(strObj.toString()));//ID
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("student_check_major_class2")) {
                    stringBuffer.append(getClassId(tnId, strObj.toString(), studentGrade,"edu"));//指定所带班级
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("student_check_major3")) {
                    stringBuffer.append(getCourseID(strObj.toString()));//ID
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
                if(strKey.equals("student_check_major_class3")) {
                    stringBuffer.append(getClassId(tnId, strObj.toString(), studentGrade,"edu"));//指定所带班级
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
            }
            stringBuffer.append(FileOperation.LINE_SPLIT);

            stringBuffers.add(stringBuffer);
        }

        return stringBuffers;
    }

    /**
     * 教师设置
     * @return
     */
    private List<StringBuffer> teachersSetting(Integer taskId,Integer tnId,String type) {

        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String teacherGrade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        String tableName = ParamsUtils.combinationTableName("teacher", tnId);


        Map map = new HashMap();
        map.put("tableName",tableName);
        map.put("searchKey", "teacher_grade");
        map.put("searchValue", teacherGrade);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);


        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(linkedHashMaps.size());
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer1);

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
                        Integer ci = getClassId(tnId, str.toString(), teacherGrade, "adm");
                        if (ci <= 0 && type.equals("edu"))
                            ci = getClassId(tnId, str.toString(), teacherGrade, "edu");
                        stringBuffer.append(ci);//指定所带班级
                        stringBuffer.append(FileOperation.STR_SPLIT);
                    }

                    Map teacherMap=new HashMap();
                    teacherMap.put("taskId",taskId);
                    teacherMap.put("teacherId",teacherId);

                    JwTeacherRule jwTeacherRule= iJwTeacherRuleDAO.queryOne(teacherMap,"id","asc");

                    if(jwTeacherRule==null){
                        stringBuffer.append(0);
                        stringBuffer.append(FileOperation.STR_SPLIT);
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

                    stringBuffer.append(jwBaseConRule==null?0:(jwBaseConRule.getDayConType()==1?3:4)); //连上节数
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(jwBaseConRule==null?0:jwBaseConRule.getImportantType()); //连上规则权重
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(1); //教案平齐 都是1
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    JwBaseJaqpRule jwBaseJaqpRule=iJwBaseJaqpRuleDAO.queryOne(conRuleMap,"id","asc");

                    stringBuffer.append(jwBaseJaqpRule==null?0:jwBaseJaqpRule.getImportantType()); //教案平齐权重
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    JwBaseWeekRule jwBaseWeekRule=iJwBaseWeekRuleDAO.queryOne(conRuleMap,"id","asc");

                    stringBuffer.append(jwBaseWeekRule==null?0:jwBaseWeekRule.getWeekType()); //周任课规则
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(jwBaseWeekRule==null?0:jwBaseWeekRule.getImportantType()); //周任课规则权重
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    JwBaseDayRule jwBaseDayRule=iJwBaseDayRuleDAO.queryOne(conRuleMap,"id","asc");
                    stringBuffer.append(jwBaseDayRule==null?0:jwBaseDayRule.getDayType()); //日任课规则
                    stringBuffer.append(FileOperation.STR_SPLIT);

                    stringBuffer.append(jwBaseDayRule==null?0:jwBaseDayRule.getImportantType()); //日任课规则权重
                    stringBuffer.append(FileOperation.STR_SPLIT);
                }
            }
            stringBuffer.append(FileOperation.LINE_SPLIT);

            stringBuffers.add(stringBuffer);
        }


        return stringBuffers;
    }



    private Integer getCourseID(String courseName) {
        Map courseMap = new HashMap();
        courseMap.put("courseName", courseName);
        CourseBaseInfo courseBaseInfo = iCourseBaseInfoDAO.queryOne(courseMap, "id", "asc");
        return courseBaseInfo == null ? 0 : Integer.valueOf(courseBaseInfo.getId().toString());
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

    public  Integer getClassId(Integer tnId,String className,String grade,String type) {

        Integer classId = 0;

        String tableName = ParamsUtils.combinationTableName("class_"+type, tnId);


        List<Map<String,Object>> params = new ArrayList<>();

        Map<String,Object> param = new HashMap<>();
        param.put("key","class_name");
        param.put("op","=");
        param.put("value",className);
        params.add(param);

        Map<String,Object> param1 = new HashMap<>();
        param1.put("key","class_grade");
        param1.put("op","=");
        param1.put("value",grade);
        params.add(param1);

        List<LinkedHashMap<String, Object>> tenantCustoms = iexTeantCustomDAO.likeTableByParams(tableName,params);

        for (int j = 0; j < tenantCustoms.size(); j++) {
            LinkedHashMap<String, Object> dataLinkedMap = tenantCustoms.get(j);
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
     * @return
     */
    private List<StringBuffer> getGradeNonDispaching(Integer taskId) {


        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);
        if(jwScheduleTask==null)
            return ObjIsNothing();

        Map map = new HashMap();
        map.put("taskId", taskId);
        map.put("gradeId", jwScheduleTask.getGrade());
        List<JwGradeRule> jwGradeRules = iJwGradeRuleDAO.queryList(map, "id", "asc");

        List<StringBuffer> stringBuffers = new ArrayList<>();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer = new StringBuffer();
            if(jwGradeRules==null||jwGradeRules.size()<=0)
                return ObjIsNothing();

        JwGradeRule jwGradeRule = jwGradeRules.get(0);


        stringBuffer.append(converGradeNon(jwGradeRule.getMon()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwGradeRule.getTues()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwGradeRule.getWed()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwGradeRule.getTues()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwGradeRule.getFri()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwGradeRule.getSut()));
        stringBuffer.append(FileOperation.STR_SPLIT);
        stringBuffer.append(converGradeNon(jwGradeRule.getSun()));
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


    private List<StringBuffer> courseInfomation(Integer taskId,Integer tnId,String type){

        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        Map map=new HashMap();
        map.put("tnId",tnId);
        map.put("gradeId",jwScheduleTask.getGrade());
        List<CourseManageVo> courseManageVos=iCourseManageDAO.selectCourseManageInfo(map);


        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(courseManageVos.size());
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer1);

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

            if(jwCourse==null){
                stringBuffer.append(0);
                stringBuffer.append(FileOperation.STR_SPLIT);
                stringBuffer.append(0);
                stringBuffer.append(FileOperation.STR_SPLIT);
            }else {
                stringBuffer.append(converCourseHour(jwCourse.getCourseHour(), 0));
                stringBuffer.append(FileOperation.STR_SPLIT);
                stringBuffer.append(converCourseHour(jwCourse.getCourseHour(), 1));
                stringBuffer.append(FileOperation.STR_SPLIT);
            }
            String cType=courseManageVo.getCourseType();
            stringBuffer.append(type=="adm"?ConvertUtil.converCourseType(cType):(Integer.valueOf(cType)==4?0:1));//行政班分文理  教学班 0：行政课 1：走班课程
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

    private  String converCourseHour(String hour,Integer index) {

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
        Integer n=0;
        String rul = "";
        if(rule!=null) {
            String[] rules = rule.getRule().split("@@");
            n=rules.length;
            int len = rules.length;
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    String[] detail = rules[i].split(":");
                    if (detail.length < 2)
                        continue;
                    rul += detail[1] + FileOperation.STR_SPLIT;
                }
            }
        }
        for(int i=n;i<maxNum;i++) {  //空的 补0
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
            courMap.put("courseType", classType == 0 ? -1 : (classType == 1 ? 2 : 1));
            List<CourseManageVo> courseManageVos = iCourseManageDAO.selectCourseManageInfoPK(courMap);

            //行政班 文科班&理科班
            if (gradeObj.getClassType() == 1 || gradeObj.getClassType() == 3) {
                stringBuffer.append(courseManageVos.size());
                stringBuffer.append(FileOperation.STR_SPLIT);
                for (CourseManageVo courseManageVo : courseManageVos) {
                    stringBuffer.append(courseManageVo.getCourseId());
                    stringBuffer.append(FileOperation.CHAR_SPLIT);
                }
            } else if (gradeObj.getClassType() == 2) {   //走读班

                String couId = "";
                int i = 0;
                for (CourseManageVo courseManageVo : courseManageVos) {
                    if (courseManageVo.getCourseType().equals("4")) {
                        i++;
                        couId += courseManageVo.getCourseId() + FileOperation.CHAR_SPLIT;
                    }
                }
                stringBuffer.append((i ));
                stringBuffer.append(FileOperation.STR_SPLIT);
                stringBuffer.append(couId);
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
    private List<StringBuffer> getClassInfo(Integer taskId,Integer tnId,String type) {

        List<StringBuffer> stringBuffers = new ArrayList<>();

        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);

        String classGrade = ConvertUtil.converGradeByTag(jwScheduleTask.getGrade());

        String tableName = ParamsUtils.combinationTableName("class_"+type, tnId);

        Map map = new HashMap();
        map.put("tableName",tableName);
        map.put("searchKey", "class_grade");
        map.put("searchValue", classGrade);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.getTenantCustom(map);

        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(linkedHashMaps.size());
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffers.add(stringBuffer1);

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

    private List<StringBuffer> getParameters(String type ) {
        List<StringBuffer> stringBuffers = new ArrayList<>();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append(101);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffer1.append(20);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffer1.append(200);
        stringBuffer1.append(FileOperation.LINE_SPLIT);
        stringBuffer1.append(type.equals("adm")?10:20);
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
//        if(type==0)
//            return ObjIsNothing();

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
    @Override
    public void getCourseResult(int tnId, int taskId){
        Map<String, Object> params = new HashMap<>();
        params.put("tnId", tnId);
        params.put("taskId", taskId);
        courseTableDAO.deleteByCondition(params);
        jwSyllabusDAO.deleteByCondition(params);
        List<JwCourseTable> jwCourseTables = new ArrayList<>();
        Map<String, Object> resultMap = Maps.newHashMap();

        LOGGER.info("************获取时间设置 S************");
        Map<String, Object> timeConfigMap = this.getCourseTimeConfig(tnId, taskId);
        resultMap.put("teachDate", timeConfigMap.get("teachDate").toString());
        resultMap.put("teachTime", timeConfigMap.get("time").toString());
        int everyDaySection = Integer.valueOf(timeConfigMap.get("count").toString());
        LOGGER.info("************获取时间设置 E************");



        LOGGER.info("************获取年级信息设置 S************");
        JwScheduleTask jwScheduleTask = scheduleTaskDAO.fetch(taskId);
        Map<String, Object> param = new HashMap<>();
        param.put("gradeCode", jwScheduleTask.getGrade());
        param.put("tnId", tnId);
        Grade gradeServiceOne = (Grade) gradeService.queryOne(param);
        String grade = gradeServiceOne.getGrade();
        LOGGER.info("************获取年级信息设置 E************");

        LOGGER.info("************获取课程表 S************");
        boolean isEduClass = isEduGrade(tnId,Integer.valueOf(jwScheduleTask.getGrade()));
        //行政班
        getAdmSyllabus(tnId,taskId,grade,everyDaySection,jwScheduleTask,jwCourseTables);
        if (isEduClass){
            getEduSyllabus(tnId,taskId,grade,everyDaySection,jwScheduleTask,jwCourseTables);
        }

        LOGGER.info("************获取课程表 E************");

        LOGGER.info("************存储课表 S*************");
        try {
            iexJwCourseTableDAO.insertList(jwCourseTables);
        } catch (Exception e) {
            LOGGER.info("************存数据库失败*************");
        }
        LOGGER.info("************存储课表 E*************");
    }



    /**
     * 获取课表设置时间
     * @param tnId
     * @param taskId
     * @return
     */
    @Override
    public Map<String,Object> getCourseTimeConfig(int tnId, int taskId){


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
            count = StringUtils.remove(jwTeachDate.getTeachDetail(),Constant.TIME_INTERVAL).length();
            String[] times = jwTeachDate.getTeachDetail().split(Constant.TIME_INTERVAL);
            StringBuilder builder = new StringBuilder();
            for (String ss : times){
                builder.append(ss.length());
            }
            if (times.length<3){
                builder.append(0);
            }
            time = builder.toString();
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
     * @return
     */
    private Map<Integer,String> getClassByTnIdAndTaskId(int tnId,int classType,String grade){
        List<LinkedHashMap<String, Object>> classes = getClassMap(tnId,classType,grade);

        Map<Integer,String> rtnMap = new HashMap<>();
        for (LinkedHashMap<String, Object> ss : classes){
            rtnMap.put(Integer.valueOf(ss.get("id").toString()),ss.get("class_name").toString());
        }
        return rtnMap;
    }

    private List<LinkedHashMap<String, Object>> getClassMap(int tnId,int classType,String grade){
        List<LinkedHashMap<String, Object>> classes;

        if (classType == Constant.CLASS_ADM_CODE) {
            //行政班
            classes = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, grade,Constant.CLASS_ADM);
        } else {
            //教学班
            classes = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, grade,Constant.CLASS_EDU);
        }
        return classes;
    }
    /**
     * 获取班级信息
     * @param tnId
     * @return
     */
    @Override
    public Map<Integer,LinkedHashMap<String, Object>> getClassMapByTnIdAndTaskId(int tnId,int classType,String grade){
        List<LinkedHashMap<String, Object>> classes = this.getClassMap(tnId,classType,grade);

        Map<Integer,LinkedHashMap<String, Object>> rtnMap = new HashMap<>();
        for (LinkedHashMap<String, Object> ss : classes){
            rtnMap.put(Integer.valueOf(ss.get("id").toString()),ss);
        }
        return rtnMap;
    }

    /**
     * 获取班级信息
     * @param tnId
     * @return
     */
    @Override
    public Map<String,Integer> getClassMapByTnId(int tnId,int classType,String grade){
        List<LinkedHashMap<String, Object>> classes = this.getClassMap(tnId,classType,grade);

        Map<String,Integer> rtnMap = new HashMap<>();
        for (LinkedHashMap<String, Object> ss : classes){
            rtnMap.put(ss.get("class_name").toString(),Integer.valueOf(ss.get("id").toString()));
        }
        return rtnMap;
    }

    /**
     * 获取课程信息
     * @return
     */
    private Map<Integer,String> getCourseByTnIdAndTaskId(){

        List<CourseBaseInfo> list = courseBaseInfoDAO.findAll("id","asc");
        Map<Integer,String> courseMap = new LinkedHashMap();
        for (CourseBaseInfo courseBaseInfo  : list){
            courseMap.put(Integer.valueOf(courseBaseInfo.getId().toString()),courseBaseInfo.getCourseName());
        }
        //0的时候是没有课程
        courseMap.put(0,"");
        return courseMap;
    }
    /**
     * 获取课程信息
     * @return
     */
    private Map<String,Integer> getCourseIdByTnIdAndTaskId(){

        List<CourseBaseInfo> list = courseBaseInfoDAO.findAll("id","asc");
        Map<String,Integer> courseMap = new LinkedHashMap();
        for (CourseBaseInfo courseBaseInfo  : list){
            courseMap.put(courseBaseInfo.getCourseName(),Integer.valueOf(courseBaseInfo.getId().toString()));
        }
        return courseMap;
    }

    /**
     * 获取教师
     * @return
     */
    private Map<String,Object> getTeacherByCourse(int tnId,int taskId,String grade){
        Map<String,Object> param = new HashMap<>();
        param.put("teacher_grade",grade);
        List<LinkedHashMap<String,Object>> rtnList = iexTeacherService.getScheduleTeacherByTnIdAndTaskId(tnId,taskId,param);
        Map<String,Object> rtnMap = new HashMap<>();
        for (Map<String,Object> map : rtnList){
            String[] teacherClasses = map.get("teacher_class").toString().split("、");
            for (String className : teacherClasses){
                String course = map.get("teacher_major_type").toString();
                rtnMap.put(getTeacherKey(course,className),map);
            }
        }
        return rtnMap;
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

    private static String getTeacherKey(String course,String className){
        return course+Constant.TIME_INTERVAL+className;
    }

    @Override
    public Map<String,Object> getConfigRooms(String taskId){
        Map<String,Object> resultMap=new HashMap<>();
        // 通过taskId获得年级信息，
        JwScheduleTask task = scheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        String grade=task.getGrade();
        int tnId=task.getTnId();
        // 根据年级信息判断是否是“行政班-教学班”，
        Map<String, Object> map = new HashMap<>();
        map.put("gradeCode", grade);
        map.put("tnId", tnId);
        Grade gradeObj = (Grade) iGradeDAO.queryOne(map,"id","asc");
        if(gradeObj.getClassType()!=2){
            resultMap.put("msg","年级类型非行政教学");
            return resultMap;
        }
        // 如果是，返回行政班数量，及教室管理中总数量
        String tableName= ParamsUtils.combinationTableName("class_adm", tnId);
        Map<String,Object> map1=new HashMap<>();
        map1.put("tableName",tableName);
        map1.put("searchKey","class_grade");
        map1.put("searchValue",gradeObj.getGrade());
        int admNumber=iexTeantCustomDAO.getTenantCustomCount(map1);
        map.put("gradeId",grade);
        List<ClassRoomView> classRooms = exiClassRoomService.selectClassRoomByTnId(map);
        if (classRooms==null||classRooms.size()!=1){
            resultMap.put("msg","教室管理设置有误");
            return resultMap;
        }
        int sum=classRooms.get(0).getExecutiveNumber()+classRooms.get(0).getDayNumber();
        if (classRooms.get(0).getScheduleNumber()!=0){
            sum=classRooms.get(0).getScheduleNumber();
        }
        resultMap.put("admNumber",admNumber);
        resultMap.put("sum",sum);
        resultMap.put("classRoomId",classRooms.get(0).getId());
        return resultMap;
    }

    @Override
    public Map<String,Object> updateClassRoom(String classRoomId,int scheduleNumber){
        Map<String,Object> update=new HashMap<>();
        update.put("scheduleNumber",scheduleNumber);
        Map<String,Object> condition=new HashMap<>();
        condition.put("id",classRoomId);
        iClassRoomsDAO.updateByCondition(update,condition);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("msg","success");
        return resultMap;
    }

    /**
     * 判定年级是否存在教学班
     * @param tnId
     * @param gradeCode
     * @return
     */
    private boolean isEduGrade(int tnId,int gradeCode){
        Integer type = exiGradeService.getGradeType(tnId,gradeCode);
        return type != null && type == GradeTypeEnum.Teaching.getCode();
    }

    /**
     * 获取行政班课表
     * @param tnId
     * @param taskId
     * @param grade
     * @param everyDaySection
     * @param jwScheduleTask
     * @param jwCourseTables
     */
    private void getAdmSyllabus(int tnId,int taskId,String grade,Integer everyDaySection,JwScheduleTask jwScheduleTask,List<JwCourseTable> jwCourseTables){

        LOGGER.info("************获取班级设置 S************");
        Map<Integer, String> classMap = this.getClassByTnIdAndTaskId(tnId,Constant.CLASS_ADM_CODE,grade);
        LOGGER.info("************获取班级设置 E************");

        LOGGER.info("************获取课表 S************");
        List<String> allCourseList = null;
        Map<Integer, String> courses = this.getCourseByTnIdAndTaskId();
        try {
            String path = getScheduleTaskPath(taskId, tnId) + Constant.PATH_SCHEDULE_ADM;
//                String path = "/Users/yangyongping/Desktop/yqhc/zgk-saas/saas-service/src/main/resources/config/admin_course_0(1).txt";
            CharSource main = Files.asCharSource(new File(path), Charset.defaultCharset());
            allCourseList = main.readLines();
        } catch (Exception e) {
            throw new BizException("error", "排课数据获取失败");
        }
        Map<String, Object> teachers = this.getTeacherByCourse(tnId, taskId, grade);
        for (String courseLine : allCourseList) {
            String[] courseInfo = courseLine.split(Constant.COURSE_TABLE_LINE_SPLIT_CLASS);
            String courseStr = courseInfo[1];

            Integer classId = null;
            try {
                classId = Integer.valueOf(courseInfo[0]);
            } catch (Exception e) {
                throw new BizException("error", "班级不存在");
            }
            JwSyllabus jwSyllabus = new JwSyllabus();
            jwSyllabus.setInfo(courseStr);
            jwSyllabus.setTaskId(taskId);
            jwSyllabus.setClassId(classId);
            jwSyllabus.setClassType(Constant.CLASS_ADM_CODE);
            jwSyllabusDAO.insert(jwSyllabus);
            String className = classMap.get(classId);


            List<List<String>> weekTempCourses = spiltDay(everyDaySection, courseStr);

            for (int i = 0; i < weekTempCourses.size(); i++) {
                List<String> dayCourseTempList = weekTempCourses.get(i);

                for (int j = 0; j < dayCourseTempList.size(); j++) {
                    String dayCourse = dayCourseTempList.get(j);
                    String course = courses.get(Integer.valueOf(dayCourse));
                    int roomId = classId;
                    if (course == "") {
                        JwCourseTable jwCourseTable = new JwCourseTable();
                        jwCourseTable.setTnId(tnId);
                        jwCourseTable.setTaskId(taskId);
                        jwCourseTable.setGradeId(Integer.valueOf(jwScheduleTask.getGrade()));
                        jwCourseTable.setClassId(classId);
                        jwCourseTable.setRoomId(roomId);
                        jwCourseTable.setCourseId(Integer.valueOf(dayCourse));
                        jwCourseTable.setTeacherId(0);
                        jwCourseTable.setStatus(0);
                        jwCourseTable.setWeek(i);
                        jwCourseTable.setSort(j);
                        jwCourseTable.setClassType(Constant.CLASS_ADM_CODE);
                        jwCourseTables.add(jwCourseTable);
                    } else {
//
                        Map<String, Object> teacherMap = (Map<String, Object>) teachers.get(getTeacherKey(course, className));
                        //teacherId teacherMap.get("id")
                        //gradeId jwScheduleTask.getGrade()
                        //classId classId
                        //courseId Integer.valueOf(dayCourse)
                        //week i
                        //sort j
                        JwCourseTable jwCourseTable = new JwCourseTable();
                        jwCourseTable.setTnId(tnId);
                        jwCourseTable.setTaskId(taskId);
                        jwCourseTable.setGradeId(Integer.valueOf(jwScheduleTask.getGrade()));
                        jwCourseTable.setClassId(classId);
                        jwCourseTable.setRoomId(roomId);
                        jwCourseTable.setCourseId(Integer.valueOf(dayCourse));
                        jwCourseTable.setTeacherId(Integer.valueOf(teacherMap.get("id").toString()));
                        jwCourseTable.setWeek(i);
                        jwCourseTable.setSort(j);
                        jwCourseTable.setStatus(Constant.COURSE_STATUS_Y);
                        jwCourseTable.setClassType(Constant.CLASS_ADM_CODE);
                        jwCourseTables.add(jwCourseTable);

                    }
                }
            }
        }
        LOGGER.info("************获取课表 E************");
    }

    /**
     * 获取教学班课表
     * @param tnId
     * @param taskId
     * @param grade
     * @param everyDaySection
     * @param jwScheduleTask
     * @param jwCourseTables
     */
    private void getEduSyllabus(int tnId,int taskId,String grade,Integer everyDaySection,JwScheduleTask jwScheduleTask,List<JwCourseTable> jwCourseTables){

        LOGGER.info("************获取班级设置 S************");
        Map<Integer, String> classMap = this.getClassByTnIdAndTaskId(tnId,Constant.CLASS_EDU_CODE,grade);
        Map<Integer, LinkedHashMap<String, Object>> classObjMap = this.getClassMapByTnIdAndTaskId(tnId,Constant.CLASS_EDU_CODE,grade);
        LOGGER.info("************获取班级设置 E************");

        LOGGER.info("************获取课表 S************");
        List<String> allCourseList = null;
        Map<String, Integer> courses = this.getCourseIdByTnIdAndTaskId();
        try {
            String path = getScheduleTaskPath(taskId, tnId) + Constant.PATH_SCHEDULE_EDU;
//                String path = "/Users/yangyongping/Desktop/yqhc/zgk-saas/saas-service/src/main/resources/config/edu_course_0(1).txt";
            CharSource main = Files.asCharSource(new File(path), Charset.defaultCharset());
            allCourseList = main.readLines();
        } catch (Exception e) {
            throw new BizException("error", "排课数据获取失败");
        }
        Map<String, Object> teachers = this.getTeacherByCourse(tnId, taskId, grade);
        for (String courseLine : allCourseList) {
            String[] courseInfo = courseLine.split(Constant.COURSE_TABLE_LINE_SPLIT_CLASS);
            String courseStr = courseInfo[1];

            Integer classId = null;
            try {
                classId = Integer.valueOf(courseInfo[0]);
            } catch (Exception e) {
                throw new BizException("error", "班级不存在");
            }
            JwSyllabus jwSyllabus = new JwSyllabus();
            jwSyllabus.setInfo(courseStr);
            jwSyllabus.setTaskId(taskId);
            jwSyllabus.setClassId(classId);
            jwSyllabus.setClassType(Constant.CLASS_EDU_CODE);
            jwSyllabusDAO.insert(jwSyllabus);
            String className = classMap.get(classId);


            List<List<String>> weekTempCourses = spiltDay(everyDaySection, courseStr);

            for (int i = 0; i < weekTempCourses.size(); i++) {
                List<String> dayCourseTempList = weekTempCourses.get(i);

                for (int j = 0; j < dayCourseTempList.size(); j++) {
                    String dayRoom = dayCourseTempList.get(j);
                    //教学班对应实际的教学班教室
                    int roomId = Integer.valueOf(dayRoom);
                    int courseId = 0;
                    if (roomId == 0 ) {
                        JwCourseTable jwCourseTable = new JwCourseTable();
                        jwCourseTable.setTnId(tnId);
                        jwCourseTable.setTaskId(taskId);
                        jwCourseTable.setGradeId(Integer.valueOf(jwScheduleTask.getGrade()));
                        jwCourseTable.setClassId(classId);
                        jwCourseTable.setRoomId(roomId);
                        jwCourseTable.setCourseId(courseId);
                        jwCourseTable.setTeacherId(0);
                        jwCourseTable.setStatus(0);
                        jwCourseTable.setWeek(i);
                        jwCourseTable.setSort(j);
                        jwCourseTable.setClassType(Constant.CLASS_EDU_CODE);
                        jwCourseTables.add(jwCourseTable);
                    } else {
                        Map<String,Object> classObj = classObjMap.get(classId);
                        String course = classObj.get("class_major_type").toString();
                        Map<String, Object> teacherMap = (Map<String, Object>) teachers.get(getTeacherKey(course, className));
                        courseId = courses.get(course);
                        //teacherId teacherMap.get("id")
                        //gradeId jwScheduleTask.getGrade()
                        //classId classId
                        //courseId Integer.valueOf(dayCourse)
                        //week i
                        //sort j
                        JwCourseTable jwCourseTable = new JwCourseTable();
                        jwCourseTable.setTnId(tnId);
                        jwCourseTable.setTaskId(taskId);
                        jwCourseTable.setGradeId(Integer.valueOf(jwScheduleTask.getGrade()));
                        jwCourseTable.setClassId(classId);
                        jwCourseTable.setCourseId(courseId);
                        jwCourseTable.setRoomId(roomId);
                        jwCourseTable.setTeacherId(Integer.valueOf(teacherMap.get("id").toString()));
                        jwCourseTable.setWeek(i);
                        jwCourseTable.setSort(j);
                        jwCourseTable.setStatus(Constant.COURSE_STATUS_Y);
                        jwCourseTable.setClassType(Constant.CLASS_EDU_CODE);
                        jwCourseTables.add(jwCourseTable);

                    }
                }
            }
        }
        LOGGER.info("************获取课表 E************");
    }
    @Override
    public List queryRoom(int taskId,int tnId){
        JwScheduleTask jwScheduleTask = iJwScheduleTaskDAO.fetch(taskId);
        Map<String,Object> param = new HashMap<>();
        param.put("tnId",tnId);
        param.put("gradeCode",jwScheduleTask.getGrade());
        Grade grade = (Grade) gradeService.queryOne(param);
        Map<Integer,LinkedHashMap<String,Object>> classMap = this.getClassMapByTnIdAndTaskId(tnId,Constant.CLASS_ADM_CODE,grade.getGrade());

        List<Map<String,Object> > rtnList = new ArrayList<>();
        Map<String,Object> room;
        List<StringBuffer> buffers = this.getClassRoom(taskId,tnId);
        buffers.remove(0);
        for (StringBuffer ss : buffers){
            room = new HashMap<>();
            int roomId = Integer.valueOf(ss.toString().replace("\r\n",""));
            if (roomId>0){
                Map<String,Object> classObj = classMap.get(roomId);
                room.put("roomId",classObj.get("id"));
                room.put("roomName",classObj.get("class_name")+"教室");
                rtnList.add(room);
            }else {
                room.put("roomId",roomId);
                room.put("roomName","教室"+Math.abs(roomId));
                rtnList.add(room);
            }
        }
        return rtnList;
    }
}
