package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.gk.api.IMajoredApi;
import cn.thinkjoy.saas.dto.CourseAndTeacherDto;
import cn.thinkjoy.saas.service.bussiness.reform.ISelectClassesGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yangguorong on 17/1/18.
 *
 * 选课指导模块
 */
@Controller
@RequestMapping("/guidance")
public class CourseGuidanceController {

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
    public List<CourseAndTeacherDto> queryTeacherBytnIdAndGrade(@RequestParam String grade,@RequestParam String tnId){
        return selectClassesGuideService.queryTeachersByGrade(grade,tnId);
    }
}
