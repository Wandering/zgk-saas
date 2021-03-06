package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/12/7.
 *
 * 排课基本信息设置
 */
public interface IEXScheduleBaseInfoService {

    /**
     * 根据任务ID获取课程信息
     *
     * @param taskId
     * @return
     */
    List<CourseBaseDto> queryCourseInfoByTaskId(int taskId);

    /**
     * 根据任务ID获取租户ID
     *
     * @param taskId
     * @return
     */
    int getTnIdByTaskId(int taskId);

//    /**
//     * 根据任务和课程获取老师列表
//     * @param map
//     * @return
//     */
//    public List<TeacherBaseDto> queryTeacherByTaskId(Map<String,Object> map);

    /**
     * 根据任务ID获取教师信息
     *
     * @param taskId
     * @return
     */
    List<TeacherBaseDto> queryTeacherByTaskId(int taskId);

    /**
     * 根据年级和课程名获取班级信息
     *
     * @param tnId
     * @param grade
     * @param course
     * @return
     */
    List<Map<String,Object>> getClassBaseDtosByCourse(int tnId,int grade,String course,String classType);

    /**
     * 根据关键词搜索教师信息
     *
     * @param taskId
     * @param keyword
     * @return
     */
//    List<TeacherBaseDto> queryTeacherByKeyWord(int taskId,String keyword);

    /**
     * 新增或修改教师信息
     *
     * @param taskId
     * @param teacherId
     * @param classNum
     * @param course
     * @param classId
     */
//    void saveOrUpdateTeacher(int taskId, int teacherId,int classNum,String course, String classId);

    /**
     * 同步基本规则数据
     *
     * @param recordId 教师排课记录ID
     * @param isAttend 0：不排课，1：排课
     */
    void insertBaseRule(int recordId,int isAttend);
}
