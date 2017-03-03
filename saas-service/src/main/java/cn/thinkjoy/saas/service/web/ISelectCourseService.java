package cn.thinkjoy.saas.service.web;

import cn.thinkjoy.gk.pojo.Page;
import cn.thinkjoy.saas.domain.SelectCourseSetting;
import cn.thinkjoy.saas.dto.BaseDto;
import cn.thinkjoy.saas.dto.BaseStuDto;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.SelectCourseSurveyDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 17/2/22.
 */
public interface ISelectCourseService {
    public Map bindingSchool(String schoolId,String studentNo,String studentName,String userId);

    public Map<String,Object> getSelectCourseInfo(String schoolId,String studentNo);

    public Map<String,Object> getSaasStudentInfo(String schoolId,String studentNo);

    public Map<String,Object> addSelectCourse(String schoolId,String studentNo,String majors,String schoolCourse);

    /**
     * 根据任务Id拉取学校所有高考课程和校本课程
     *
     * @param taskId
     * @return
     */
    List<SelectCourseSetting> getSelectCourses(int taskId);

    /**
     * 获取选课概况
     *
     * @param taskId
     * @return
     */
    SelectCourseSurveyDto SelectCourseSurvey(int taskId);

    /**
     * 查询单科选课结果
     *
     * @param taskId
     * @return
     */
    List<CourseBaseDto> getSingleCourseSituation(int taskId);

    /**
     * 组合选课结果
     *
     * @param taskId
     * @return
     */
    List<CourseBaseDto> getGroupCourseSituation(int taskId);

    /**
     * 查询学生高考课程选课详情（带分页）
     *
     * @param taskId
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BaseStuDto> getStuCourseDetail(
            int taskId,
            int type,
            int pageNo,
            int pageSize
    );

    /**
     * 修改学生选课信息
     *
     * @param stuNo
     * @param taskId
     * @param type
     * @param courseIds
     */
    void updateStuCourse(
            String stuNo,
            int taskId,
            int type,
            List<String> courseIds
    );

    /**
     * 确认使用选课数据
     *
     * @param taskId
     */
    void confirmSelectCourse(
            int taskId
    );

    /**
     * 根据任务ID和课程类型查询课程信息
     *
     * @param taskId
     * @param type
     * @return
     */
    List<BaseDto> getCourseBaseInfo(
            int taskId,
            int type
    );
}
