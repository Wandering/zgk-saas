package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.*;
import cn.thinkjoy.saas.dao.bussiness.ICourseBaseInfoDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXClassBaseInfoDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXCourseManageDAO;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.dto.ClassBaseDto;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeEnum;
import cn.thinkjoy.saas.enums.StatusEnum;
import cn.thinkjoy.saas.service.bussiness.IEXCourseBaseInfoService;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
import cn.thinkjoy.saas.service.bussiness.IEXTenantCustomService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/12/7.
 */
@Service("eXScheduleBaseInfoServiceImpl")
public class EXScheduleBaseInfoServiceImpl implements IEXScheduleBaseInfoService {

    @Autowired
    private IJwCourseDAO jwCourseDAO;

    @Autowired
    private IEXCourseManageDAO iexCourseManageDAO;

    @Autowired
    private ICourseBaseInfoDAO iCourseBaseInfoDAO;

    @Autowired
    private IJwScheduleTaskDAO jwScheduleTaskDAO;

    @Autowired
    private IEXTenantCustomService iexTenantCustomService;

    @Autowired
    private IJwTeacherDAO jwTeacherDAO;

    @Autowired
    private IJwCourseGapRuleDAO jwCourseGapRuleDAO;

    @Autowired
    private IJwBaseConRuleDAO iJwBaseConRuleDAO;

    @Autowired
    private IJwBaseDayRuleDAO iJwBaseDayRuleDAO;

    @Autowired
    private IJwBaseJaqpRuleDAO iJwBaseJaqpRuleDAO;

    @Autowired
    private IJwBaseWeekRuleDAO iJwBaseWeekRuleDAO;

    @Override
    public List<CourseBaseDto> queryCourseInfoByTaskId(int taskId) {

        // 先从任务基本信息中获取课程
        List<JwCourse> jwCourses = jwCourseDAO.findList("task_id",taskId,"id", Constant.DESC);
        if(jwCourses.size() > 0){
            return convertCourses2Dtos(jwCourses);
        }

        // 根据租户ID和年级获取课程
        JwScheduleTask task = jwScheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        if(task == null || task.getDelStatus() == StatusEnum.D.getCode()){
            ExceptionUtil.throwException(ErrorCode.TASK_NOT_EXIST);
        }

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("tnId",task.getTnId());
        paramMap.put("grade",task.getGrade());
        List<CourseBaseInfo> infos = iexCourseManageDAO.queryListByCondition(paramMap);

        return convertInfos2Dtos(infos,taskId,task.getTnId());
    }

    /**
     * List<JwCourse>  -->  List<CourseBaseDto>
     *
     * @param jwCourses
     * @return
     */
    private List<CourseBaseDto> convertCourses2Dtos(List<JwCourse> jwCourses){

        List<CourseBaseDto> dtos = Lists.newArrayList();
        for(JwCourse course : jwCourses){
            CourseBaseDto dto = new CourseBaseDto();
            dto.setCourseId(course.getCourseId());
            CourseBaseInfo info = iCourseBaseInfoDAO.fetch(course.getCourseId());
            dto.setCourseName(info.getCourseName());
            dto.setTime(course.getCourseHour());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * List<CourseBaseInfo>  -->  List<CourseBaseDto>
     *
     * @param infos
     * @return
     */
    private List<CourseBaseDto> convertInfos2Dtos(List<CourseBaseInfo> infos,int taskId,int tnId){

        List<CourseBaseDto> dtos = Lists.newArrayList();
        for(CourseBaseInfo info : infos){
            CourseBaseDto dto = new CourseBaseDto();
            dto.setCourseId(Integer.valueOf(info.getId().toString()));
            dto.setCourseName(info.getCourseName());
            dto.setTime("0");
            dtos.add(dto);

            insertJwCourse(info,taskId,tnId);
        }
        return dtos;
    }

    /**
     * 插入课程课时信息
     *
     * @param info
     * @param taskId
     */
    private void insertJwCourse(CourseBaseInfo info,int taskId,int tnId){
        JwCourse course = new JwCourse();
        course.setTaskId(taskId);
        course.setTnId(tnId);
        course.setCourseId(Integer.valueOf(info.getId().toString()));
        jwCourseDAO.insert(course);
    }

    @Override
    public int getTnIdByTaskId(int taskId) {
        JwScheduleTask task = jwScheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        if(task == null || task.getDelStatus() == StatusEnum.D.getCode()){
            ExceptionUtil.throwException(ErrorCode.TASK_NOT_EXIST);
        }
        return task.getTnId();
    }

    private JwScheduleTask getTaskByTaskId(int taskId) {
        JwScheduleTask task = jwScheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        if(task == null || task.getDelStatus() == StatusEnum.D.getCode()){
            ExceptionUtil.throwException(ErrorCode.TASK_NOT_EXIST);
        }
        return task;
    }


//    @Override
//    public List<TeacherBaseDto> queryTeacherByTaskId(Map<String,Object> map) {
//
//        List<JwTeacher> teachers = jwTeacherDAO.queryList(map,"id","asc");
//
//        return convertTeachers2Dtos(teachers,map.get("taskId").toString());
//    }

    @Override
    public List<TeacherBaseDto> queryTeacherByTaskId(int taskId) {

        List<JwTeacher> teachers = jwTeacherDAO.findList("task_id",taskId,"id",Constant.DESC);

        if(teachers == null || teachers.size() == 0){
            JwScheduleTask task = getTaskByTaskId(taskId);
            int grade = Integer.valueOf(task.getGrade());
            List<TeacherBaseDto> dtos = iexTenantCustomService.getTeacherInfos(task.getTnId(),Constant.GRADES[grade-1]);
            return insertJwTeacher(dtos,taskId,task.getTnId());
        }

        return convertTeachers2Dtos(teachers);
    }

    /**
     * 插入教师规则信息
     *
     * @param dtos
     * @param taskId
     * @param tnId
     */
    private List<TeacherBaseDto> insertJwTeacher(List<TeacherBaseDto> dtos,int taskId,int tnId){
        for(TeacherBaseDto dto : dtos){
            JwTeacher teacher = new JwTeacher();
            teacher.setIsAttend(1);
            teacher.setTaskId(taskId);
            teacher.setTeacherId(dto.getTeacherId());
            teacher.setTnId(tnId);
            int id = jwTeacherDAO.insert(teacher);
            dto.setId(id);

            // 异步插入教师规则信息
            insert(dto.getTeacherId(),tnId,taskId,dto.getCourseName());
        }
        return dtos;
    }


    /**
     * List<JwTeacher>  -->  List<TeacherBaseDto>
     *
     * @param teachers
     * @return
     */
    private List<TeacherBaseDto> convertTeachers2Dtos(List<JwTeacher> teachers){
        List<TeacherBaseDto> dtos = Lists.newArrayList();
        for(JwTeacher teacher : teachers){
            TeacherBaseDto dto = iexTenantCustomService.getTeacherInfo(teacher.getTnId(),teacher.getTeacherId());
            dto.setId(Integer.valueOf(teacher.getId().toString()));
            dto.setIsAttend(teacher.getIsAttend());
            dtos.add(dto);
        }
        return dtos;
    }


//    @Override
//    public List<TeacherBaseDto> queryTeacherByKeyWord(int taskId, String keyword) {
//
//        List<TeacherBaseDto> dtos = Lists.newArrayList();
//
//        // 根据租户ID和关键词搜索教师信息
//        JwScheduleTask task = getTaskByTaskId(taskId);
//
//        List<JwTeacherBaseInfo> infos = jwTeacherBaseInfoDAO.findList("tn_id",task.getTnId(),"id",Constant.DESC);
//        for(JwTeacherBaseInfo info : infos){
//            if(info.getTeacherName().indexOf(keyword) == -1){
//                continue;
//            }
//            TeacherBaseDto dto = new TeacherBaseDto();
//            dto.setTeacherId(Integer.valueOf(info.getId().toString()));
//            dto.setTeacherName(info.getTeacherName());
//            dto.setCourseName(info.getTeacherCourse());
//            dto.setClassInfo(
//                    getClassBaseDtosByCourse(
//                    task.getTnId(),
//                    Integer.valueOf(task.getGrade()),
//                    info.getTeacherCourse())
//            );
//            dtos.add(dto);
//        }
//
//        return dtos;
//    }

    @Override
    public List<Map<String,Object>> getClassBaseDtosByCourse(int tnId,int grade,String course){

        List<Map<String,Object>> returnMaps = Lists.newArrayList();

        Map<String,String> paramMap = Maps.newHashMap();
        paramMap.put("tableName", ParamsUtils.combinationTableName(Constant.CLASS_EDU,tnId));
        paramMap.put("searchKey","class_grade");
        paramMap.put("searchValue",Constant.GRADES[Integer.valueOf(grade-1)]);
        paramMap.put("classType","0");

        List<Map<String,Object>> maps = jwCourseGapRuleDAO.queryClassList(paramMap);

        // 根据课程名称和年纪查询班级信息
        for(Map map : maps){
            if(map.get("course").toString().indexOf(course) != -1){
                returnMaps.add(map);
            }
        }

        // 不存在则查询行政班级
        if(returnMaps.size() == 0){
            paramMap.put("classType","1");
            paramMap.put("tableName", ParamsUtils.combinationTableName(Constant.CLASS_ADM,tnId));
            returnMaps = jwCourseGapRuleDAO.queryClassList(paramMap);
        }

        return returnMaps;
    }

//    @Override
//    public void saveOrUpdateTeacher(int taskId, int teacherId, int classNum,String course, String classId) {
//
//        Map<String,Object> paramMap = Maps.newHashMap();
//        paramMap.put("teacherId",teacherId);
//        paramMap.put("taskId",taskId);
//        JwTeacher jwTeacher = jwTeacherDAO.queryOne(paramMap,"id",Constant.DESC);
//        if(jwTeacher != null){
//            jwTeacher.setTeachNum(classNum);
//            jwTeacher.setClassId(classId);
//            jwTeacherDAO.update(jwTeacher);
//        }else {
//            jwTeacher = new JwTeacher();
//            jwTeacher.setTaskId(taskId);
//            jwTeacher.setTnId(getTnIdByTaskId(taskId));
//            jwTeacher.setClassId(classId);
//            jwTeacher.setCourse(course);
//            jwTeacher.setTeacherId(teacherId);
//            jwTeacher.setTeachNum(classNum);
//            jwTeacherDAO.insert(jwTeacher);
//
//            // 异步添加教师基本规则
//            insertBaseRule(taskId,teacherId,jwTeacher.getTnId(),course);
//        }
//
//    }

    @Override
    public void insertBaseRule(int recordId,int isAttend){

        JwTeacher jwTeacher = jwTeacherDAO.fetch(recordId);
        int teacherId = jwTeacher.getTeacherId();
        int tnId = jwTeacher.getTnId();
        int taskId = jwTeacher.getTaskId();

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("teacherId",teacherId);
        paramMap.put("taskId",taskId);
        JwBaseConRule rule = iJwBaseConRuleDAO.queryOne(paramMap,null,null);
        // 教师不排课，且曾经设置过规则，则直接删除
        if(rule != null && isAttend == 0){
            iJwBaseConRuleDAO.deleteByCondition(paramMap);
            iJwBaseDayRuleDAO.deleteByCondition(paramMap);
            iJwBaseWeekRuleDAO.deleteByCondition(paramMap);
            iJwBaseJaqpRuleDAO.deleteByCondition(paramMap);
            return;
        }

        // 教师排课，且曾经没有设置过，直接添加
        if(rule == null && isAttend == 1){
            // 根据教师ID查询教师信息
            TeacherBaseDto dto = iexTenantCustomService.getTeacherInfo(jwTeacher.getTnId(),teacherId);
            String course = dto.getCourseName();
            if(StringUtils.isEmpty(course)){
                return;
            }

            insert(teacherId,tnId,taskId,course);
        }
    }

    @Async
    private void insert(int teacherId,int tnId,int taskId,String course){
        // 根据课程名查询课程Id
        CourseBaseInfo info = iCourseBaseInfoDAO.findOne("course_name",course,null,null);

        int courseId = Integer.valueOf(info.getId().toString());
        long currentTime = System.currentTimeMillis();

        // 连上规则
        JwBaseConRule conRule = new JwBaseConRule();
        conRule.setTeacherId(teacherId);
        conRule.setTnId(tnId);
        conRule.setCourseId(courseId);
        conRule.setCreateDate(currentTime);
        conRule.setDayConType(1);
        conRule.setImportantType(2);
        conRule.setTaskId(taskId);
        iJwBaseConRuleDAO.insert(conRule);

        // 日任课规则
        JwBaseDayRule dayRule = new JwBaseDayRule();
        dayRule.setCreateDate(currentTime);
        dayRule.setTaskId(taskId);
        dayRule.setImportantType(2);
        dayRule.setCourseId(courseId);
        dayRule.setDayType(1);
        dayRule.setTeacherId(teacherId);
        dayRule.setTnId(tnId);
        iJwBaseDayRuleDAO.insert(dayRule);

        // 周任课规则
        JwBaseWeekRule weekRule = new JwBaseWeekRule();
        weekRule.setCreateDate(currentTime);
        weekRule.setTnId(tnId);
        weekRule.setTeacherId(teacherId);
        weekRule.setCourseId(courseId);
        weekRule.setImportantType(2);
        weekRule.setTaskId(taskId);
        weekRule.setWeekType(1);
        iJwBaseWeekRuleDAO.insert(weekRule);

        // 教案齐平规则
        JwBaseJaqpRule jaqpRule = new JwBaseJaqpRule();
        jaqpRule.setCreateDate(currentTime);
        jaqpRule.setTaskId(taskId);
        jaqpRule.setImportantType(2);
        jaqpRule.setCourseId(courseId);
        jaqpRule.setTeacherId(teacherId);
        jaqpRule.setTnId(tnId);
        iJwBaseJaqpRuleDAO.insert(jaqpRule);
    }
}
