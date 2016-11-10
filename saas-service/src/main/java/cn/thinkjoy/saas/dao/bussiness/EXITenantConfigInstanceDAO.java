package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.domain.TenantConfigInstance;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/13.
 */
public interface EXITenantConfigInstanceDAO extends IBaseDAO<TenantConfigInstance> {
    /**
     * 批量导入租户已选表头
     *
     * @param tenantConfigInstanceList 数据集
     * @return
     */
    Integer addConfigs(List<TenantConfigInstance> tenantConfigInstanceList);

    /**
     * 批量删除
     *
     * @param ids
     */
    Integer removeTeantConfigs(List<String> ids);


    /**
     * 租户自选表头排序
     *
     * @param tenantConfigInstanceList 需排序的对象集
     * @return
     */
    Integer sortConfigUpdate(List<TenantConfigInstance> tenantConfigInstanceList);

    /**
     * 查询租户配置集
     *
     * @param map
     * @return
     */
    List<TenantConfigInstanceView> selectTeanConfigList(Map map);

    /**
     * 删除租户动态表
     *
     * @param tableName 表名
     * @return
     */
    int dropTable(@Param("tableName") String tableName);

    /**
     * 查询租户是否已创建动态表
     *
     * @param tableName 表名
     * @return
     */
    int existTable(String tableName);

    /**
     * 查询列是否存在
     * @param map
     * @return
     */
    int existColumn(Map map);

    /**
     * 新增列
     * @param map
     */
    void addColumn(Map map);

    /**
     * 删除列
     * @param map
     */
    void removeColumn(Map map);

    /**
     * 动态创建表
     *
     * @param configurations 表字段集
     * @return
     */
    Integer createConfigTable(@Param("tableName") String tableName, @Param("configList") List<Configuration> configurations);

    /**
     * 解析excel 将数据插入租户动态表
     * @param tableName  租户动态表名
     * @param configurations 租户自定义表头集
     * @param configValueList 租户excel上传数据集
     * @return
     */
    Integer insertTenantConfigCom(@Param("tableName") String tableName, @Param("configKeyList") List<TenantConfigInstanceView> configurations, @Param("configValueList") List<LinkedHashMap<String, String>> configValueList);

}
