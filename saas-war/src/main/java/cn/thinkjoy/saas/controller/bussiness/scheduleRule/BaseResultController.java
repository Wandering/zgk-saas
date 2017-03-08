package cn.thinkjoy.saas.controller.bussiness.scheduleRule;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.domain.JwScheduleTask;
import cn.thinkjoy.saas.dto.CourseManageDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeEnum;
import cn.thinkjoy.saas.service.IGradeService;
import cn.thinkjoy.saas.service.IJwScheduleTaskService;
import cn.thinkjoy.saas.service.bussiness.*;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.collect.Maps;
import freemarker.ext.beans.HashAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by yangyongping on 2016/12/10.
 */
@Controller
@RequestMapping("/baseResult")
public class BaseResultController {
    @Autowired
    private IJwScheduleTaskService jwScheduleTaskService;
    @Autowired
    private IEXJwScheduleTaskService iexJwScheduleTaskService;
    @Autowired
    EXITenantConfigInstanceService exiTenantConfigInstanceService;
    @Autowired
    private IEXCourseManageService courseManageService;
    @Autowired
    private EXIGradeService exiGradeService;
    @Autowired
    private IGradeService gradeService;
    @Autowired
    private IEXTeacherService teacherService;


    /**
     * 获取教室名称
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/queryClass",method = RequestMethod.GET)
    @ResponseBody
    public List queryClass(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        //获取行政班班级列表
        List<Map<String,Object>> admList = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())),Constant.CLASS_ADM);
        List<Map<String,Object>> eduList = exiTenantConfigInstanceService.getClassByTnIdAndGrade(tnId, GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())),Constant.CLASS_EDU);
        for (Map<String,Object> map : admList){
            map.put("class_type",Constant.CLASS_ADM_CODE);
        }
        for (Map<String,Object> map : eduList){
            map.put("class_type",Constant.CLASS_EDU_CODE);
        }
        List<Map<String,Object>> list = new ArrayList<>();
        list.addAll(admList);
        list.addAll(eduList);
        return list;
    }

    /**
     * 获取教室名称列表
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/queryRoom",method = RequestMethod.GET)
    @ResponseBody
    public List queryRoom(@RequestParam Integer taskId) {
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        Map<String,Object> param = new HashMap<>();
        param.put("tnId",tnId);
        param.put("gradeCode",jwScheduleTask.getGrade());
        Grade grade = (Grade) gradeService.queryOne(param);
        Map<Integer,LinkedHashMap<String,Object>> classMap = iexJwScheduleTaskService.getClassMapByTnIdAndTaskId(tnId,Constant.CLASS_ADM_CODE,grade.getGrade());

        List<Map<String,Object> > rtnList = new ArrayList<>();
        Map<String,Object> room;
        List<StringBuffer> buffers = iexJwScheduleTaskService.getClassRoom(taskId,tnId);
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
                Map<String,Object> classObj = classMap.get(roomId);
                room.put("roomId",roomId);
                room.put("roomName","教室"+Math.abs(roomId));
                rtnList.add(room);
            }


        }
        return rtnList;
    }

    /**
     * 获取年级班级类型classType classType=2 不开启调课
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/queryGradeClassType",method = RequestMethod.GET)
    @ResponseBody
    public Integer queryGradeClassType(@RequestParam Integer taskId) {
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        return exiGradeService.getGradeType(tnId,Integer.valueOf(jwScheduleTask.getGrade()));
    }

    /**
     * 获取学生列表(支持模糊查询)
     * @param taskId
     * @param studentName 可选模糊查询参数(如果不为空会模糊匹配学生)
     * @return
     */
    @RequestMapping(value = "/queryStudent",method = RequestMethod.GET)
    @ResponseBody
    public List queryStudent(@RequestParam Integer taskId,@RequestParam Integer classId,@RequestParam Integer classType,String studentName) {
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        //获取一个学生所在的班级列表
        String tableName = ParamsUtils.combinationTableName(Constant.STUDENT, tnId);

        if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(tableName)) {
            return null;
        }
        String className = getClssNameByIdAndType(tnId,taskId,classId,classType);
        List<Map<String,Object>> params = new ArrayList<>();
        Map<String, Object> param;
        if (!StringUtils.isEmpty(studentName)) {
            param = new HashMap<>();
            param.put("key", "student_name");
            param.put("op", "like");
            param.put("value", "%" + studentName + "%");
            params.add(param);

        }
        if (classType == Constant.CLASS_ADM_CODE) {
            param = new HashMap<>();
            param.put("key", "student_class");
            param.put("op", "=");
            param.put("value", className);
            params.add(param);
        }else {
            for (int i = 1 ; i <=3 ; i++) {
                param = new HashMap<>();
                param.put("groupOp", "or");
                param.put("key", "student_check_major_class"+i);
                param.put("op", "=");
                param.put("value",className);
                params.add(param);
            }
        }
        List<LinkedHashMap<String, Object>> tenantCustoms = exiTenantConfigInstanceService.likeTableByParams(tableName,params);
        return studentToRtnDomain(tenantCustoms);
    }

    private List<Map<String,Object>> studentToRtnDomain(List<LinkedHashMap<String, Object>> students){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> domain;
        for (Map<String,Object> map : students){
            domain = new HashMap<>();
            domain.put("studentNo",map.get("student_no"));
            domain.put("studentName",map.get("student_name"));
            list.add(domain);
        }
        return list;
    }

    private String getClssNameByIdAndType(int tnId,int taskId,int classId,int classType){
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        if (classType ==Constant.CLASS_ADM_CODE) {
            String tableName = ParamsUtils.combinationTableName(Constant.CLASS_ADM, tnId);
            List<Map<String,Object>> params = new ArrayList<>();
            Map<String, Object> param = new HashMap<>();
            param.put("key", "id");
            param.put("op", "=");
            param.put("value", classId);
            params.add(param);
            List<Map<String, Object>> admList = exiTenantConfigInstanceService.likeTableByParams(tableName,params);
            return admList.get(0).get("class_name").toString();
        }
        else {
            String tableName = ParamsUtils.combinationTableName(Constant.CLASS_EDU, tnId);
            List<Map<String,Object>> params = new ArrayList<>();
            Map<String, Object> param = new HashMap<>();
            param.put("key", "id");
            param.put("op", "=");
            param.put("value", classId);
            params.add(param);
            List<Map<String, Object>> admList = exiTenantConfigInstanceService.likeTableByParams(tableName,params);
            return admList.get(0).get("class_name").toString();
        }
    }
    /**
     * 获取教室名称
     * @return
     */
    @RequestMapping(value = "/queryTeacher",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getTeacher(@RequestParam Integer taskId,@RequestParam String teacherCourse) {
        if (StringUtils.isEmpty(teacherCourse)){
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }
        JwScheduleTask jwScheduleTask = (JwScheduleTask)jwScheduleTaskService.fetch(taskId);
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        Map<String,Object> param = new HashMap<>();
        param.put("teacher_grade",GradeEnum.getName(Integer.valueOf(jwScheduleTask.getGrade())));
        param.put("teacher_major_type",teacherCourse);
        List<LinkedHashMap<String,Object>> rtnList = teacherService.getScheduleTeacherByTnIdAndTaskId(tnId,taskId,param);
        if (rtnList == null || rtnList.size() == 0) {
            throw new BizException("error","当前科目所查教师为空");
        }
        return teacherBaseDtotoMap(rtnList);
    }
    List<Map<String,Object>> teacherBaseDtotoMap(List<LinkedHashMap<String,Object>> list){
        Map<String,Object> map ;
        List<Map<String,Object> >  maps = new ArrayList<>();
        for (LinkedHashMap<String,Object> teacherBaseDto:list){
            map = Maps.newHashMap();
            map.put("id",teacherBaseDto.get("id"));
            map.put("teacherName",teacherBaseDto.get("teacher_name"));
            maps.add(map);
        }
        return maps;
    }
    /**
     * 获取课程信息
     * @return
     */
    @RequestMapping(value = "/queryCourse",method = RequestMethod.GET)
    @ResponseBody
    public Set<Map<String,Object>> queryCourse(@RequestParam Integer taskId) {
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        List<CourseManageDto> courseManageDtos = courseManageService.getCourseByTnId(tnId);
        Map<String,Object> map;
        Set<Map<String,Object>> maps = new HashSet<>();
        for (CourseManageDto courseManageDto : courseManageDtos) {
            map = new HashMap<>();
            map.put("courseBaseId",courseManageDto.getCourseBaseId());
            map.put("courseBaseName",courseManageDto.getCourseBaseName());
            maps.add(map);
        }

        return maps;
    }

}
