package cn.thinkjoy.saas.dao.web;

import cn.thinkjoy.saas.domain.SelectCourseTask;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 17/2/22.
 */
public interface ISelectCourseDAO {

    public int bindingSchool(Map<String,Object> map);

    public int hasStudent(Map map);

    public Map getStudentInfo(Map map);

    public List<SelectCourseTask> getSelectCourseInfo(Map map);

    public int insertList(Map map);
}
