package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/12.
 */
public interface EXIConfigurationDAO extends IBaseDAO<Configuration> {


    /**
     * 获取初始化字段
     * @param map
     * @return
     */
    public List<Configuration> selectListBydomain(Map map);

    /**
     * 获取初始化字段
     * @param map
     * @return
     */
    public List<Configuration> selectListRetain(Map map);


    String selectColumnName(Map map);

    /**
     * 根据租户ID查询未添加的表头字段
     *
     * @param map
     * @return
     */
    List<Configuration> selectNotAddHeaderByTnId(Map map);
}
