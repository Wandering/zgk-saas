package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.*;
import cn.thinkjoy.saas.dao.bussiness.IEXClassBaseInfoDAO;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.dto.ClassBaseDto;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.StatusEnum;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
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
    private IJwCourseBaseInfoDAO jwCourseBaseInfoDAO;

    @Autowired
    private IJwScheduleTaskDAO jwScheduleTaskDAO;

    @Autowired
    private IJwTeacherDAO jwTeacherDAO;

    @Autowired
    private IJwTeacherBaseInfoDAO jwTeacherBaseInfoDAO;

    @Autowired
    private IJwClassBaseInfoDAO jwClassBaseInfoDAO;

    @Autowired
    private IEXClassBaseInfoDAO iexClassBaseInfoDAO;

    @Override
    public List<CourseBaseDto> queryCourseInfoByTaskId(int taskId) {

        // 先从任务基本信息中获取课程
        List<JwCourse> jwCourses = jwCourseDAO.findList("task_id",taskId,"id", Constant.DESC);
        if(jwCourses.size() != 0){
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
        List<JwCourseBaseInfo> infos = jwCourseBaseInfoDAO.queryList(paramMap,"id",Constant.DESC);

        return convertInfos2Dtos(infos,taskId);
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
            JwCourseBaseInfo info = jwCourseBaseInfoDAO.fetch(course.getCourseId());
            dto.setCourseName(info.getCourseName());
            dto.setTime(course.getCourseHour());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * List<JwCourseBaseInfo>  -->  List<CourseBaseDto>
     *
     * @param infos
     * @return
     */
    private List<CourseBaseDto> convertInfos2Dtos(List<JwCourseBaseInfo> infos,int taskId){

        List<CourseBaseDto> dtos = Lists.newArrayList();
        for(JwCourseBaseInfo info : infos){
            CourseBaseDto dto = new CourseBaseDto();
            dto.setCourseId(Integer.valueOf(info.getId().toString()));
            dto.setCourseName(info.getCourseName());
            dto.setTime("0");
            dtos.add(dto);

            insertJwCourse(info,taskId);
        }
        return dtos;
    }

    /**
     * 插入课程课时信息
     *
     * @param info
     * @param taskId
     */
    private void insertJwCourse(JwCourseBaseInfo info,int taskId){
        JwCourse course = new JwCourse();
        course.setTaskId(taskId);
        course.setTnId(info.getTnId());
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

    @Override
    public List<TeacherBaseDto> queryTeacherByTaskId(int taskId) {

        List<JwTeacher> teachers = jwTeacherDAO.findList("task_id",taskId,"id",Constant.DESC);

        return convertTeachers2Dtos(teachers);
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
            TeacherBaseDto dto = new TeacherBaseDto();
            dto.setCourseName(teacher.getCourse());
            dto.setClassNum(teacher.getTeachNum());
            dto.setTeacherId(teacher.getTeacherId());
            JwTeacherBaseInfo info = jwTeacherBaseInfoDAO.fetch(teacher.getTeacherId());
            dto.setTeacherName(info.getTeacherName());
            dto.setClassInfo(getClassBaseDtosByIds(teacher.getClassId()));
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * 根据班级ID获取班级基本信息
     *
     * @param classIds
     * @return
     */
    private List<ClassBaseDto> getClassBaseDtosByIds(String classIds){

        List<ClassBaseDto> dtos = Lists.newArrayList();

        if(StringUtils.isBlank(classIds)){
            return dtos;
        }

        String [] arr = classIds.split(",");
        for(int i=0;i<arr.length;i++){
            ClassBaseDto dto = new ClassBaseDto();
            int classId = Integer.valueOf(arr[i]);
            dto.setClassId(classId);
            JwClassBaseInfo info = jwClassBaseInfoDAO.fetch(classId);
            dto.setClassName(info.getClassName());
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<TeacherBaseDto> queryTeacherByKeyWord(int taskId, String keyword) {

        List<TeacherBaseDto> dtos = Lists.newArrayList();

        // 根据租户ID和关键词搜索教师信息
        JwScheduleTask task = getTaskByTaskId(taskId);

        List<JwTeacherBaseInfo> infos = jwTeacherBaseInfoDAO.findList("tn_id",task.getTnId(),"id",Constant.DESC);
        for(JwTeacherBaseInfo info : infos){
            if(info.getTeacherName().indexOf(keyword) == -1){
                continue;
            }
            TeacherBaseDto dto = new TeacherBaseDto();
            dto.setTeacherId(Integer.valueOf(info.getId().toString()));
            dto.setTeacherName(info.getTeacherName());
            dto.setCourseName(info.getTeacherCourse());
            dto.setClassInfo(
                    getClassBaseDtosByCourse(
                    task.getTnId(),
                    Integer.valueOf(task.getGrade()),
                    info.getTeacherCourse())
            );
            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 根据年级和课程名获取班级信息
     *
     * @param tnId
     * @param grade
     * @param course
     * @return
     */
    public List<ClassBaseDto> getClassBaseDtosByCourse(int tnId,int grade,String course){

        List<ClassBaseDto> dtos = Lists.newArrayList();

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("className",course);
        paramMap.put("classType",2);
        paramMap.put("grade",grade);
        paramMap.put("tnId",tnId);
        List<JwClassBaseInfo> infos = iexClassBaseInfoDAO.queryClassList(paramMap);

        // 不存在则查询行政班级
        if(infos.size() == 0){
            paramMap.clear();
            paramMap.put("grade",grade);
            paramMap.put("classType",1);
            paramMap.put("tnId",tnId);
            infos = iexClassBaseInfoDAO.queryClassList(paramMap);
        }

        for(JwClassBaseInfo info : infos){
            ClassBaseDto dto = new ClassBaseDto();
            dto.setClassId(Integer.valueOf(info.getId().toString()));
            dto.setClassName(info.getClassName());
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void saveOrUpdateTeacher(int taskId, int teacherId, int classNum,String course, String classId) {

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("teacherId",teacherId);
        paramMap.put("taskId",taskId);
        JwTeacher jwTeacher = jwTeacherDAO.queryOne(paramMap,"id",Constant.DESC);
        if(jwTeacher != null){
            jwTeacher.setTeachNum(classNum);
            jwTeacher.setClassId(classId);
            jwTeacherDAO.update(jwTeacher);
        }else {
            jwTeacher = new JwTeacher();
            jwTeacher.setTaskId(taskId);
            jwTeacher.setTnId(getTnIdByTaskId(taskId));
            jwTeacher.setClassId(classId);
            jwTeacher.setCourse(course);
            jwTeacher.setTeacherId(teacherId);
            jwTeacher.setTeachNum(classNum);
            jwTeacherDAO.insert(jwTeacher);

            // 异步添加教师基本规则
            insertBaseRule(taskId,teacherId,jwTeacher.getTnId(),course);
        }

    }

    @Async
    private void insertBaseRule(int taskId, int teacherId,int tnId,String course){

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("tnId",tnId);
        paramMap.put("courseName",course);
        JwCourseBaseInfo info = jwCourseBaseInfoDAO.queryOne(paramMap,"id",Constant.DESC);
        if(info == null){
            return;
        }
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

        // 日任课规则
        JwBaseDayRule dayRule = new JwBaseDayRule();
        dayRule.setCreateDate(currentTime);
        dayRule.setTaskId(taskId);
        dayRule.setImportantType(2);
        dayRule.setCourseId(courseId);
        dayRule.setDayType(1);
        dayRule.setTeacherId(teacherId);
        dayRule.setTnId(tnId);

        // 周任课规则
        JwBaseWeekRule weekRule = new JwBaseWeekRule();
        weekRule.setCreateDate(currentTime);
        weekRule.setTnId(tnId);
        weekRule.setTeacherId(teacherId);
        weekRule.setCourseId(courseId);
        weekRule.setImportantType(2);
        weekRule.setTaskId(taskId);
        weekRule.setWeekType(1);

        // 教案齐平规则
        JwBaseJaqpRule jaqpRule = new JwBaseJaqpRule();
        jaqpRule.setCreateDate(currentTime);
        jaqpRule.setTaskId(taskId);
        jaqpRule.setImportantType(2);
        jaqpRule.setCourseId(courseId);
        jaqpRule.setTeacherId(teacherId);
        jaqpRule.setTnId(tnId);
    }
}
