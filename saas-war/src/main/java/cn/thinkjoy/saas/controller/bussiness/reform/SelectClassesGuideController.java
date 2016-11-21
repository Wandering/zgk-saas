package cn.thinkjoy.saas.controller.bussiness.reform;

import cn.thinkjoy.saas.dto.AnalysisDto;
import cn.thinkjoy.saas.dto.MajorDto;
import cn.thinkjoy.saas.dto.PlanEnrollingDto;
import cn.thinkjoy.saas.dto.UniversityAndMajorNumberDto;
import cn.thinkjoy.saas.service.bussiness.reform.ISelectClassesGuideService;
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
 * Created by zuohao on 16/11/2.
 */
@Controller
@RequestMapping("/selectClassesGuide")
public class SelectClassesGuideController {

    @Autowired
    private ISelectClassesGuideService iSelectClassesGuideService;

    /**
     * 科目分析
     * @return
     */
    @RequestMapping("getUniversityAndMajorNumber")
    @ResponseBody
    public Map<String,Object> getUniversityAndMajorNumber(@RequestParam(value = "subject",required = false)String subject) {

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        if(StringUtils.isNotBlank(subject)) {
            paramMap.put("subject", subject);
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("universityAndMajorNumber", iSelectClassesGuideService.selectUniversityAndMajorNumber(paramMap));
        return returnMap;
    }

    /**
     * 输入院校名
     * @return
     */
    @RequestMapping("getMajorByUniversityNameAndBatch")
    @ResponseBody
    public Map<String,Object> getMajorByUniversityNameAndBatch(@RequestParam("subject")String subject,
                                                               @RequestParam("batch")String batch,
                                                               @RequestParam(value = "universityName",required = false)String universityName,
                                                               @RequestParam(value = "offset",required = false,defaultValue = "0")String offset,
                                                               @RequestParam(value = "rows",required = false,defaultValue = "10")String rows) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        paramMap.put("batch",batch);
        paramMap.put("subject",subject);
        if (StringUtils.isNotBlank(universityName)) {
            paramMap.put("universityName", universityName);
        }
        paramMap.put("offset",offset);
        paramMap.put("rows",rows);
        List<MajorDto> majorDtoList=iSelectClassesGuideService.selectMajorByUniversityNameAndBatch(paramMap);
        Map<String, Object> propertyMap = new HashMap();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("type", "FEATURE");
        List<Map<String, Object>> dictMapList = iSelectClassesGuideService.queryDictList(map1);
        for(MajorDto majorDto:majorDtoList) {
            if (StringUtils.isNotEmpty(majorDto.getProperty())) {
                String[] propertys = majorDto.getProperty().split(",");
                for (String property : propertys) {
                    for (Map<String, Object> propertyDict : dictMapList) {
                        if (property.equals(propertyDict.get("name"))) {
                            propertyMap.put(propertyDict.get("id").toString(), property);
                        }
                    }
                }
            }
            majorDto.setPropertys(propertyMap);
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("majorByUniversityNameAndBatch", iSelectClassesGuideService.selectMajorByUniversityNameAndBatch(paramMap));
        returnMap.put("count",iSelectClassesGuideService.selectMajorByUniversityNameAndBatchCount(paramMap));
        return returnMap;
    }

    /**
     * 211,包含了985
     * @return
     */
    @RequestMapping("getPlanEnrollingByProperty")
    @ResponseBody
    public Map<String,Object> getPlanEnrollingByProperty(@RequestParam("subject")String subject) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        paramMap.put("subject",subject);
        paramMap.put("property","211");
        Map<String,Object> returnMap=new HashMap<>();
        List<PlanEnrollingDto> planEnrollingDtoList= iSelectClassesGuideService.selectPlanEnrollingByProperty(paramMap);
        int number1=0;
        int number2=0;
        for(PlanEnrollingDto planEnrollingDto:planEnrollingDtoList){
            if(planEnrollingDto.getProperty().contains("985")){
                number1=number1+Integer.valueOf(planEnrollingDto.getPlanEnrolling());
            }else {
                number2 = number2 + Integer.valueOf(planEnrollingDto.getPlanEnrolling());
            }
        }
        returnMap.put("985高校",number1);
        returnMap.put("211工程",number2);
        return returnMap;
    }

    /**
     * 按批次分析
     * @return
     */
    @RequestMapping("getAnalysisBatch")
    @ResponseBody
    public Map<String,Object> getAnalysisBatch(@RequestParam("subject")String subject) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        paramMap.put("subject",subject);
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("analysisBatch", iSelectClassesGuideService.selectAnalysisBatch(paramMap));
//        returnMap.put("count",iSelectClassesGuideService.selectAnalysisBatchCount(paramMap));
        return returnMap;
    }

    /**
     * 按专业类别分析
     * @return
     */
    @RequestMapping("getAnalysisDiscipline")
    @ResponseBody
    public Map<String,Object> getAnalysisDiscipline(@RequestParam("subject")String subject) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        paramMap.put("subject",subject);
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("analysisDiscipline", iSelectClassesGuideService.selectAnalysisDiscipline(paramMap));
//        returnMap.put("count",iSelectClassesGuideService.selectAnalysisDisciplineCount(paramMap));
        return returnMap;
    }

    @RequestMapping("getAnalysisGroup")
    @ResponseBody
    public List<Map<String,Object>> getAnalysisGroup(@RequestParam("grade") String grade,@RequestParam("tnId") String tnId) {

        return iSelectClassesGuideService.getAnalysisGroup(grade,tnId);
    }

}
