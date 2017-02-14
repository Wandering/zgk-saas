package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.service.bussiness.ICourseBaseInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 17/2/13.
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    @Resource
    ICourseBaseInfoService iCourseBaseInfoService;


    /**
     * 课程基础信息集
     * @return
     */
    @RequestMapping("/get/baseInfo")
    @ResponseBody
    public Map getCourse() {
        Map map = new HashMap();
        List<CourseBaseInfo> courseBaseInfoList = iCourseBaseInfoService.getCourseBaseInfoList();

        map.put("courses", courseBaseInfoList);
        return map;
    }

}
