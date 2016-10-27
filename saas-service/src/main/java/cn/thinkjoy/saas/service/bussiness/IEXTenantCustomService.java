package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.bussiness.TeantCustom;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/25.
 */
public interface IEXTenantCustomService {



    /**
     * 新增租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @param teantCustoms key&value
     * @return
     */
    boolean addTeantCustom(String type,Integer tnId,List<TeantCustom> teantCustoms);

    /**
     * 更新租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @param pri 租户ID
     * @param teantCustoms key&value
     * @return
     */
    boolean modifyTeantCustom(String type,Integer tnId,Integer pri,List<TeantCustom> teantCustoms);

    /**
     * 删除租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @param pri 租户ID
     * @return
     */
    boolean removeTenantCustom(String type,Integer tnId,Integer pri);


    /**
     * 查询租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @return
     */
    List<LinkedHashMap<String,Object>> getTenantCustom(String type,Integer tnId);


    /**
     * excel内添加select
     * @param columnNames
     * @return
     */
    List<Map<Integer,Object>> isExcelAddSelect(Integer tnId ,String[] columnNames);
}
