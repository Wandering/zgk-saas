package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.service.bussiness.EXIConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/12.
 */
@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController {

    @Resource
    EXIConfigurationService exiConfigurationService;

    /**
     * 获取初始化字段--type: class:班级  teacher:老师
     * @return
     */
    @RequestMapping("/getInit/{type}")
    @ResponseBody
    public Map getInitConfiguration(@RequestParam String type) {
        List<Configuration> configurationList = exiConfigurationService.selectListBydomain(type);
        Map resultMap = new HashMap();
        resultMap.put("configList", configurationList);
        return resultMap;
    }
}
