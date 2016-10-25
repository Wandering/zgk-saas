package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.common.Env;
import cn.thinkjoy.saas.domain.ClassRooms;
import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.domain.EnrollingRatio;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import cn.thinkjoy.saas.service.IClassRoomsService;
import cn.thinkjoy.saas.service.IEnrollingRatioService;
import cn.thinkjoy.saas.service.bussiness.EXIClassRoomService;
import cn.thinkjoy.saas.service.bussiness.EXIConfigurationService;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.common.ExcelUtils;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/12.
 */
@Controller
@RequestMapping(value = "/config")
public class ConfigurationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationController.class);
    @Resource
    EXIConfigurationService exiConfigurationService;

    @Resource
    IClassRoomsService iClassRoomsService;

    @Resource
    EXIClassRoomService exiClassRoomService;

    @Resource
    EXIGradeService exiGradeService;


    @Resource
    IEnrollingRatioService iEnrollingRatioService;

    @Autowired
    Env env;
    @Resource
    EXITenantConfigInstanceService exiTenantConfigInstanceService;


    /**
     * 年级设置
     * @param tnId 租户ID
     * @param nums 年级
     * @return
     */
    @RequestMapping(value = "/class/setting/{tnId}/{nums}",method = RequestMethod.POST)
    @ResponseBody
    public Map classSetting(@PathVariable Integer tnId,@PathVariable String nums) {
        boolean result =exiGradeService.AddGrade(tnId,nums);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 查询年级列表
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/grade/get/{tnId}",method = RequestMethod.GET)
    @ResponseBody
    public Map getGradeByTnId(@PathVariable Integer tnId) {
        Map map = new HashMap();
        map.put("tnId", tnId);
        List<Grade> grades = exiGradeService.selectGradeByTnId(map);
        Map resultMap = new HashMap();
        resultMap.put("grades", grades);
        return resultMap;
    }

    /**
     * 教室查询
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/classRoom/get/{tnId}",method = RequestMethod.GET)
    @ResponseBody
    public Map getClassRoomByTnId(@PathVariable Integer tnId) {
        Map map = new HashMap();
        map.put("tnId", tnId);

        ClassRooms classRooms = exiClassRoomService.selectClassRoomByTnId(map);
        Map resultMap = new HashMap();
        resultMap.put("classRoom", classRooms);
        return resultMap;
    }
    /**
     * 教室设置
     * @param tnId 租户ID
     * @param nums 数量集  例:  1:1-2:2-3:3   1:高一年级:教室数量 2:高二年级:教室数量 3:高三年级:教室数量
     * @return
     */
    @RequestMapping(value = "/classRoom/setting/{tnId}/{nums}",method = RequestMethod.POST)
    @ResponseBody
    public Map classRoomSetting(@PathVariable Integer tnId,@PathVariable String nums) {

        boolean result = exiClassRoomService.addClassRoom(tnId, nums);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 升学率设置
     * @param tnId 租户ID
     * @param nums 数量集  例:  1-2-3-4-5   1:高三年级考生数量 2:去年一本上线人数 3:去年二本上线人数 4:去年三本上线人数 5:去年高职上线人数
     * @return
     */
    @RequestMapping(value = "/enrollingRatio/setting/{tnId}/{nums}",method = RequestMethod.POST)
    @ResponseBody
    public Map  enrollingRatioSetting(@PathVariable Integer tnId,@PathVariable String nums) {
        boolean result = false;
        if (tnId > 0 && !StringUtils.isBlank(nums)) {
            List<String> numArr = ParamsUtils.idsSplit(nums);
            if (numArr.size() == 5) {
                EnrollingRatio enrollingRatio = new EnrollingRatio();
                enrollingRatio.setTnId(tnId);
                enrollingRatio.setStu3numbers(Integer.valueOf(numArr.get(0)));
                enrollingRatio.setYear(ParamsUtils.getYear()-1);
                enrollingRatio.setBatch1enrolls(Integer.valueOf(numArr.get(1)));
                enrollingRatio.setBatch2enrolls(Integer.valueOf(numArr.get(2)));
                enrollingRatio.setBatch3enrolls(Integer.valueOf(numArr.get(3)));
                enrollingRatio.setBatch4enrolls(Integer.valueOf(numArr.get(4)));
                enrollingRatio.setCreateDate(System.currentTimeMillis());
                Integer addResu = iEnrollingRatioService.insert(enrollingRatio);
                result = addResu > 0 ? true : false;
            }
        }
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 获取初始化字段--type: class:班级  teacher:老师
     *
     * @return
     */
    @RequestMapping(value = "/getInit/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Map getInitConfiguration(@PathVariable String type) {
        List<Configuration> configurationList = exiConfigurationService.selectListBydomain(type);
        Map resultMap = new HashMap();
        resultMap.put("configList", configurationList);
        return resultMap;
    }

    /**
     * 查询租户表头List
     *
     * @param type
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/get/{type}/{tnId}", method = RequestMethod.GET)
    @ResponseBody
    public Map getTeantConfigList(@PathVariable String type, @PathVariable Integer tnId) {

        List<TenantConfigInstanceView> tenantConfigInstances = exiTenantConfigInstanceService.getTenantConfigListByTnIdAndType(type, tnId);

        Map resultMap = new HashMap();

        resultMap.put("configList", tenantConfigInstances);

        return resultMap;
    }

    /**
     * 生成租户自选表头
     *
     * @return
     */
    @RequestMapping(value = "/import/{type}/{tnId}", method = RequestMethod.POST)
    @ResponseBody
    public Map InsertConfig(@PathVariable String type, @PathVariable Integer tnId, HttpServletRequest request) {
        String ids = request.getParameter("ids");

        String exMsg = exiTenantConfigInstanceService.createConfig(type, ids, tnId);

        Map resultMap = new HashMap();
        resultMap.put("result", exMsg);
        return resultMap;
    }

    /**
     * 删除租户表头
     *
     * @param ids 表头ID
     * @return
     */
    @RequestMapping(value = "/tenant/remove/{ids}", method = RequestMethod.POST)
    @ResponseBody
    public Map removeTeantConfigs(@PathVariable String ids) {
        boolean result = exiTenantConfigInstanceService.removeTeantConfigs(ids);
        Map resultMap = new HashMap();
        resultMap.put("result", result ? "SUCCESS" : "FAIL");
        return resultMap;
    }

    /**
     * 租户表头排序
     *
     * @param type class:班级  teacher:教师
     * @param ids  排序集   {id0}-{id1}   max size:2
     * @return
     */
    @RequestMapping(value = "/sort/{type}/{ids}", method = RequestMethod.POST)
    @ResponseBody
    public Map sortTeantConfig(@PathVariable String type, @PathVariable String ids) {

        boolean result = exiTenantConfigInstanceService.sortTeantConfig(type, ids);

        Map resultMap = new HashMap();
        resultMap.put("result", result ? "SUCCESS" : "FAIL");
        return resultMap;
    }

    /**
     * 导出租户excel表头
     *
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    @RequestMapping(value = "/export/{type}/{tnId}")
    public String exportConfig(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable String type,
                               @PathVariable Integer tnId) throws IOException {
        LOGGER.info("===============导出租户excel表头 S================");
        LOGGER.info("type:" + type);
        LOGGER.info("tnId:" + tnId);
        String[] columnNames = exiTenantConfigInstanceService.getTenantConfigListArrByTnIdAndType(type, tnId);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtils.createWorkBook(columnNames).write(os);
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
            LOGGER.info("===============导出租户excel表头 E================");
        }
        return null;
    }

//    /**
//     * 导入租户设置Excel
//     *
//     * @param type
//     * @param tnId
//     * @return
//     */
//    @RequestMapping("/import/{type}/{tnId}")
//    @ResponseBody
//    public Map importConfig(@PathVariable String type,
//                            @PathVariable Integer tnId,
//                            HttpServletRequest request,
//                            HttpServletResponse response) {
//
////        boolean result = exiTenantConfigInstanceService.createTenantCombinationTable(type, tnId);
//
//        Map resultMap = new HashMap();
////        resultMap.put("result", result ? "SUCCESS" : "FAIL");
//        return resultMap;
//    }

    /**
     * excel模板上传
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/upload/{type}/{tnId}")
    @ResponseBody
    public Map uploadExcel(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable String type,
                           @PathVariable Integer tnId,
                           @RequestParam("inputFile") MultipartFile myfile) throws IOException {
        boolean result=false;
        LOGGER.info("==================excel上传 S==================");
        if (myfile.isEmpty()) {
            LOGGER.info("文件未上传");
        } else {
            LOGGER.info("文件长度: " + myfile.getSize());
            LOGGER.info("文件类型: " + myfile.getContentType());
            LOGGER.info("文件名称: " + myfile.getName());
            LOGGER.info("文件原名: " + myfile.getOriginalFilename());
            String realPath = env.getProp("configuration.excel.upload.url");
            FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
            result = exiTenantConfigInstanceService.uploadExcel(type, tnId, realPath + myfile.getOriginalFilename());
        }
        LOGGER.info("==================excel上传 E==================");
        Map resultMap = new HashMap();
        resultMap.put("result", result ? "SUCCESS" : "FAIL");
        return resultMap;
    }

}

