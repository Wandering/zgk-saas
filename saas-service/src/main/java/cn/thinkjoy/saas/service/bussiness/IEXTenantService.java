package cn.thinkjoy.saas.service.bussiness;

/**
 * Created by douzy on 16/10/25.
 */
public interface IEXTenantService {
    /**
     * 当前租户步骤设置
     * @return
     */
    boolean stepSetting(Integer tnId,boolean isLast);
}
