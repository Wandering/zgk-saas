package cn.thinkjoy.saas.controller.bussiness.reform;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.api.IMajoredApi;
import cn.thinkjoy.saas.dto.*;
import cn.thinkjoy.saas.service.bussiness.reform.ISelectClassesGuideService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by zuohao on 16/11/2.
 */
@Controller
@RequestMapping("/selectClassesGuide")
public class SelectClassesGuideController {

    @Autowired
    private ISelectClassesGuideService iSelectClassesGuideService;

    @Autowired
    private IMajoredApi iMajoredApi;

    @Autowired
    private ISelectClassesGuideService selectClassesGuideService;

    @ResponseBody
    @ApiDesc(value = "查询有招生计划的年份")
    @RequestMapping(value = "getEnrollingYear",method = RequestMethod.GET)
    public List<String> getEnrollingYear(){
        return iMajoredApi.getEnrollingYear();
    }

    @ResponseBody
    @ApiDesc(value = "根据年份查询个种选课组合的招生院校")
    @RequestMapping(value = "selectMajorTopCount",method = RequestMethod.GET)
    public Object selectMajorTopCount(@RequestParam String year){
        return iMajoredApi.selectMajorTopCount(35,year);
    }

    @ResponseBody
    @ApiDesc(value = "根据年份查询个种选课组合的招生院校")
    @RequestMapping(value = "queryTeacherBytnIdAndGrade",method = RequestMethod.GET)
    public List<CourseAndTeacherDto> queryTeacherBytnIdAndGrade(@RequestParam String grade, @RequestParam String tnId){
        return selectClassesGuideService.queryTeachersByGrade(grade,tnId);
    }

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
    @RequestMapping("getUniversityName")
    @ResponseBody
    public Map<String,Object> getUniversityName(@RequestParam(value = "subject",required = false)String subject,
                                                @RequestParam(value = "universityName",required = false)String universityName,
                                                @RequestParam(value = "universityId",required = false)String universityId,
                                                @RequestParam(value = "offset",required = false,defaultValue = "0")String offset,
                                                @RequestParam(value = "rows",required = false,defaultValue = "5")String rows) {
        return getStringObjectMap(0, subject, universityName, universityId, offset, rows);
    }
    /**
     * 输入院校名
     * @return
     */
    @RequestMapping("getMajorByUniversityNameAndBatch")
    @ResponseBody
    public Map<String,Object> getMajorByUniversityNameAndBatch(@RequestParam(value = "subject",required = false)String subject,
                                                               @RequestParam(value = "universityName",required = false)String universityName,
                                                               @RequestParam(value = "universityId",required = false)String universityId,
                                                               @RequestParam(value = "offset",required = false,defaultValue = "0")String offset,
                                                               @RequestParam(value = "rows",required = false,defaultValue = "10")String rows) {
        return getStringObjectMap(1, subject, universityName, universityId, offset, rows);
    }

    /**
     * 211,包含了985
     * @return
     */
    @RequestMapping("getPlanEnrollingByProperty")
    @ResponseBody
    public Map<String,Object> getPlanEnrollingByProperty(@RequestParam(value = "subject",required = false)String subject) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        if(StringUtils.isNotBlank(subject)) {
            paramMap.put("subject", subject);
        }
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
    public Map<String,Object> getAnalysisBatch(@RequestParam(value = "subject",required = false)String subject) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        if(StringUtils.isNotBlank(subject)) {
            paramMap.put("subject", subject);
        }
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
    public Map<String,Object> getAnalysisDiscipline(@RequestParam(value = "subject",required = false)String subject) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        if(StringUtils.isNotBlank(subject)) {
            paramMap.put("subject", subject);
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("analysisDiscipline", iSelectClassesGuideService.selectAnalysisDiscipline(paramMap));
//        returnMap.put("count",iSelectClassesGuideService.selectAnalysisDisciplineCount(paramMap));
        return returnMap;
    }

    private Map<String, Object> getStringObjectMap(int flag, String subject, String universityName, String universityId, String offset, String rows) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        paramMap.put("flag",flag);
        if(StringUtils.isNotBlank(subject)) {
            paramMap.put("subject", subject);
        }
        if (StringUtils.isNotBlank(universityName)) {
            paramMap.put("universityName", universityName);
        }
        if (StringUtils.isNotBlank(universityId)) {
            paramMap.put("universityId", universityId);
        }
        paramMap.put("offset",offset);
        paramMap.put("rows",rows);
        List<MajorDto> majorDtoList=iSelectClassesGuideService.selectMajorByUniversityNameAndBatch(paramMap);
        if(flag==1) {
            Map<String, Object> propertyMap = new HashMap();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("type", "FEATURE");
            List<Map<String, Object>> dictMapList = iSelectClassesGuideService.queryDictList(map1);
            for (MajorDto majorDto : majorDtoList) {
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
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("majorByUniversityNameAndBatch", majorDtoList);
        if(flag==1) {
            returnMap.put("count", iSelectClassesGuideService.selectMajorByUniversityNameAndBatchCount(paramMap));
        }
        return returnMap;
    }

    @RequestMapping("getEnrollingNumberByBatch")
    @ResponseBody
    public Map<String,Object> getEnrollingNumberByBatch(){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("enrollingNumberByBatch", iSelectClassesGuideService.selectEnrollingNumberByBatch(paramMap));
        return returnMap;
    }

    @RequestMapping("getEnrollingNumberTable")
    @ResponseBody
    public Map<String,Object> getEnrollingNumberTable(){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("areaId","330000");
        paramMap.put("year","2016");
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("enrollingNumberTable", iSelectClassesGuideService.selectEnrollingNumber(paramMap));
        return returnMap;
    }

    @RequestMapping("getAnalysisGroup")
    @ResponseBody
    public List<Map<String,Object>> getAnalysisGroup(@RequestParam("grade") String grade,@RequestParam("tnId") String tnId) {

        return iSelectClassesGuideService.getAnalysisGroup(grade,tnId);
    }

    /**
     * 往年选课分析
     * @return
     */
    @RequestMapping("getNumberByYear")
    @ResponseBody
    public Map<String,Object> getNumberByYear(@RequestParam("tnId")int tnId){
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("data", (Map<String,Map<String, Integer>>)iSelectClassesGuideService.selectNumberByYear(tnId).get("yearMap"));
        return returnMap;
    }


    @RequestMapping("selectTypeAnalysis")
    @ResponseBody
    public Map<String,Object> selectTypeAnalysis(@RequestParam("tnId")int tnId,@RequestParam("studentGrade")String studentGrade){
        return iSelectClassesGuideService.selectTypeAnalysis(tnId, studentGrade);
    }

    @RequestMapping("getUndergraduateEnrollingNumber")
    @ResponseBody
    public Object getUndergraduateEnrollingNumber(@RequestParam("tnId")int tnId){
        Map<String,Object> numberMap=iSelectClassesGuideService.selectNumberByYear(tnId);
        Map<String,Map<String, Integer>> data=(Map<String,Map<String, Integer>>)numberMap.get("yearMap");
        List<String> yearList = new ArrayList<String>(((Map<String,Integer>)numberMap.get("yearCountMap")).keySet());
        Map<String,Object> map=iSelectClassesGuideService.getUndergraduateEnrollingNumber(tnId,yearList);
        String maxYear=map.get("maxYear").toString();
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("undergraduateEnrollingNumberList",map.get("undergraduateEnrollingNumberList"));
        if (data.containsKey(maxYear)){
            Map<String, Integer> maxYearData=data.get(maxYear);
            Integer count=Integer.valueOf(((Map<String,Integer>)numberMap.get("yearCountMap")).get(maxYear));
            Map<String,Object> coursePercent=new HashMap<>();
            for (String course:maxYearData.keySet()){
                double percent=(double)(Math.round(maxYearData.get(course)*100/count)/100.0);
                coursePercent.put(course,percent);
            }
            returnMap.put("maxYear",maxYear);
            returnMap.put("coursePercent",coursePercent);
        }else {
            returnMap.put("msg","选课数据或录取情况不匹配，无法获取录取数人数最多比例数据");
        }
        returnMap.put("data",data);
        return returnMap;
    }

}
