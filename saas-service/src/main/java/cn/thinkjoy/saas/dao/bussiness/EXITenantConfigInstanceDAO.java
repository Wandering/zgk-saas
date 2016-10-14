package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.TenantConfigInstance;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;

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
     * @param tenantConfigInstanceList 需排序的对象集
     * @return
     */
    Integer sortConfigUpdate(List<TenantConfigInstance> tenantConfigInstanceList);

    /**
     * 查询租户配置集
     * @param map
     * @return
     */
    List<TenantConfigInstanceView> selectTeanConfigList(Map map);
}
