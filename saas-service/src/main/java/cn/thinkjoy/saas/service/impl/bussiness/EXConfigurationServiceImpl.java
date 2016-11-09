package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.bussiness.EXIConfigurationDAO;
import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.service.bussiness.EXIConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
