package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.service.bussiness.EXIClassRoomService;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by douzy on 16/10/21.
 */
@Controller
@RequestMapping(value = "/manage")
public class ManageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageController.class);

    @Resource
    EXIGradeService exiGradeService;

    @Resource
    EXIClassRoomService exiClassRoomService;

    /**
     * 新增年级
     * @param tnId 租户ID
     * @param request
     * @return
     */
    @RequestMapping(value = "/grade/add/{tnId}",method = RequestMethod.POST)
    @ResponseBody
    public Map addGrade(@PathVariable Integer tnId,HttpServletRequest request) {

        String gradeName = request.getParameter("gradeName");

        boolean result = exiGradeService.insertGrade(tnId, gradeName);

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    /**
     * 更新年级
     * @param tnId  租户ID
     * @param gId 年级标识
     * @param request
     * @return
     */
    @RequestMapping(value = "/grade/modify/{tnId}/{gid}",method = RequestMethod.POST)
    @ResponseBody
    public Map modifyGrade(@PathVariable Integer tnId,
                           @PathVariable Integer gId,
                           HttpServletRequest request) {

        String gradeName = request.getParameter("gradeName");

        boolean result = exiGradeService.updateGrade(tnId, gradeName,gId);

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    /**
     * 删除年级
     * @return
     */
    @RequestMapping(value = "/grade/delete/{gid}",method = RequestMethod.POST)
    @ResponseBody
    public Map deleteGrade(@PathVariable Integer gId,
                           HttpServletRequest request) {

        Map map = new HashMap();
        map.put("id", gId);
        Integer result = exiGradeService.deleteByMap(map);
        Map resultMap = new HashMap();
        resultMap.put("result", (result > 0 ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 新增教室
     * @param tnId 租户ID
     * @param request
     * @return
     */
    @RequestMapping(value = "/classRoom/add/{tnId}/{gId}",method = RequestMethod.POST)
    @ResponseBody
    public Map addClassRoom(@PathVariable Integer tnId,
                            @PathVariable Integer gId,
                            HttpServletRequest request) {
        String crNum = request.getParameter("crNum");
        boolean result = false;
        if (!StringUtils.isBlank(crNum)&&tnId>0&&gId>0) {
            Integer classRoomNum = Integer.valueOf(crNum);
            result = exiClassRoomService.addClassRoom(tnId, classRoomNum, gId);
        }
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    /**
     * 更新教室
     * @param cid 教室标识
     * @param request
     * @return
     */
    @RequestMapping(value = "/classRoom/modify/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Map modifyClassRoom(@PathVariable Integer cid,HttpServletRequest request) {

        String gid = request.getParameter("gid"),
                num = request.getParameter("num");

        boolean result = false;

        if (!StringUtils.isBlank(gid) && !StringUtils.isBlank(num)) {

            Integer g = Integer.valueOf(gid),
                    n = Integer.valueOf(num);

            result = exiClassRoomService.updateClassRoom(n, g, cid);

        }

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    /**
     * 删除教室
     * @param cid 教室标识
     * @return
     */
    @RequestMapping(value = "/classRoom/delete/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Map deleteClassRoom(@PathVariable Integer cid) {
        boolean result = false;
        if (cid > 0) {
            result = exiClassRoomService.removeClassRoom(cid);
        }
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

}
