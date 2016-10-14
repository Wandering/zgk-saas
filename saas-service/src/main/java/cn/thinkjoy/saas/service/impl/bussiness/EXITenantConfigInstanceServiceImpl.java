package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.IConfigurationDAO;
import cn.thinkjoy.saas.dao.ITenantConfigInstanceDAO;
import cn.thinkjoy.saas.dao.ITenantDAO;
import cn.thinkjoy.saas.dao.bussiness.EXITenantConfigInstanceDAO;
import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.domain.Tenant;
import cn.thinkjoy.saas.domain.TenantConfigInstance;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.common.EnumUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by douzy on 16/10/13.
 */
@Service("EXITenantConfigInstanceServiceImpl")
public class EXITenantConfigInstanceServiceImpl extends AbstractPageService<IBaseDAO<TenantConfigInstance>, TenantConfigInstance> implements EXITenantConfigInstanceService<IBaseDAO<TenantConfigInstance>,TenantConfigInstance> {

    private static final Logger LOGGER= LoggerFactory.getLogger(EXITenantConfigInstanceServiceImpl.class);

    @Autowired
    EXITenantConfigInstanceDAO exiTenantConfigInstanceDAO;
    @Resource
    ITenantConfigInstanceDAO iTenantConfigInstanceDAO;
    @Resource
    IConfigurationDAO iConfigurationDAO;
    @Resource
    ITenantDAO iTenantDAO;

    @Override
    public IBaseDAO<TenantConfigInstance> getDao() {
        return exiTenantConfigInstanceDAO;
    }

    /**
     * 租户是否已经选择表头
     * @param tnId
     * @return
     */
    public boolean isExistConfigDataByTnId(String type,Integer tnId) {
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("domain",type);
        TenantConfigInstance tenantConfigInstance = iTenantConfigInstanceDAO.queryOne(map, "id", "asc");
        return tenantConfigInstance == null ? false : true;
    }

    /**
     * 批量删除租户已选表头  by 租户ID
     * @param tnId
     * @return
     */
    public boolean removeConfigDataByTnId(String type,Integer tnId) {
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("domain",type);
        Integer result = iTenantConfigInstanceDAO.deleteByCondition(map);
        return result > 0 ? true : false;
    }

    /**
     * 逐条删除租户表头  by
     * @param id  表头ID
     * @return
     */
    @Override
    public boolean removeConfigDataById(Integer id) {
        Map map = new HashMap();
        map.put("id", id);
        Integer result = iTenantConfigInstanceDAO.deleteByCondition(map);
        return result > 0 ? true : false;
    }

    /**
     * 批量删除租户表头
     *
     * @param ids 需要删除的表头ID串  -号分隔
     */
    @Override
    public boolean removeTeantConfigs(String ids) {
        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;
        return exiTenantConfigInstanceDAO.removeTeantConfigs(idsList) > 0 ? true : false;
    }

    /**
     * 租户表头排序
     * @param type class:班级 teacher:教师
     * @param ids 需要调换的两个表头ID
     * @return
     */
    @Override
    public boolean sortTeantConfig(String type,String ids) {
        LOGGER.info("===============租户表头排序 S==============");
        LOGGER.info("type:" + type);
        LOGGER.info("ids:" + ids);

        boolean result = false;

        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;

        //调换表头顺序 必须保证ids size 为2
        if (idsList.size() == 2) {
            List<TenantConfigInstance> tenantConfigInstances = new ArrayList<TenantConfigInstance>();

            for (int i = 0; i < idsList.size(); i++) {
                Map map = new HashMap();
                map.put("id", idsList.get(i));
                map.put("domain", type);
                TenantConfigInstance tenantConfigInstance = iTenantConfigInstanceDAO.queryOne(map, "id", "asc");
                if (tenantConfigInstance == null)
                    return false;
                tenantConfigInstances.add(tenantConfigInstance);
            }

            List<TenantConfigInstance> sortResultTenantConfigInstances = new ArrayList<TenantConfigInstance>();
            TenantConfigInstance tenantConfigInstanceStart = tenantConfigInstances.get(0);
            TenantConfigInstance tenantConfigInstanceEnd = tenantConfigInstances.get(1);
            //存储一个order
            Integer temp = tenantConfigInstanceStart.getConfigOrder();
            //调换另一个对象的order
            tenantConfigInstanceStart.setConfigOrder(tenantConfigInstanceEnd.getConfigOrder());
            tenantConfigInstanceStart.setModifyDate(System.currentTimeMillis());
            //调换temporder
            tenantConfigInstanceEnd.setConfigOrder(temp);
            tenantConfigInstanceEnd.setModifyDate(System.currentTimeMillis());

            sortResultTenantConfigInstances.add(tenantConfigInstanceStart);
            sortResultTenantConfigInstances.add(tenantConfigInstanceEnd);

            Integer sortResult = exiTenantConfigInstanceDAO.sortConfigUpdate(sortResultTenantConfigInstances);
            result = sortResult > 0 ? true : false;
        }

        LOGGER.info("===============租户表头排序 E==============");

        return result;
    }

    /**
     * 查询当前租户&当前模块下的表头信息
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    @Override
    public List<TenantConfigInstanceView> getTenantConfigListByTnIdAndType(String type, Integer tnId) {

        if (StringUtils.isBlank(type) || tnId <= 0)
            return null;

        Map map = new HashMap();
        map.put("domain", type);
        map.put("tnId", tnId);
        List<TenantConfigInstanceView> tenantConfigInstances = exiTenantConfigInstanceDAO.selectTeanConfigList(map);

        return tenantConfigInstances;
    }

    /**
     * 获取当前租户表头 数组 - 用于导出表头excel
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    @Override
    public String[] getTenantConfigListArrByTnIdAndType(String type, Integer tnId) {
        List<TenantConfigInstanceView> tenantConfigInstanceViews = this.getTenantConfigListByTnIdAndType(type, tnId);
        String[] configArr = new String[tenantConfigInstanceViews.size()];
        for (int i = 0; i < tenantConfigInstanceViews.size(); i++) {
            TenantConfigInstanceView tenantConfigInstanceView = tenantConfigInstanceViews.get(i);
            configArr[i] = tenantConfigInstanceView.getName();
            LOGGER.info("表头[" + i + "]:" + tenantConfigInstanceView.getName());
        }
        return configArr;
    }
    /**
     * 生成租户自选表头
     * @param ids  表头id集
     * @param tnId 租户
     *
     * @return
     *
     * 0:成功
     * -1:失败,系统错误
     * 1:参数错误
     * 2:该租户不存在
     * 3:选项集校验失败
     */
    @Override
    public String importConfig(String type,String ids,Integer tnId) {
        String result = EnumUtil.ErrorCode.getDesc(EnumUtil.GLOBAL_SYSTEMERROR);

        LOGGER.info("===============生成租户自选表头 S==============");
        LOGGER.info("ids:" + ids);
        LOGGER.info("tnId:" + tnId);
        List<String> idsList = ParamsUtils.idsSplit(ids);

        if (tnId <= 0 || idsList == null) {
            LOGGER.info("result:1[参数错误]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_PARAMSERROR);
        }

        if (isExistConfigDataByTnId(type,tnId)) {
            LOGGER.info("tnId:" + tnId + "=[该租户存在历史表头数据.]");
            boolean removeResult = removeConfigDataByTnId(type,tnId);
            LOGGER.info("removeResult:" + removeResult);

            if (!removeResult)
                return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_REMOVEHISTORYERROR);
        }

        Tenant tenant = iTenantDAO.findOne("id", tnId, "id", "asc");

        if (tenant == null) {
            LOGGER.info("result:2[该租户不存在]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_TEANTNULL);
        }

        List<TenantConfigInstance> tenantConfigInstances = new ArrayList<TenantConfigInstance>();

        boolean isNext = true;
        for (String configId : idsList) {
            Map map = new HashMap();
            map.put("id", configId);
            map.put("domain",type);
            Configuration configuration = iConfigurationDAO.queryOne(map, "id", "asc");
            if (configuration == null) {
                LOGGER.info("[ERROR]-configId:" + configId);
                isNext = false;
                break;
            }
            TenantConfigInstance tenantConfigInstance = configStructure(configuration, tnId);

            tenantConfigInstances.add(tenantConfigInstance);
        }
        LOGGER.info("isNext:[配置选项集校验结果]" + isNext);

        if (isNext) {

            Integer addResult = exiTenantConfigInstanceDAO.addConfigs(tenantConfigInstances);

            result = addResult > 0 ? EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_SUCCESS) : result;

        } else {
            LOGGER.info("result:[选项集校验失败]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_CONFIGSVALIDERROR);
        }

        LOGGER.info("===============生成租户自选表头 E==============");
        return result;
    }


    /**
     * 封装租户已选表头对象
     * @param configuration 租户已选配置对象
     * @param tnId 租户ID
     * @return
     */
    private  TenantConfigInstance configStructure(Configuration configuration,Integer tnId) {
        TenantConfigInstance tenantConfigInstance = new TenantConfigInstance();
        tenantConfigInstance.setCheckRule(configuration.getCheckRule());
        tenantConfigInstance.setConfigKey(configuration.getId().toString());
        tenantConfigInstance.setConfigOrder(configuration.getConfigOrder());
        tenantConfigInstance.setTnId(tnId);
        tenantConfigInstance.setDomain(configuration.getDomain());
        tenantConfigInstance.setCreateDate(System.currentTimeMillis());
        tenantConfigInstance.setModifyDate(null);
        return tenantConfigInstance;
    }
}
