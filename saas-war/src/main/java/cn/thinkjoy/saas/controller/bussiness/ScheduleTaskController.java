package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.domain.bussiness.CourseResultView;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.JwScheduleTaskDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeEnum;
import cn.thinkjoy.saas.enums.StatusEnum;
import cn.thinkjoy.saas.enums.TermEnum;
import cn.thinkjoy.saas.service.*;
import cn.thinkjoy.saas.service.bussiness.*;
import cn.thinkjoy.saas.service.common.ExcelUtils;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.saas.service.common.FileOperation;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by yangyongping on 2016/12/6.
 *
 * 排课任务CRUD
 */
@Controller
@RequestMapping("/scheduleTask")
public class ScheduleTaskController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTaskController.class);

    @Autowired
    private IJwScheduleTaskService jwScheduleTaskService;

    @Autowired
    private IEXScheduleBaseInfoService iexScheduleBaseInfoService;

    @Autowired
    private IJwCourseService jwCourseService;

    @Autowired
    private IJwTeacherService jwTeacherService;

    @Autowired
    private IEXTenantCustomService iexTenantCustomService;

    @Autowired
    private IJwTeachDateService jwTeachDateService;

    @Resource
    private IEXJwScheduleTaskService iexJwScheduleTaskService;

    @Autowired
    private ICourseBaseInfoService courseBaseInfoService;

    @Autowired
    IExTeachTimeService teachTimeService;

    @Autowired
    IEXTeacherService iexTeacherService;

    @Autowired
    EXITenantConfigInstanceService exiTenantConfigInstanceService;

    @Autowired
    private ISyllabusService syllabusService;

    @Autowired
    private EXIGradeService exiGradeService;
    /**
     * 新建排课任务
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveScheduleTask")
    public boolean saveScheduleTask(@RequestParam String scheduleName,
                                               @RequestParam String grade,
                                               @RequestParam String year,
                                               @RequestParam String term){
        //参数校验
        if (StringUtils.isEmpty(scheduleName)||StringUtils.isEmpty(grade)||StringUtils.isEmpty(year)||StringUtils.isEmpty(term)){
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }
        JwScheduleTask jwScheduleTask = new JwScheduleTask();
        jwScheduleTask.setScheduleName(scheduleName);
        jwScheduleTask.setGrade(grade);
        jwScheduleTask.setTerm(term);
        jwScheduleTask.setYear(year);
        jwScheduleTask.setDelStatus(StatusEnum.Y.getCode());
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        jwScheduleTask.setTnId(tnId);
        jwScheduleTask.setCreateDate(System.currentTimeMillis());
        jwScheduleTask.setStatus(Constant.SCHEDULE_TASK_INIT_STATUS);
        boolean flag = jwScheduleTaskService.add(jwScheduleTask)>0;
        //初始化数据
        teachTimeService.saveTeachTime(Integer.valueOf(jwScheduleTask.getId().toString()),Constant.DEFULT_TEACH_DATE,Constant.DEFULT_TEACH_TIME,tnId);
        return flag;
    }
    /**
     * 查询拍客任务状态
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryScheduleTaskStatus")
    public Integer queryScheduleTaskStatus(@RequestParam Integer taskId){
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        return jwScheduleTask.getStatus();
    }

    /**
     *
     * @param type class:班级 teacher:老师
     * @param taskId 任务Id
     * @param id 班级/老师Id
     * @param source 源坐标 坐标[1,6] 列号(周几),行号(第几节)
     * @param target 目标坐标(要换到的坐标) 坐标[2,6] 列号(周几),行号(第几节)
     * @return
     */
    @ResponseBody
    @RequestMapping("/{type}/exchange")
    public boolean classExchange(@PathVariable String type,@RequestParam Integer taskId,@RequestParam Integer id,@RequestParam String source,@RequestParam String target){
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        int[] sourceCoord = this.getIntCoord(source);
        int[] targetCoord = this.getIntCoord(target);
        switch (type){
            case Constant.TABLE_TYPE_CLASS:
                return syllabusService.classExchange(tnId,taskId,id,sourceCoord,targetCoord);
            case Constant.TABLE_TYPE_TEACHER:
                return syllabusService.teacherExchange(tnId,taskId,id,sourceCoord,targetCoord);
            default:
                return false;
        }
    }

    /**
     * 根据坐标获取调课所需班级课表
     * @param taskId 任务ID
     * @param id 老师Id
     * @param coord 坐标[1,6] 列号(周几),行号(第几节)
     * @return
     */
    @ResponseBody
    @RequestMapping("/teacher/queryClassByCoord")
    public List<List<String>> queryClassByCoord(@RequestParam Integer taskId, @RequestParam Integer id,@RequestParam String coord){
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        Map<String,Object> params;
        int[] currCoord = this.getIntCoord(coord);
        CourseResultView courseResultView = null;

        List<List<String>> lists;
        Map<String, Object> timeConfigMap;
        int courseCount,dayCount;
        timeConfigMap = iexJwScheduleTaskService.getCourseTimeConfig(tnId, taskId);

        if (timeConfigMap == null)
            throw new BizException("error", "当前租户下排课任务课时设置为空,请设置后再试");

        dayCount = ((List)timeConfigMap.get("list")).size();
        courseCount = (int)timeConfigMap.get("count");
        lists = syllabusService.genDefaultSyllabus(dayCount,courseCount);
        List<JwCourseTable> jwCourseTables = syllabusService.getSyllabusByCoordinate(tnId,taskId,id,currCoord,Constant.TABLE_TYPE_TEACHER);
        if (jwCourseTables.size() == 0){
            throw new BizException("error","请检查坐标是否正确!");
        }

        for (JwCourseTable jwCourseTable : jwCourseTables){
            params = new HashMap<>();
            params.put("classId",jwCourseTable.getClassId());
            courseResultView = syllabusService.genSyllabus(tnId,taskId,Constant.TABLE_TYPE_TEACHER,params,lists,timeConfigMap);
        }

        return courseResultView.getWeek();
    }


    /**
     * 根据坐标获取调课状态
     * @param type class:班级 teacher:老师
     * @param taskId 任务ID
     * @param id 班级/老师Id
     * @param coord 坐标[1,6] 列号(周几),行号(第几节)
     * @return
     */
    @ResponseBody
    @RequestMapping("/{type}/queryStatusByCoord")
    public boolean queryStatusByCoord(@PathVariable String type, @RequestParam Integer taskId, @RequestParam Integer id,@RequestParam String coord){
        int adjustmentType;
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        int[] currCoord = this.getIntCoord(coord);
        switch (type){
            case Constant.TABLE_TYPE_TEACHER:
                adjustmentType = 0;
                break;
            case Constant.TABLE_TYPE_CLASS:
                adjustmentType = 1;
                break;
            default:
                adjustmentType = 0;
                break;
        }
        List<JwCourseTable> list = syllabusService.getSyllabusByCoordinate(tnId,taskId,id,currCoord,type);
        if (list.size() == 0){
            throw new BizException("error","请检查坐标是否正确!");
        }
        JwCourseTable jwCourseTable = list.get(0);
        iexJwScheduleTaskService.SerializableAdjustmentSchedule(jwCourseTable,adjustmentType);
        return true;
    }

    /**
     * 一键排课
     * @return
     */
    @ResponseBody
    @RequestMapping("/trigger")
    public boolean updateScheduleTaskStatus(@RequestParam Integer taskId,@RequestParam Integer tnId) throws IOException {

        boolean initBool = iexJwScheduleTaskService.initParmasFile(taskId, tnId);


        if (initBool) {
            String type= iexJwScheduleTaskService.getClsssTypeTagByTaskId(taskId, tnId);
            String path = FileOperation.getParamsPath(tnId, taskId,type);
            JwScheduleTask jwScheduleTask = new JwScheduleTask();
            jwScheduleTask.setId(taskId);
            jwScheduleTask.setStatus(Constant.TASK_SUCCESS);
            jwScheduleTask.setPath(path);
            initBool = jwScheduleTaskService.update(jwScheduleTask) > 0;
        }
        return initBool;
    }
    /**
     * 重新排课
     * @return
     */
    @ResponseBody
    @RequestMapping("/reload/trigger")
    public boolean reloadTrigger(@RequestParam Integer taskId,@RequestParam Integer tnId) throws IOException {

        String rePath = iexJwScheduleTaskService.getScheduleTaskPath(taskId, tnId);

//        String type= iexJwScheduleTaskService.getClsssTypeTagByTaskId(taskId, tnId);

//        String path = FileOperation.getParamsPath(tnId, taskId,type);

        File file = new File(rePath);

        if(file.exists())
             FileOperation.removeAllFile(file);

        boolean initBool = iexJwScheduleTaskService.initParmasFile(taskId, tnId);

        if (initBool) {
            String type= iexJwScheduleTaskService.getClsssTypeTagByTaskId(taskId, tnId);
            String path = FileOperation.getParamsPath(tnId, taskId,type);
            JwScheduleTask jwScheduleTask = new JwScheduleTask();
            jwScheduleTask.setId(taskId);
            jwScheduleTask.setStatus(Constant.TASK_SUCCESS);
            jwScheduleTask.setPath(path);
            initBool = jwScheduleTaskService.update(jwScheduleTask) > 0;
        }

        return initBool;
    }



    /**
     * 排课结果查询
     * @param taskId
     * @param tnId
     * @return
     */
    @ResponseBody
    @RequestMapping("/state")
    public String scheduleResult(@RequestParam Integer taskId,@RequestParam Integer tnId) {
        return iexJwScheduleTaskService.getSchduleResultStatus(taskId, tnId);
    }

    /**
     * 硬性规则
     * @param taskId
     * @param tnId
     * @return
     */
    @ResponseBody
    @RequestMapping("/error/desc")
    public List<String> getSchduleErrorDesc(@RequestParam Integer taskId,@RequestParam Integer tnId){
           return  iexJwScheduleTaskService.getSchduleErrorDesc(taskId,tnId);
    }

    /**
     * 软性规则
     * @param taskId
     * @param tnId
     * @return
     */
    @ResponseBody
    @RequestMapping("/pliable/rule")
    public List<String> getNoNScheduleTaskPliableRule(@RequestParam Integer taskId,@RequestParam Integer tnId){
        return  iexJwScheduleTaskService.getNoNScheduleTaskPliableRule(taskId, tnId);
    }
    /**
     * 调课结果查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/adjustment/schedule/result")
    public String  adjustmentSchedule(@RequestParam Integer taskId,@RequestParam Integer tnId) {
        return  iexJwScheduleTaskService.getAdjustmentSchduleResult(taskId,tnId);
    }

    /**
     * 调课成功
     * @return
     */
    @ResponseBody
    @RequestMapping("/adjustment/success")
    public List<String> adjustmentScheduleSuccess(@RequestParam Integer taskId,@RequestParam Integer tnId){
        return iexJwScheduleTaskService.getAdjustmentInfo(taskId,tnId);
    }

    /**
     * 修改排课任务
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateScheduleTask")
    public boolean updateScheduleTask(@RequestParam Integer id,
                                    @RequestParam String scheduleName,
                                    @RequestParam String grade,
                                    @RequestParam String year,
                                    @RequestParam String term){
        //参数校验
        if (StringUtils.isEmpty(scheduleName)||StringUtils.isEmpty(grade)||StringUtils.isEmpty(year)||StringUtils.isEmpty(term)){
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }
        JwScheduleTask jwScheduleTask = new JwScheduleTask();
        jwScheduleTask.setId(id);
        jwScheduleTask.setScheduleName(scheduleName);
        jwScheduleTask.setGrade(grade);
        jwScheduleTask.setTerm(term);
        jwScheduleTask.setYear(year);
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        jwScheduleTask.setTnId(tnId);
        jwScheduleTask.setCreateDate(System.currentTimeMillis());
        jwScheduleTask.setStatus(Constant.SCHEDULE_TASK_INIT_STATUS);
        return jwScheduleTaskService.update(jwScheduleTask)>0;
    }

    /**
     * 删除排课任务
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteScheduleTask")
    public boolean deleteScheduleTask(@RequestParam Integer id){
        //参数校验
        JwScheduleTask jwScheduleTask = new JwScheduleTask();
        jwScheduleTask.setId(id);
        jwScheduleTask.setDelStatus(id);
        return jwScheduleTaskService.update(jwScheduleTask)>0;
    }

    /**
     * 查询单个排课任务
     * @return
     */
    @ResponseBody
    @RequestMapping("/fetchScheduleTask")
    public JwScheduleTask fetchScheduleTask(@RequestParam Integer id){
        //参数校验
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",id);
        map.put("delStatus",StatusEnum.Y.getCode());
        JwScheduleTask jwScheduleTask = (JwScheduleTask) jwScheduleTaskService.queryOne(map);
        return jwScheduleTask==null?new JwScheduleTask():jwScheduleTask;
    }


    @ResponseBody
    @RequestMapping("/queryGradeInfo")
    public List<Map<String,Object>> queryGradeInfo(){
        List<Map<String,Object>> list = new ArrayList();
        Map<String,Object> map = Maps.newHashMap();
        map.put("key",GradeEnum.GAOYI.getCode());
        map.put("value",GradeEnum.GAOYI.getGradeName());
        list.add(map);
        map = Maps.newHashMap();
        map.put("key",GradeEnum.GAOER.getCode());
        map.put("value",GradeEnum.GAOER.getGradeName());
        list.add(map);
        map = Maps.newHashMap();
        map.put("key",GradeEnum.GAOSAN.getCode());
        map.put("value",GradeEnum.GAOSAN.getGradeName());
        list.add(map);
        return list;
    }

    @ResponseBody
    @RequestMapping("/queryScheduleTask")
    public List<JwScheduleTaskDto> queryScheduleTask(){
        List<JwScheduleTaskDto> returnList = new ArrayList<>();
        Map<String,Object> map = Maps.newHashMap();
        map.put("tnId",UserContext.getCurrentUser().getTnId());
        map.put("delStatus", StatusEnum.Y.getCode());
        List<JwScheduleTask> dataList = jwScheduleTaskService.queryList(map,"createDate","desc");
        if (dataList!=null&&dataList.size()>0){
            returnList = JwScheduleTask2Dto(dataList);
        }
        return returnList;
    }

    /**
     * 检查表字段是否完整
     * @param taskId
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkTaskBaseInfo")
    public boolean checkTaskBaseInfo(@RequestParam Integer taskId){
        JwScheduleTask jwScheduleTask = fetchScheduleTask(taskId);
        if (jwScheduleTask.getGrade()==null){
            throw new BizException("error","该任务已经被删除或未创建");
        }
        //获取所有字段名称
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        String tableName = ParamsUtils.combinationTableName("student",tnId );
        Map<String,Object> params = Maps.newHashMap();
        params.put("tableName",tableName);
        params.put("columns",Constant.CHECK_TABLE_STUDENT_COLUMNS);
        params.put("searchKey",Constant.STUDENT_GRADE);
        params.put("searchValue",GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())));
        Map<String,Object> map = null;
        try {
            map = iexTenantCustomService.existDataCount(params);
        }catch (Exception e){
            throw new BizException(ErrorCode.TASK_ERROR.getCode(),"您还未上传学生信息，请至学生管理中完善!");
        }

//        Iterator<String> iterator = map.keySet().iterator();
//        List<String> emptyColumns = new ArrayList<>();
//        while (iterator.hasNext()){
//            String key = iterator.next();
//            if ("0".equals(map.get(key)==null?null:map.get(key).toString())) {
//                emptyColumns.add(key);
//            }
//        }
//        if (emptyColumns.size()>0) {
//            StringBuffer buffer = new StringBuffer();
//            buffer.append("您还未填写");
//            for (String s : emptyColumns) {
//                Map<String, Object> queryMap = Maps.newHashMap();
//                queryMap.put("domain", Constant.STUDENT);
//                queryMap.put("enName", s);
//                String cnName = exiConfigurationService.selectColumnName(queryMap);
//                buffer.append(cnName).append("、");
//            }
//            buffer.delete(buffer.length()-1,buffer.length());
//            buffer.append("字段信息，请至学生管理中完善");
//            throw new BizException(ErrorCode.TASK_ERROR.getCode(), buffer.toString());
//        }
        return true;
    }



    private List<JwScheduleTaskDto> JwScheduleTask2Dto(List<JwScheduleTask> jwScheduleTasks){
        List<JwScheduleTaskDto> list = new ArrayList<>();
        if (jwScheduleTasks!=null && jwScheduleTasks.size()>0) {
            for (JwScheduleTask jwScheduleTask:jwScheduleTasks){
                list.add(JwScheduleTask2Dto(jwScheduleTask));
            }
        }
        return list;
    }

    private JwScheduleTaskDto JwScheduleTask2Dto(JwScheduleTask jwScheduleTask){
        if (jwScheduleTask==null)return null;
        JwScheduleTaskDto dto = new JwScheduleTaskDto();
        BeanUtils.copyProperties(jwScheduleTask,dto);
        dto.setGradeName(GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())));
        dto.setTermName(TermEnum.getName(Integer.valueOf(jwScheduleTask.getTerm())));
        return dto;
    }

    @ResponseBody
    @ApiDesc(value = "根据任务ID获取课程信息",owner = "gryang")
    @RequestMapping(value = "/queryCourseInfoByTaskId",method = RequestMethod.GET)
    public List<CourseBaseDto> queryCourseInfoByTaskId(@RequestParam int taskId){
        List<CourseBaseDto> dtos = iexScheduleBaseInfoService.queryCourseInfoByTaskId(taskId);
        return dtos;
    }

    @ResponseBody
    @ApiDesc(value = "添加或修改课程课时信息",owner = "gryang")
    @RequestMapping(value = "/saveOrUpdateCourseTime",method = RequestMethod.POST)
    public Map saveOrUpdateCourseTime(@RequestParam int taskId,@RequestParam int courseId,@RequestParam String time){

        int chour = 0;
        try {
            String [] arr = time.split("\\+");
            if(arr.length == 1){
                chour = Integer.valueOf(arr[0]);
            }else if (arr.length == 2){
                chour = Integer.valueOf(arr[0]) + 2*Integer.valueOf(arr[1]);
            }
        }catch (Exception e){
            logger.error("课时输入错误 : time = "+time,e);
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("taskId",taskId);
        paramMap.put("courseId",courseId);
        JwCourse course = (JwCourse) jwCourseService.queryOne(paramMap,"id",SqlOrderEnum.DESC);
        if(course != null){
            course.setCourseHour(time);
            course.setChour(chour);
            jwCourseService.update(course);
        }else {
            course = new JwCourse();
            course.setCourseId(courseId);
            course.setCourseHour(time);
            course.setChour(chour);
            course.setTaskId(taskId);
            course.setTnId(iexScheduleBaseInfoService.getTnIdByTaskId(taskId));
            jwCourseService.insert(course);
        }

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "根据任务ID获取教师信息",owner = "gryang")
    @RequestMapping(value = "/queryTeacherByTaskId",method = RequestMethod.GET)
    public List<TeacherBaseDto> queryTeacherByTaskId(@RequestParam int taskId){
        List<TeacherBaseDto> dtos = iexScheduleBaseInfoService.queryTeacherByTaskId(taskId);
        return dtos;
    }

    @ResponseBody
    @ApiDesc(value = "保存教师排课设置",owner = "gryang")
    @RequestMapping(value = "/saveTeacherSchedule",method = RequestMethod.POST)
    public Map saveTeacherSchedule(@RequestParam String str){
        // str 传输规则 : 记录ID-设置（0：不排课，1：排课）多个逗号隔开，eg:1-1,2-0,3-1
        String [] strArr = str.split(",");
        for(int i=0;i<strArr.length;i++){
            Integer id = Integer.valueOf(StringUtils.substringBefore(strArr[i],"-"));
            Integer isAttend = Integer.valueOf(StringUtils.substringAfter(strArr[i],"-"));

            JwTeacher jwTeacher = new JwTeacher();
            jwTeacher.setIsAttend(isAttend);
            jwTeacher.setId(id);
            jwTeacherService.update(jwTeacher);

            // 如果对教师排课，则异步插入教师规则数据
            if(isAttend == 1){
                iexScheduleBaseInfoService.insertBaseRule(id,isAttend);
            }
        }

        return Maps.newHashMap();
    }


//    @ResponseBody
//    @ApiDesc(value = "自动补全教师姓名",owner = "gryang")
//    @RequestMapping(value = "/queryTeacherByKeyWord",method = RequestMethod.GET)
//    public List<TeacherBaseDto> queryTeacherByKeyWord(@RequestParam int taskId,@RequestParam String keyWord){
//        List<TeacherBaseDto> dtos = iexScheduleBaseInfoService.queryTeacherByKeyWord(taskId,keyWord);
//        return dtos;
//    }

//    @ResponseBody
//    @ApiDesc(value = "保存或修改教师配置信息",owner = "gryang")
//    @RequestMapping(value = "/saveOrUpdateTeacher",method = RequestMethod.POST)
//    public Map saveOrUpdateTeacher(@RequestParam int taskId,
//                                   @RequestParam int teacherId,
//                                   @RequestParam int classNum,
//                                   @RequestParam String course,
//                                   @RequestParam String classId){
//
//        iexScheduleBaseInfoService.saveOrUpdateTeacher(
//                taskId,
//                teacherId,
//                classNum,
//                course,
//                classId
//        );
//
//        return Maps.newHashMap();
//    }

//    @ResponseBody
//    @ApiDesc(value = "删除教师配置信息",owner = "gryang")
//    @RequestMapping(value = "/deleteTeacher",method = RequestMethod.GET)
//    public Map deleteTeacher(@RequestParam int taskId,@RequestParam int teacherId){
//
//        Map<String,Object> paramMap = Maps.newHashMap();
//        paramMap.put("teacherId",teacherId);
//        paramMap.put("taskId",taskId);
//        jwTeacherService.deleteByCondition(paramMap);
//
//        return Maps.newHashMap();
//    }

    @ResponseBody
    @ApiDesc(value = "根据任务ID检测基础信息是否完善",owner = "gryang")
    @RequestMapping(value = "/checkInfoIsPerfect",method = RequestMethod.GET)
    public Map checkInfoIsPerfect(@RequestParam int taskId){

        // 检测教学时间是否填写
        List<JwTeachDate> dates = jwTeachDateService.findList("task_id",taskId);
        if(dates.size() == 0){
            ExceptionUtil.throwException(ErrorCode.TEACH_DATE_ERROR);
        }

        // 检测课程课时信息是否填写
        List<JwCourse> courses = jwCourseService.findList("task_id",taskId);
        if(courses.size() == 0){
            ExceptionUtil.throwException(ErrorCode.COURSE_INFO_ERROR);
        }

        // 检测课程课时信息是否填写完整(给所有课程是否已设置课时)
        for(JwCourse course : courses){
            if(StringUtils.isEmpty(course.getCourseHour())){
                ExceptionUtil.throwException(ErrorCode.COURSE_INFO_NOT_PERFECT);
            }
        }

        // 检测教师信息是否填写
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("taskId",taskId);
        paramMap.put("isAttend",1);
        List<JwTeacher> teachers = jwTeacherService.queryList(paramMap,null,null);
        if(teachers.size() == 0){
            ExceptionUtil.throwException(ErrorCode.TEACHER_INFO_ERROR);
        }

        // 检测教师信息是否填写完整(给所有课程是否已经设置教师)
        Map<Integer,String> map = Maps.newHashMap(); // 存放教师ID与课程名
        for(JwCourse course : courses){
            CourseBaseInfo info = (CourseBaseInfo) courseBaseInfoService.fetch(course.getCourseId());

            if(info == null){
                continue;
            }

            boolean flag = false;
            for(JwTeacher teacher : teachers){

                String teachCourse = "";
                if(map.get(teacher.getTeacherId()) != null){
                    teachCourse = map.get(teacher.getTeacherId());
                }else {
                    TeacherBaseDto dto = iexTenantCustomService.getTeacherInfo(
                            teacher.getTnId(),
                            teacher.getTeacherId()
                    );
                    map.put(dto.getTeacherId(),dto.getCourseName());
                    teachCourse = dto.getCourseName();
                }

                if(teachCourse.equals(info.getCourseName())){
                    flag = true;
                    break;
                }
            }

            if(!flag){
                ExceptionUtil.throwException(ErrorCode.TEACHER_INFO_NOT_PERFECT);
            }
        }

        return Maps.newHashMap();
    }

    /**
     *
     * @param type 类型分为: room:教室 class:班级  student:学生 all:总课表 teacher:总课表
     * @param taskId
     * @param param 根据type不同而组合的参数 room:{"roomId":1} class:{"classId":1,"classType":0}(classType:0:行政班,1:教学班)
     *              student:{"studentNo":1111111} teacher:{"teacherId":1}
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/{type}/course/result",method = RequestMethod.GET)
    @ResponseBody
    public Map getCourseResult(@PathVariable String type,@RequestParam Integer taskId,String param) throws IOException, ParseException {
        Map<String,Object> paramsMap  = null;
        CourseResultView courseResultView = null;
        int teacherId,classId,classType,roomId;
        long studentNo;
        if (param!=null) {
            try {
                paramsMap = JSON.parseObject(param);
            } catch (Exception e) {
                logger.info("request参数为空");
            }
        }
        Map resultMap = new HashMap();
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        if (Constant.TABLE_TYPE_ALL.equals(type)){
//            iexJwScheduleTaskService.getAllCourseResult(taskId, tnId);
            resultMap.put("result",syllabusService.getAllSyllabus(tnId, taskId));
            return resultMap;
        }
        switch (type){
            case Constant.TABLE_TYPE_TEACHER:
                teacherId = Integer.valueOf(paramsMap.get("teacherId").toString());
                courseResultView = syllabusService.getTeacherSyllabus(tnId,taskId,teacherId);
                break;
            case Constant.TABLE_TYPE_CLASS:
                try {
                    classId = Integer.valueOf(paramsMap.get("classId").toString());
                    classType = Integer.valueOf(paramsMap.get("classType").toString());
                }catch (Exception e){
                    throw new BizException("error","参数获取异常!");
                }
                courseResultView = syllabusService.getClassSyllabus(tnId,taskId,classId,classType);
                break;
            case Constant.STUDENT:
                studentNo = Long.valueOf(paramsMap.get("studentNo").toString());
                courseResultView = syllabusService.getStudentSyllabus(tnId,taskId,studentNo);
                break;
            case Constant.TABLE_TYPE_ROOM:
                roomId = Integer.valueOf(paramsMap.get("roomId").toString());
                courseResultView = syllabusService.getRoomSyllabus(tnId,taskId,roomId);
                break;
            default:
                break;
        }

        resultMap.put("result",courseResultView);
        return resultMap;
    }

    /**
     * 排课结果
     * @return
     */
    @RequestMapping(value = "/{type}/course/export",method = RequestMethod.GET)
    @ResponseBody
    public Map getCourseExport(@PathVariable String type, @RequestParam Integer taskId, HttpServletResponse response) throws IOException {
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        Map<String, Object> param;
        String[] sheetNames = null;
        String[] strings;
        Workbook workbook;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //参数校验
        param = Maps.newHashMap();
        param.put("id", taskId);
        param.put("delStatus", StatusEnum.Y.getCode());
        JwScheduleTask jwScheduleTask = (JwScheduleTask) jwScheduleTaskService.queryOne(param);
        List<List<List<String>>> courseLists = new ArrayList<>();
        Map<String, Object> courseTimeConfig = iexJwScheduleTaskService.getCourseTimeConfig(tnId, taskId);
        int count = ((List) courseTimeConfig.get("list")).size();
        strings = new String[count];
        for (int i = 0; i < count; i++) {
            strings[i] = getWeek(i);
        }

        switch (type) {
            case Constant.TABLE_TYPE_TEACHER:
                //获取本次排课所有教师
                logger.info("***********导出教师课表 S***********");
                param = new HashMap<>();
                param.put("teacher_grade", GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())));
                List<LinkedHashMap<String, Object>> list = iexTeacherService.getScheduleTeacherByTnIdAndTaskId(tnId, taskId, param);
                sheetNames = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    LinkedHashMap<String, Object> map = list.get(i);
                    sheetNames[i] = map.get("teacher_name").toString();
                    int teacherId = Integer.valueOf(map.get("id").toString());
                    CourseResultView courseResultView = syllabusService.getTeacherSyllabus(tnId,taskId,teacherId);
                    courseLists.add(courseResultView.getWeek());
                }

                logger.info("***********导出教师课表 E***********");
                break;
            case Constant.TABLE_TYPE_CLASS:
                logger.info("***********导出班级课表 S***********");
                //获取本次排课所有班级
                //获取行政班班级列表
                List<LinkedHashMap<String, Object>> classList = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())), Constant.CLASS_ADM);
                sheetNames = new String[classList.size()];
                for (int i = 0; i < classList.size(); i++) {
                    LinkedHashMap<String, Object> map = classList.get(i);
                    sheetNames[i] = map.get("class_name").toString();
                    int classId = Integer.valueOf(map.get("id").toString());
                    CourseResultView courseResultView = syllabusService.getClassSyllabus(tnId,taskId,classId,Constant.CLASS_ADM_CODE);
                    courseLists.add(courseResultView.getWeek());
                }
                logger.info("***********导出班级课表 E***********");
                break;
            case Constant.STUDENT:
                String tableName = ParamsUtils.combinationTableName(Constant.STUDENT, tnId);
                if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(tableName)) {
                    return null;
                }
                List<Map<String,Object>> params = new ArrayList<>();
                Set<Integer> gradeCodeSet = new HashSet<>();
                gradeCodeSet.add(Integer.valueOf(jwScheduleTask.getGrade()));
                List<Grade> gradeList = exiGradeService.getGradeByTnIdAndGradeCode(tnId,gradeCodeSet);
                String grade = gradeList.get(0).getGrade();
                param = new HashMap<>();
                param.put("key", "student_grade");
                param.put("op", "=");
                param.put("value", grade);
                params.add(param);
                List<LinkedHashMap<String, Object>> tenantCustoms = exiTenantConfigInstanceService.likeTableByParams(tableName,params);
                Iterator<LinkedHashMap<String,Object>> studentIterator = tenantCustoms.iterator();
                Map<String,Integer> admClassMap = iexJwScheduleTaskService.getClassMapByTnId(tnId,Constant.CLASS_ADM_CODE,grade);
                Map<String,Integer> eduClassMap = iexJwScheduleTaskService.getClassMapByTnId(tnId,Constant.CLASS_EDU_CODE,grade);
                sheetNames = new String[tenantCustoms.size()];
                int cc = 0;
                while (studentIterator.hasNext()) {

                    Map<String,Object> studentMap = studentIterator.next();
                    sheetNames[cc] =(String) studentMap.get("student_name");
                            List<Map<String,Object>> studentClassList = syllabusService.getClassList(studentMap,admClassMap,eduClassMap);
                    CourseResultView courseResultView = syllabusService.getStudentSyllabus(tnId, taskId,courseTimeConfig,studentClassList);
                    courseLists.add(courseResultView.getWeek());
                }

                break;
            case Constant.TABLE_TYPE_ROOM:
                //教室
                List<Map<String,Object>> roomList = iexJwScheduleTaskService.queryRoom(taskId,tnId);
                sheetNames = new String[roomList.size()];

                for (int i = 0 ; i <roomList.size() ; i ++){
                    Map<String,Object> room = roomList.get(i);
                    sheetNames[i] = room.get("roomName").toString();
                    int roomId = Integer.valueOf(room.get("roomId").toString());
                    CourseResultView courseResultView = syllabusService.getRoomSyllabus(tnId,taskId,roomId);
                    courseLists.add(courseResultView.getWeek());
                }

            default:
                break;
        }
        logger.info("===============导出租户课程表 S================");
        if (sheetNames.length>0) {
            workbook = ExcelUtils.createWorkBook(strings, sheetNames, courseLists);
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((ExcelUtils.getFileName(type, tnId) + ".xls").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[20480];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                logger.info("Excel文件流导出完成");
            } catch (final IOException e) {
                logger.info("Excel文件流导出失败![" + e.getMessage() + "]");
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
                if (out != null)
                    out.close();
                logger.info("===============导出租户课程表 E================");
            }
        }
        return null;
    }



    private static String getWeek(int i){
        switch (i){
            case 0:
                return "星期一";
            case 1:
                return "星期二";
            case 2:
                return "星期三";
            case 3:
                return "星期四";
            case 4:
                return "星期五";
            case 5:
                return "星期六";
            case 6:
                return "星期日";
            default:
                return null;
        }
    }

    private static int[] getIntCoord(String s){
        try {
            return JSON.parseObject(s, int[].class);
        }catch (Exception e){
            throw new BizException("error","格式化坐标失败!");
        }
    }

    @RequestMapping("getConfigRooms")
    @ResponseBody
    public Map<String,Object> getConfigRooms(@RequestParam("taskId")String taskId){
        return iexJwScheduleTaskService.getConfigRooms(taskId);
    }

    @RequestMapping(value = "updateClassRoom",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateClassRoom(@RequestParam("classRoomId")String classRoomId,@RequestParam("scheduleNumber")int scheduleNumber){
        return iexJwScheduleTaskService.updateClassRoom(classRoomId,scheduleNumber);
    }

    @RequestMapping("initTable")
    @ResponseBody
    public boolean initTable(int taskId){
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        iexJwScheduleTaskService.getCourseResult(tnId,taskId);
        return true;
    }

}
