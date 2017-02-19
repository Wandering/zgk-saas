package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.JwCourse;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.domain.JwTeachDate;
import cn.thinkjoy.saas.domain.JwTeacher;
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
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    IJwScheduleTaskService jwScheduleTaskService;

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
     * 一键排课
     * @return
     */
    @ResponseBody
    @RequestMapping("/trigger")
    public boolean updateScheduleTaskStatus(@RequestParam Integer taskId,@RequestParam Integer tnId) throws IOException {

        boolean initBool = iexJwScheduleTaskService.InitParmasFile(taskId, tnId);

        if (initBool) {
            JwScheduleTask jwScheduleTask = new JwScheduleTask();
            jwScheduleTask.setId(taskId);
            jwScheduleTask.setStatus(Constant.TASK_SUCCESS);

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
     * 排课结果
     * @return
     */
    @RequestMapping(value = "/{type}/course/result",method = RequestMethod.GET)
    @ResponseBody
    public Map getCourseResult(@PathVariable String type,@RequestParam Integer taskId,String param) throws IOException, ParseException {
        Map<String,Object> paramsMap  = null;
        if (param!=null) {
            try {
                paramsMap = JSON.parseObject(param);
            } catch (Exception e) {
                paramsMap = Maps.newHashMap();
            }
        }
        Map resultMap = new HashMap();
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        if ("all".equals(type)){
            resultMap.put("result",iexJwScheduleTaskService.getAllCourseResult(taskId, tnId));
            return resultMap;
        }
        CourseResultView courseResultView = iexJwScheduleTaskService.getCourseResult(type,taskId, tnId,paramsMap);


        resultMap.put("result",courseResultView);
        return resultMap;
    }
    public static void main(String[] args) {
        String param = "{\"course\":\"外语\",\"teacherId\":\"\"}";
        Map<String,Object>  map = JSON.parseObject(param);
        System.out.println(map.size());

    }
}
