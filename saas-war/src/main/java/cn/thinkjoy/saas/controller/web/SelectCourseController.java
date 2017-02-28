package cn.thinkjoy.saas.controller.web;

import cn.thinkjoy.saas.service.web.ISelectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zuohao on 17/2/22.
 */
@Controller
@RequestMapping("web/selectCourse")
public class SelectCourseController {

    @Autowired
    private ISelectCourseService iSelectCourseService;


    @RequestMapping("bindingSchool")
    @ResponseBody
    public Map<String,Object> bindingSchool(@RequestParam(value = "userId",required = true)String userId,
                                            @RequestParam(value = "schoolId",required = true)String schoolId,
                                            @RequestParam(value = "studentNo",required = true)String studentNo,
                                            @RequestParam(value = "studentName",required = true)String studentName){

        return iSelectCourseService.bindingSchool(schoolId,studentNo,studentName,userId);
    }

    @RequestMapping("getSelectCourseInfo")
    @ResponseBody
    public Map<String,Object> getSelectCourseInfo(@RequestParam(value = "schoolId",required = true)String schoolId,
                                                  @RequestParam(value = "studentNo",required = true)String studentNo) {
        return iSelectCourseService.getSelectCourseInfo(schoolId, studentNo);
    }

    @RequestMapping("addSelectCourse")
    @ResponseBody
    public Map<String,Object> addSelectCourse(@RequestParam(value = "major1",required = true)String major1,
                                              @RequestParam(value = "major2",required = true)String major2,
                                              @RequestParam(value = "major3",required = true)String major3,
                                              @RequestParam(value = "schoolCourse",required = true)String schoolCourse) {
        return null;
    }

    @RequestMapping("getSaasStudentInfo")
    @ResponseBody
    public Map<String,Object> getSaasStudentInfo(@RequestParam(value = "schoolId",required = true)String schoolId,
                                                 @RequestParam(value = "studentNo",required = true)String studentNo) {
        return iSelectCourseService.getSaasStudentInfo(schoolId,studentNo);
    }

}
