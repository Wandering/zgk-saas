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
    public Map<String,Object> bindingSchool(@RequestParam(value = "schoolId",required = true)String schoolId,
                                            @RequestParam(value = "studentNo",required = true)String studentNo,
                                            @RequestParam(value = "studentName",required = true)String studentName){

        return iSelectCourseService.bindingSchool(schoolId,studentNo,studentName);
    }
}
