package cn.thinkjoy.saas.service.web;

import cn.thinkjoy.saas.domain.SelectCourseSetting;
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
}
