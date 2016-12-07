package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.*;
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
        paramMap.put("tn_id",task.getTnId());
        paramMap.put("grade",task.getGrade());
        List<JwCourseBaseInfo> infos = jwCourseBaseInfoDAO.queryList(paramMap,"id",Constant.DESC);

        return convertInfos2Dtos(infos);
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
    private List<CourseBaseDto> convertInfos2Dtos(List<JwCourseBaseInfo> infos){

        List<CourseBaseDto> dtos = Lists.newArrayList();
        for(JwCourseBaseInfo info : infos){
            CourseBaseDto dto = new CourseBaseDto();
            dto.setCourseId((long)info.getId());
            dto.setCourseName(info.getCourseName());
            dto.setTime("0");
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int getTnIdByTaskId(int taskId) {
        JwScheduleTask task = jwScheduleTaskDAO.findOne("id",taskId,"id", Constant.DESC);
        if(task == null || task.getDelStatus() == StatusEnum.D.getCode()){
            ExceptionUtil.throwException(ErrorCode.TASK_NOT_EXIST);
        }
        return task.getTnId();
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
        int tnId = getTnIdByTaskId(taskId);

        List<JwTeacherBaseInfo> infos = jwTeacherBaseInfoDAO.findList("tn_id",tnId,"id",Constant.DESC);
        for(JwTeacherBaseInfo info : infos){
            TeacherBaseDto dto = new TeacherBaseDto();
            dto.setTeacherId((int)info.getId());
            dto.setTeacherName(info.getTeacherName());
            dto.setCourseName(info.getTeacherCourse());
            dto.setClassInfo(getClassBaseDtosByCourse(info.getTeacherCourse()));
            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 根据课程名获取班级信息
     *
     * @param course
     * @return
     */
    private List<ClassBaseDto> getClassBaseDtosByCourse(String course){

        List<ClassBaseDto> dtos = Lists.newArrayList();

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("class_name",course);
        List<JwClassBaseInfo> infos = jwClassBaseInfoDAO.like(paramMap,"id",Constant.DESC);

        for(JwClassBaseInfo info : infos){
            ClassBaseDto dto = new ClassBaseDto();
            dto.setClassId((int)info.getId());
            dto.setClassName(info.getClassName());
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void saveOrUpdateTeacher(int taskId, int teacherId, int classNum,String course, String classId) {

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("teacher_id",teacherId);
        paramMap.put("task_id",taskId);
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
        }

    }
}
