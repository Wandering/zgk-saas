package cn.thinkjoy.saas.dao.web;

import java.util.Map;

/**
 * Created by zuohao on 17/2/22.
 */
public interface ISelectCourseDAO {

    public int bindingSchool(Map<String,Object> map);

    public int hasStudent(Map map);
}
