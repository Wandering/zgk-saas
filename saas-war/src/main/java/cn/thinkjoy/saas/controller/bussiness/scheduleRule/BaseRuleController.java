package cn.thinkjoy.saas.controller.bussiness.scheduleRule;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dto.BaseRuleDto;
import cn.thinkjoy.saas.service.bussiness.scheduleRule.IBaseRule;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 * 基本规则
 */
@Controller
@RequestMapping("baseRuleController")
public class BaseRuleController {

    @Autowired
    private IBaseRule iBaseRule;

    @RequestMapping("selectJaqpByCourseId")
    @ResponseBody
    public Map<String,Object> selectJaqpByCourseId(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId,
                                           @RequestParam(value = "courseId",required = false)String courseId){

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        if(StringUtils.isNotBlank(courseId)) {
            paramMap.put("courseId", courseId);
        }
        paramMap.put("ruleTable","saas_jw_base_jaqp_rule");
        paramMap.put("teacherTable", ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER,Integer.valueOf(tnId)));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("baseRuleList",iBaseRule.selectBaseRule(paramMap));
        return resultMap;
    }

    @RequestMapping("updateJaqpById")
    @ResponseBody
    public Map<String,Object> updateJaqpById(HttpServletRequest request){
        String baseRuleList=request.getParameter("baseRuleList");
        List<BaseRuleDto> list=JSON.parseArray(baseRuleList, BaseRuleDto.class);
        iBaseRule.updateBaseRule("saas_jw_base_jaqp_rule", list);
        Map<String,Object> resultMap=new HashMap<>();
        return resultMap;
    }

    @RequestMapping("selectWeekByCourseId")
    @ResponseBody
    public Map<String,Object> selectWeekByCourseId(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId,
                                           @RequestParam(value = "courseId",required = false)String courseId){

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        if(StringUtils.isNotBlank(courseId)) {
            paramMap.put("courseId", courseId);
        }
        paramMap.put("ruleTable","saas_jw_base_week_rule");
        paramMap.put("teacherTable", ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER,Integer.valueOf(tnId)));
        paramMap.put("weekType",1);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("baseRuleList",iBaseRule.selectBaseRule(paramMap));
        return resultMap;
    }

    @RequestMapping("updateWeekById")
    @ResponseBody
    public Map<String,Object> updateWeekById(HttpServletRequest request){
        String baseRuleList=request.getParameter("baseRuleList");
        List<BaseRuleDto> list=JSON.parseArray(baseRuleList, BaseRuleDto.class);
        iBaseRule.updateBaseRule("saas_jw_base_week_rule", list);
        Map<String,Object> resultMap=new HashMap<>();
        return resultMap;
    }

    @RequestMapping("selectDayByCourseId")
    @ResponseBody
    public Map<String,Object> selectDayByCourseId(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId,
                                           @RequestParam(value = "courseId",required = false)String courseId){

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        if(StringUtils.isNotBlank(courseId)) {
            paramMap.put("courseId", courseId);
        }
        paramMap.put("ruleTable","saas_jw_base_day_rule");
        paramMap.put("teacherTable", ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER,Integer.valueOf(tnId)));
        paramMap.put("dayType",1);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("baseRuleList",iBaseRule.selectBaseRule(paramMap));
        return resultMap;
    }

    @RequestMapping("updateDayById")
    @ResponseBody
    public Map<String,Object> updateDayById(HttpServletRequest request){
        String baseRuleList=request.getParameter("baseRuleList");
        List<BaseRuleDto> list=JSON.parseArray(baseRuleList, BaseRuleDto.class);
        iBaseRule.updateBaseRule("saas_jw_base_day_rule", list);
        Map<String,Object> resultMap=new HashMap<>();
        return resultMap;
    }
    @RequestMapping("selectDayConByCourseId")
    @ResponseBody
    public Map<String,Object> selectDayConByCourseId(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId,
                                           @RequestParam(value = "courseId",required = false)String courseId){

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        if(StringUtils.isNotBlank(courseId)) {
            paramMap.put("courseId", courseId);
        }
        paramMap.put("ruleTable","saas_jw_base_con_rule");
        paramMap.put("teacherTable", ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER,Integer.valueOf(tnId)));
        paramMap.put("dayConType",1);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("baseRuleList",iBaseRule.selectBaseRule(paramMap));
        return resultMap;
    }

    @RequestMapping("updateDayConById")
    @ResponseBody
    public Map<String,Object> updateDayConById(HttpServletRequest request){
        String baseRuleList=request.getParameter("baseRuleList");
        List<BaseRuleDto> list=JSON.parseArray(baseRuleList, BaseRuleDto.class);
        iBaseRule.updateBaseRule("saas_jw_base_con_rule", list);
        Map<String,Object> resultMap=new HashMap<>();
        return resultMap;
    }


}
