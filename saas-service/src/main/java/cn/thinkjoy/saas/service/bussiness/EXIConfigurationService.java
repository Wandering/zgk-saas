package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.saas.domain.Configuration;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据租户ID查询未添加的表头字段
     * @param tnId
     * @param type
     * @return
     */
    List<Configuration> selectNotAddHeaderByTnId(Integer tnId,String type);

    /**
     * 初始化必选字段
     * @param type
     * @return
     */
    boolean selectListRetain(String type,Integer tnid);

    /**
     * 中英文列名互转
     * @param map
     * @return
     */
    String selectColumnName(Map map);

}
