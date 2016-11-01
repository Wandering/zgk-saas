package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.ITenantDAO;
import cn.thinkjoy.saas.domain.Tenant;
import cn.thinkjoy.saas.service.bussiness.IEXTenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by douzy on 16/10/25.
 */
@Service("EXTenantServiceImpl")
public class EXTenantServiceImpl implements IEXTenantService {

    @Resource
    ITenantDAO iTenantDAO;


    /**
     * 租户步骤设置
     * @param tnId
     * @return
     */
    @Override
    public boolean stepSetting(Integer tnId,boolean isLast) {
        Map map = new HashMap();
        map.put("id", tnId);
        Tenant tenant = iTenantDAO.queryOne(map, "id", "asc");
        if (tenant == null)
            return false;
        Integer step = isLast ? 0 : tenant.getIsInit() + 1;
        tenant.setIsInit(step);
        return (iTenantDAO.update(tenant) > 0 ? true : false);
    }

    /**
     * 获取租户当前步骤
     * @param tnId 租户ID
     * @return
     */
    @Override
    public Integer getStep(Integer tnId) {
        Map map = new HashMap();
        map.put("id", tnId);
        Tenant tenant = iTenantDAO.queryOne(map, "id", "asc");
        return (tenant == null ? 1 : tenant.getIsInit());
    }
}
