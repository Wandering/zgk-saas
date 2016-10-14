package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import cn.thinkjoy.saas.service.bussiness.EXIConfigurationService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/12.
 */
@Controller
@RequestMapping(value = "/config")
public class ConfigurationController {

    @Resource
    EXIConfigurationService exiConfigurationService;

    @Resource
    EXITenantConfigInstanceService exiTenantConfigInstanceService;

    /**
     * 获取初始化字段--type: class:班级  teacher:老师
     * @return
     */
    @RequestMapping(value ="/getInit/{type}",method = RequestMethod.GET)
    @ResponseBody
    public Map getInitConfiguration(@PathVariable String type) {
        List<Configuration> configurationList = exiConfigurationService.selectListBydomain(type);
        Map resultMap = new HashMap();
        resultMap.put("configList", configurationList);
        return resultMap;
    }

    /**
     * 查询租户表头List
     * @param type
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/get/{type}/{tnId}",method = RequestMethod.GET)
    @ResponseBody
    public Map getTeantConfigList(@PathVariable String type,@PathVariable Integer tnId) {

        List<TenantConfigInstanceView> tenantConfigInstances = exiTenantConfigInstanceService.getTenantConfigListByTnIdAndType(type, tnId);

        Map resultMap = new HashMap();

        resultMap.put("configList", tenantConfigInstances);

        return resultMap;
    }

    /**
     * 生成租户自选表头
     * @return
     */
    @RequestMapping(value = "/import/{type}/{tnId}",method = RequestMethod.POST)
    @ResponseBody
    public Map InsertConfig(@PathVariable String type,@PathVariable Integer tnId,HttpServletRequest request) {
        String ids = request.getParameter("ids");

        String exMsg = exiTenantConfigInstanceService.importConfig(type,ids, tnId);

        Map resultMap = new HashMap();
        resultMap.put("result", exMsg);
        return resultMap;
    }

    /**
     * 删除租户表头
     * @param ids   表头ID
     * @return
     */
    @RequestMapping(value = "/tenant/remove/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public Map removeTeantConfigs(@PathVariable String ids) {
        boolean result = exiTenantConfigInstanceService.removeTeantConfigs(ids);
        Map resultMap = new HashMap();
        resultMap.put("result", result ? "SUCCESS" : "FAIL");
        return resultMap;
    }

    /**
     * 租户表头排序
     * @param type  class:班级  teacher:教师
     * @param ids  排序集   {id0}-{id1}   max size:2
     * @return
     */
    @RequestMapping(value = "/sort/{type}/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public Map sortTeantConfig(@PathVariable String type,@PathVariable String ids) {

        boolean result = exiTenantConfigInstanceService.sortTeantConfig(type, ids);

        Map resultMap = new HashMap();
        resultMap.put("result", result ? "SUCCESS" : "FAIL");
        return resultMap;
    }
}

