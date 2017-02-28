package cn.thinkjoy.saas.service.web;

import java.util.Map;

/**
 * Created by zuohao on 17/2/22.
 */
public interface ISelectCourseService {
    public Map bindingSchool(String schoolId,String studentNo,String studentName,String userId);

    public Map<String,Object> getSelectCourseInfo(String schoolId,String studentNo);

    public Map<String,Object> getSaasStudentInfo(String schoolId,String studentNo);
}
