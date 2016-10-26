package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

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
     * @param tableName
     * @return
     */
    List<LinkedHashMap<String, Object>> getTenantCustom(@Param("tableName") String tableName);
}
