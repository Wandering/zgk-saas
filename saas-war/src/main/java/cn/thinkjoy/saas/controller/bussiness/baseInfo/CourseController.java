package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.domain.bussiness.CourseManageMapperVo;
import cn.thinkjoy.saas.service.ICourseManageService;
import cn.thinkjoy.saas.service.bussiness.IEXCourseBaseInfoService;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    IEXCourseBaseInfoService IEXCourseBaseInfoService;
    @Resource
    ICourseManageService iCourseManageService;


    /**
     * 课程基础信息集
     *
     * @return
     */
    @RequestMapping(value = "/get/baseInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map getCourse() {
        Map map = new HashMap();
        List<CourseBaseInfo> courseBaseInfoList = IEXCourseBaseInfoService.getCourseBaseInfoList();

        map.put("courses", courseBaseInfoList);
        return map;
    }

    /**
     * 获取租户下的课程设置信息
     *
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/get/manager/{tnId}",method = RequestMethod.GET)
    @ResponseBody
    public Map getCourseManager(@PathVariable Integer tnId) {
        Map map = new HashMap();
        Map parMap=new HashMap();
        parMap.put("tnId",tnId);
        List<CourseManageMapperVo> courseManages = iCourseManageService.selectCourseManageInfo(parMap);
        map.put("courses", courseManages);
        return map;
    }

    /**
     * 新增课程管理信息
     *
     * @return
     */
    @RequestMapping(value = "/add/manager",method = RequestMethod.POST)
    @ResponseBody
    public Map addCourseManager(@RequestBody Request req,HttpServletRequest request) {
        String ids = request.getParameter("ids");
        Map map = new HashMap();
        CourseManage courseManage = JSON.parseObject(req.getData().get("courseManage").toString(), CourseManage.class);

        boolean result = iCourseManageService.insertCourseManage(courseManage,ids);

        map.put("result", result );

        return map;
    }

    /**
     * 修改课程管理信息
     *
     * @return
     */
    @RequestMapping(value = "/upd/manager",method = RequestMethod.POST)
    @ResponseBody
    public Map updCourseManager(@RequestBody Request req,HttpServletRequest request) {
        String ids = request.getParameter("ids");
        Map map = new HashMap();
        CourseManage courseManage = JSON.parseObject(req.getData().get("courseManage").toString(), CourseManage.class);

        boolean result = iCourseManageService.updateCourseManage(courseManage,ids);

        map.put("result", result );

        return map;
    }

    /**
     * 删除课程管理信息
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/del/manager/{courseId}",method = RequestMethod.POST)
    @ResponseBody
    public Map delCourseManager(@PathVariable Integer courseId) {
        Map map = new HashMap();
        Integer result = iCourseManageService.delete(courseId);
        map.put("result", result > 0);
        return map;
    }


}
