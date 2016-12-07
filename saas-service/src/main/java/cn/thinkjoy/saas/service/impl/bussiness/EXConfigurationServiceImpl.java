package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.bussiness.EXIConfigurationDAO;
import cn.thinkjoy.saas.dao.bussiness.EXITenantConfigInstanceDAO;
import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.domain.TenantConfigInstance;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import cn.thinkjoy.saas.service.bussiness.EXIConfigurationService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/12.
 */
@Service("EXConfigurationServiceImpl")
public class EXConfigurationServiceImpl extends AbstractPageService<IBaseDAO<Configuration>, Configuration> implements EXIConfigurationService<IBaseDAO<Configuration>,Configuration> {
    @Autowired
    private EXIConfigurationDAO exiConfigurationDAO;
    @Autowired
    private EXITenantConfigInstanceDAO exiTenantConfigInstanceDAO;
    @Override
    public IBaseDAO<Configuration> getDao() {
        return exiConfigurationDAO;
    }

    /**
     * 获取初始化字段
     * @param type
     * @return
     */
    @Override
    public List<Configuration> selectListBydomain(String type) {
        Map paramsMap = new HashMap();
        paramsMap.put("domain", type);
        paramsMap.put("orderBy", "config_order");
        paramsMap.put("sortBy", "asc");
        return exiConfigurationDAO.selectListBydomain(paramsMap);
    }

    /**
     * 获取初始化字段
     * @param type
     * @return
     */
    @Override
    public boolean selectListRetain(String type,Integer tnid) {

        Map teanMap = new HashMap();
        teanMap.put("domain", type);
        teanMap.put("tnId", tnid);
        teanMap.put("isRetain", 1);
        List<TenantConfigInstanceView> teConfigViews = exiTenantConfigInstanceDAO.selectTeanConfigList(teanMap);

        boolean result = false;

        if (teConfigViews == null || teConfigViews.size() <= 0) {

            Map paramsMap = new HashMap();
            paramsMap.put("domain", type);
            paramsMap.put("orderBy", "config_order");
            paramsMap.put("sortBy", "asc");
            paramsMap.put("isRetain", 1);

            List<Configuration> configurations = exiConfigurationDAO.selectListRetain(paramsMap);

            List<TenantConfigInstance> tenantConfigInstances = new ArrayList<>();

            for (Configuration configuration : configurations) {
                TenantConfigInstance tenantConfigInstance = new TenantConfigInstance();
                tenantConfigInstance.setCheckRule(configuration.getCheckRule());
                tenantConfigInstance.setIsRetain(configuration.getIsRetain());
                tenantConfigInstance.setDataValue(configuration.getDataValue());
                tenantConfigInstance.setConfigOrder(configuration.getConfigOrder());
                tenantConfigInstance.setDomain(configuration.getDomain());
                tenantConfigInstance.setDataUrl(configuration.getDataUrl());
                tenantConfigInstance.setConfigKey(configuration.getId().toString());
                tenantConfigInstance.setDataType(configuration.getDataType());
                tenantConfigInstance.setCreateDate(System.currentTimeMillis());
                tenantConfigInstance.setTnId(tnid);

                tenantConfigInstances.add(tenantConfigInstance);
            }
            Integer r = exiTenantConfigInstanceDAO.addConfigs(tenantConfigInstances);

            if (r > 0 && type.equals("student")) {
                String tableName = ParamsUtils.combinationTableName(type, tnid);
                r = exiTenantConfigInstanceDAO.createConfigTable(tableName, configurations);
            }
            result = r > 0;
        } else
            result = true;
        return result;
    }
}
