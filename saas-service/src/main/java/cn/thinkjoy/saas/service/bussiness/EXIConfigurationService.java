package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.saas.domain.Configuration;

import java.util.List;

/**
 * Created by douzy on 16/10/12.
 */
public interface EXIConfigurationService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    /**
     * 获取初始化字段
     * @param type
     * @return
     */
    public List<Configuration> selectListBydomain(String type);
}