package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.bussiness.SyncClass;
import cn.thinkjoy.saas.domain.bussiness.SyncCourse;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.dto.ClassBaseDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/25.
 */
public interface IEXTeantCustomDAO {

    /**
     * 租户自定义表头新增数据
     *
     * @param tableName
     * @param teantCustoms
     * @return
     */
    Integer insertTenantCustom(@Param("tableName") String tableName, @Param("teantCustomList") List<TeantCustom> teantCustoms);


    /**
     * 租户自定义表头更新数据
     *
     * @param tableName    表名
     * @param pri          主键
     * @param teantCustoms 集合
     * @return
     */
    Integer updateTenantCustom(@Param("tableName") String tableName,
                               @Param("pri") Integer pri,
                               @Param("teantCustomList") List<TeantCustom> teantCustoms);

    /**
     * 租户自定义表头更新删除
     *
     * @param tableName 表名
     * @param pri       主键
     * @return
     */
    Integer removeTenantCustom(@Param("tableName") String tableName,
                               @Param("pri") Integer pri);


    /**
     * 获取租户自定义表头数据
     *
     * @param map
     * @return
     */
    List<LinkedHashMap<String, Object>> getTenantCustom(Map map);

    /**
     * 查询学生数据集合
     *
     * @param map
     * @return
     */
    List<LinkedHashMap<String, Object>> getStuInfo(Map map);

    /**
     * 查询教师数据集合
     *
     * @param map
     * @return
     */
    List<TeacherBaseDto> getTeacherInfos(Map map);

    /**
     * 查询行政班级数据集合
     *
     * @param map
     * @return
     */
    List<ClassBaseDto> getClassAdmInfos(Map map);

    /**
     * 查询教学班级数据集合
     *
     * @param map
     * @return
     */
    List<ClassBaseDto> getClassEduInfos(Map map);

    /**
     * 查询学生总数
     *
     * @param map
     * @return
     */
    Integer getStuInfoCount(Map map);

    Integer getTenantCustomCount(Map map);
    Integer getTenantCustomColsCount(Map map);

    /**
     * 批量删除
     * @param tableName
     * @param ids
     * @return
     */
    Integer removeTenantCustomList(@Param("tableName") String tableName,@Param("ids")List<String> ids);

    /**
     * 查询表中所有字段名
     * @param tableName
     * @return
     */
    List<String> getTableColumns(@Param("tableName") String tableName);

    /**
     * 判断表中列是否存在数据
     * @return
     */
    Map<String,Object> existDataCount(Map map);


    /**
     * 课程
     * @param map
     * @return
     */
    List<SyncCourse> selectCourseGroup(Map map);

    /**
     * 同步班级基础信息
     * @param map
     * @return
     */
    List<SyncClass> selectClassGroup(Map map);

    /**
     * 同步行政班基础信息
     * @param map
     * @return
     */
    List<SyncClass> selectExecutiveClassGroup(Map map);


    /**
     *
     * @param map
     * @return
     */
    List<LinkedHashMap<String, Object>> selectExistByCloumn(Map map);

    List<LinkedHashMap<String,Object>> likeTeacherByParams(@Param("list")List<Map<String, Object>> list);
}
