package cn.thinkjoy.saas.controller.bussiness.reform;

import cn.thinkjoy.saas.dto.EnrollingInfoDto;
import cn.thinkjoy.saas.service.bussiness.reform.ISelectClassesGuideService;
import cn.thinkjoy.saas.service.bussiness.reform.ITrineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/16.
 */
@Controller
@RequestMapping("trineController")
public class TrineController {

    @Autowired
    private ITrineService iTrineService;

    @Autowired
    private ISelectClassesGuideService iSelectClassesGuideService;

    @RequestMapping("getEnrollingNumberByBatch")
    @ResponseBody
    public Map<String,Object> getEnrollingNumberByBatch(){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        return iTrineService.selectEnrollingNumberByBatch(paramMap);
    }

    @RequestMapping("getEnrollingInfo")
    @ResponseBody
    public Map<String,Object> getEnrollingInfo(@RequestParam(value = "offset",required = false,defaultValue = "0")String offset,
                                               @RequestParam(value = "rows",required = false,defaultValue = "20")String rows){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        paramMap.put("offset",offset);
        paramMap.put("rows",rows);
        List<EnrollingInfoDto> enrollingInfoDtoList=iTrineService.selectEnrollingInfo(paramMap);
        int enrollingInfoDtoCountList=iTrineService.selectEnrollingInfoCount(paramMap);

        Map<String, Object> propertyMap = new HashMap();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("type", "FEATURE");
        List<Map<String, Object>> dictMapList = iSelectClassesGuideService.queryDictList(map1);
        for(EnrollingInfoDto enrollingInfoDto:enrollingInfoDtoList) {
            if (StringUtils.isNotEmpty(enrollingInfoDto.getProperty())) {
                String[] propertys = enrollingInfoDto.getProperty().split(",");
                for (String property : propertys) {
                    for (Map<String, Object> propertyDict : dictMapList) {
                        if (property.equals(propertyDict.get("name"))) {
                            propertyMap.put(propertyDict.get("id").toString(), property);
                        }
                    }
                }
            }
            enrollingInfoDto.setPropertys(propertyMap);
        }

        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("enrollingInfo", enrollingInfoDtoList);
        returnMap.put("count", enrollingInfoDtoCountList);
        return returnMap;
    }

}
