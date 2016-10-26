package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.domain.EnrollingRatio;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.service.bussiness.*;
import cn.thinkjoy.saas.service.common.ExcelUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

    @Resource
    IEXEnrollingRatioService iexEnrollingRatioService;

    @Resource
    IEXTenantCustomService iexTenantCustomService;

    @Resource
    EXITenantConfigInstanceService exiTenantConfigInstanceService;

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


    /**
     * 新增升学率
     * @return
     */
    @RequestMapping(value = "/enrollingRatio/add",method = RequestMethod.POST)
    @ResponseBody
    public Map addEnrollingRatio(@ModelAttribute EnrollingRatio enrollingRatio) {
        boolean result = iexEnrollingRatioService.addEnrollingRatio(enrollingRatio);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 更新升学率
     * @param enrollingRatio
     * @return
     */
    @RequestMapping(value = "/enrollingRatio/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map updateEnrollingRatio(@ModelAttribute EnrollingRatio enrollingRatio){
        boolean result = iexEnrollingRatioService.updateEnrollingRatio(enrollingRatio);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    /**
     * 新增租户自定义表头数据
     * @param type 模块名
     * @param tnId 租户ID
     * @param teantCustomList 数据集
     * @return
     */
    @RequestMapping(value = "/{type}/{tnId}/add",method = RequestMethod.POST)
    @ResponseBody
    public Map addTeantCustom(@PathVariable String type,
                              @PathVariable Integer tnId,
                              @ModelAttribute List<TeantCustom> teantCustomList) {
        boolean result = iexTenantCustomService.addTeantCustom(type, tnId, teantCustomList);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 更新租户自定义表头数据
     * @param type 模块名
     * @param tnId 租户ID
     * @param pri  主键
     * @param teantCustomList 数据集
     * @return
     */
    @RequestMapping(value = "/{type}/{tnId}/{pri}/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map modifyTeantCustom(@PathVariable String type,
                              @PathVariable Integer tnId,
                              @PathVariable Integer pri,
                              @ModelAttribute List<TeantCustom> teantCustomList) {
        boolean result = iexTenantCustomService.modifyTeantCustom(type, tnId, pri, teantCustomList);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 删除租户自定义表头数据
     * @param type 模块名
     * @param tnId 租户ID
     * @param pri  主键
     * @return
     */
    @RequestMapping(value = "/{type}/{tnId}/{pri}/remove",method = RequestMethod.POST)
    @ResponseBody
    public Map removeTenantCustom(@PathVariable String type,
                                  @PathVariable Integer tnId,
                                  @PathVariable Integer pri) {
        boolean result = iexTenantCustomService.removeTenantCustom(type, tnId, pri);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    /**
     * 导出租户excel模板
     * @param request
     * @param response
     * @param type   模块名
     * @param tnId   租户ID
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{type}/export/{tnId}",method = RequestMethod.GET)
    public Map exportTenantCustom(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable String type,
                                  @PathVariable Integer tnId) throws IOException {

        LOGGER.info("===============导出租户excel模板 S================");
        LOGGER.info("type:" + type);
        LOGGER.info("tnId:" + tnId);
        String[] columnNames = exiTenantConfigInstanceService.getTenantConfigListArrByTnIdAndType(type, tnId);
        List<LinkedHashMap<String, Object>> tenantCustoms=iexTenantCustomService.getTenantCustom(type, tnId);


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtils.createWorkBook(columnNames,tenantCustoms).write(os);
            LOGGER.info("Excel创建完成!");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("Excel创建失败![" + e.getMessage() + "]");
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((ExcelUtils.getFileName(type, tnId) + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            LOGGER.info("Excel文件流导出完成");
        } catch (final IOException e) {
            LOGGER.info("Excel文件流导出失败![" + e.getMessage() + "]");
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
            LOGGER.info("===============导出租户excel模板 E================");
        }
        return null;
    }
}
