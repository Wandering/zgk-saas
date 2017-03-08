package cn.thinkjoy.saas.dao.web;

import cn.thinkjoy.saas.domain.SelectCourseStuDetail;
import cn.thinkjoy.saas.domain.SelectCourseTask;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据任务ID获取单科选课情况
     *
     * @param taskId
     * @return
     */
    List<CourseBaseDto> getSingleCourseSituation(int taskId);

    /**
     * 根据条件查询选课学生学号集合
     *
     * @param taskId
     * @param type 课程类型  0：高考科目  1：校本课程
     * @param index
     * @param pageSize
     * @return
     */
    List<String> getStuNoListByCondition(
            @Param("taskId") int taskId,
            @Param("type") int type,
            @Param("index") int index,
            @Param("pageSize") int pageSize
    );

    /**
     * 根据条件查询选课学生总数
     *
     * @param taskId
     * @param type 课程类型  0：高考科目  1：校本课程
     * @return
     */
    Integer getStuNoCountByCondition(
            @Param("taskId") int taskId,
            @Param("type") int type
    );

    /**
     * 根据学生学号集合查询学生选课详情
     *
     * @param taskId
     * @param type
     * @param stuNos
     * @return
     */
    List<SelectCourseStuDetail> getSelectDetailByStuNos(
            @Param("taskId") int taskId,
            @Param("type") int type,
            @Param("stuNos") List<String> stuNos
    );

    /**
     * 修改学生选课信息
     *
     * @param tableName    表名
     * @param stuNo          学生学号
     * @param teantCustoms 集合
     * @return
     */
    void updateStuCourseByStuNo(
            @Param("tableName") String tableName,
            @Param("stuNo") String stuNo,
            @Param("teantCustomList") List<TeantCustom> teantCustoms
    );

    /**
     * 组装学生选课组合情况
     *
     * @param hashCode
     * @return
     */
    String getCourseHashByHash(
            @Param("hashCode") String hashCode
    );
}
