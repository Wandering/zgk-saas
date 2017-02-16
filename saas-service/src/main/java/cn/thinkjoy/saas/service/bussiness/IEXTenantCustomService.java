package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.bussiness.SyncClass;
import cn.thinkjoy.saas.domain.bussiness.SyncCourse;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.dto.ClassBaseDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/25.
 */
public interface IEXTenantCustomService {



    /**
     * 新增租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @param teantCustoms key&value
     * @return
     */
    boolean addTeantCustom(String type,Integer tnId,List<TeantCustom> teantCustoms);

    /**
     * 更新租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @param pri 租户ID
     * @param teantCustoms key&value
     * @return
     */
    boolean modifyTeantCustom(String type,Integer tnId,Integer pri,List<TeantCustom> teantCustoms);

    /**
     * 删除租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @param ids 租户ID
     * @return
     */
    boolean removeTenantCustom(String type,Integer tnId,String ids);


    /**
     * 查询租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @return
     */
    List<LinkedHashMap<String,Object>> getTenantCustom(String type,Integer tnId,String g,Integer s,Integer r);

    /**
     * 根据类型不同查询学生基础信息
     *
     * @param type 班级类型 0：教学班  1：行政班
     * @param tnId
     * @param g 年级
     * @param s
     * @param r
     * @return
     */
    List<LinkedHashMap<String,Object>> getStuInfo(Integer type,Integer tnId,String g,Integer s,Integer r);

    /**
     * 根据类型不同查询学生总数
     *
     * @param type 班级类型 0：教学班  1：行政班
     * @param tnId
     * @param g
     * @return
     */
    Integer getStuInfoCount(Integer type,Integer tnId,String g);

    /**
     * 根据类型不同查询教师基础信息集合
     *
     * @param tnId
     * @param grade
     * @return
     */
    List<TeacherBaseDto> getTeacherInfos(Integer tnId, String grade);

    /**
     * 根据类型不同查询教师基础信息
     *
     * @param tnId
     * @param id
     * @return
     */
    TeacherBaseDto getTeacherInfo(Integer tnId, Integer id);

    /**
     * 根据类型不同查询班级基础信息集合
     *
     * @param type
     * @param tnId
     * @param course
     * @param grade
     * @return
     */
    List<ClassBaseDto> getClassInfos(String type,Integer tnId, String course,String grade);

    /**
     * 查询租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @return
     */
    List<LinkedHashMap<String,Object>> getTenantCustom(String type,Integer tnId,String selectKey,String selectValue,Integer s,Integer r);

    public Integer getTenantCustomCount(String type,Integer tnId,String g);
    /**
     * excel内添加select
     * @param columnNames
     * @return
     */
    List<Map<Integer,Object>> isExcelAddSelect(String type,Integer tnId ,String[] columnNames);

    /**
     * 判断表中列是否存在数据
     * @return
     */
    Map<String,Object> existDataCount(Map map);

    /**
     * 同步课程
     * @return
     */
    List<SyncCourse> selectCourseGroup(Map map);
    /**
     * 同步班级
     * @return
     */
    List<SyncClass> selectClassGroup(Map map);

    /**
     * 同步走读班基础信息
     * @param map
     * @return
     */
    List<SyncClass> selectExecutiveClassGroup(Map map);

    Map selectExistByCloumn(String tableName,String type,String value1,String value2);
}
