package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.TenantConfigInstance;

import java.util.List;

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
}
