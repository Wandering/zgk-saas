package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.domain.TenantConfigInstance;
import cn.thinkjoy.saas.domain.bussiness.ClassView;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/13.
 */
public interface EXITenantConfigInstanceService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {

    /**
     * 生成租户自选表头
     *
     * @param type class:班级  teacher:教师
     * @param ids  表头id集
     * @param tnId 租户
     * @return 0:成功
     * -1:失败,系统错误
     * 1:参数错误
     * 2:该租户不存在
     * 3:选项集校验失败
     */
    public String createConfig(String type, String ids, Integer tnId);

    /**
     * 新增表头
     * @param type
     * @param ids
     * @param tnId
     * @return
     */
    public String createColumn(String type,String ids,Integer tnId);

    /**
     * 年级联动获取
     * @param map
     * @return
     */
    List<ClassView> selectClassTypeByGrade(String type,Integer tnId,String grade);

    /**
     * 班级名称
     * @param type
     * @param tnId
     * @param grade
     * @param classType
     * @return
     */
    public List<ClassView> selectClassNameByGradeAndType(String type,Integer tnId,String grade,String classType);
    /**
     * 删除表头
     * @param type
     * @param ids
     * @param tnId
     * @return
     */
    public String removeColumn(String type,String ids,Integer tnId);
    /**
     * 逐条删除租户表头  by
     *
     * @param id 表头ID
     * @return
     */
    public boolean removeConfigDataById(Integer id);

    /**
     * 批量删除租户表头
     *
     * @param ids
     */
    boolean removeTeantConfigs(String type,Integer tnId, String ids);

    /**
     * 租户表头排序
     *
     * @param type class:班级 teacher:教师
     * @param ids  需要调换的两个表头ID
     * @return
     */
    boolean sortTeantConfig(String type, String ids);


    /**
     * 查询当前租户&当前模块下的表头信息
     *
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    List<TenantConfigInstanceView> getTenantConfigListByTnIdAndType(String type, Integer tnId);
    List<TenantConfigInstanceView> getTenantConfigListByTnIdAndType(String type, Integer tnId,String isShow);

    /**
     * 获取当前租户表头 数组 - 用于导出表头excel
     *
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    String[] getTenantConfigListArrByTnIdAndType(String type, Integer tnId);


    /**
     * 租户动态表名校验  -表级别校验
     *
     * @param tableName 表名
     * @return
     */
    boolean tableNameValid(String tableName);


    /**
     * 创建租户动态表
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    String createTenantCombinationTable(String type,Integer tnId);

    /**
     *解析excel
     * @param type
     * @param tnId
     * @param excelPath
     * @param classType 0：教学班  1：行政班
     * @return
     */
    String uploadExcel(String type,Integer tnId,String excelPath,int classType);


    /**
     * 用户动态表是否已经创建
     * @param type class:班级  teacher:教师
     * @param tnId  租户ID
     * @return
     */
    boolean isExsitsTeantCustomTable(String type,Integer tnId);

    public Configuration queryConfigurationOne(Map map);

//    public void syncProcedureData(String type,Integer tnId);

    /**
     * 是否存在当前列
     * @param map
     * @return
     */
    public boolean existColumn(Map map);

    /**
     * 新增表头
     * @param tenantConfigInstances
     * @return
     */
    public Integer addConfigs(List<TenantConfigInstance> tenantConfigInstances);

    /**
     * 新增列
     * @param map
     */
    public void addColumn(Map map);

    /**
     * 删除租户自定义表头
     * @param map
     * @return
     */
    public Integer teantConfigDeleteByCondition(Map map);

    /**
     * 删除列
     * @param map
     */
    public void removeColumn(Map map);
    /**
     * 查找学号是否重复
     * @param map
     * @return
     */
    Integer selectCountByStudentNo(String type,Integer tnId,String studentNo);

    Integer removeTenantCustomList(String tableName,List<String> removeIds);

    int countBySubjectAndGrade(int tnId, String grade, String subject, String classEdu);


    List getBySubjectAndGrade(int tnId, String grade, String subject, String classEdu);
}
