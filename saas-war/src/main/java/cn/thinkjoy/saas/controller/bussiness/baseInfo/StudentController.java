package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.saas.common.Env;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.Configuration;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import cn.thinkjoy.saas.service.bussiness.*;
import cn.thinkjoy.saas.service.common.ExcelUtils;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.collect.Lists;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 17/2/13.
 *
 * 学生管理
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Resource
    IEXTenantCustomService iexTenantCustomService;

    @Resource
    EXITenantConfigInstanceService exiTenantConfigInstanceService;

    @Resource
    IEXTenantService iexTenantService;

    @Resource
    EXIConfigurationService exiConfigurationService;

    @Autowired
    Env env;


    /**
     * 默认表头初始化
     *
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/initStudent",method = RequestMethod.POST)
    @ResponseBody
    public Map initStudent(@RequestParam Integer tnId) {

        boolean result = exiConfigurationService.selectListRetain(Constant.STUDENT, tnId);
        Map resultMap = new HashMap();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 获取未添加的表头字段集合
     *
     * @return
     */
    @RequestMapping(value = "/getNotAddHeader", method = RequestMethod.GET)
    @ResponseBody
    public List<Configuration> getInitConfiguration(@RequestParam Integer tnId) {
        List<Configuration> configurationList = exiConfigurationService.selectNotAddHeaderByTnId(tnId,Constant.STUDENT);
        return configurationList;
    }


    /**
     * 根据租户和类型导出excel表头
     *
     * @param type 0：有教学班年级学生模板下载  1：无教学班年级学生模板下载
     * @param tnId 租户ID
     * @return
     */
    @RequestMapping(value = "/downloadStuMould", method = RequestMethod.GET)
    public String downloadStuMould(HttpServletResponse response,
                               @RequestParam Integer type,
                               @RequestParam Integer tnId) throws IOException {
        LOGGER.info("===============导出租户student excel表头 S================");
        LOGGER.info("type:" + type);
        LOGGER.info("tnId:" + tnId);
        String [] tempArr = exiTenantConfigInstanceService.getTenantConfigListArrByTnIdAndType(Constant.STUDENT, tnId);
        String [] columnNames = getInitConfiguration(type,tempArr);
        List<Map<Integer, Object>> maps = iexTenantCustomService.isExcelAddSelect(Constant.STUDENT,tnId, columnNames);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtils.createWorkBook(columnNames, maps).write(os);
            LOGGER.info("Student Excel创建完成!");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("Student Excel创建失败![" + e.getMessage() + "]");
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((ExcelUtils.getFileName(Constant.STUDENT+"_"+type, tnId) + ".xls").getBytes(), "iso-8859-1"));
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
            LOGGER.info("Student Excel文件流导出完成");
        } catch (final IOException e) {
            LOGGER.info("Student Excel文件流导出失败![" + e.getMessage() + "]");
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
            if (out !=null)
                out.close();
            LOGGER.info("===============导出租户student excel表头 E================");
        }
        return null;
    }

    /**
     * 根据班级类型组合属性
     *
     * @param type 0：教学班  1：行政班
     * @param tempArr
     * @return
     */
    private String [] getInitConfiguration(int type,String [] tempArr){

        if(type == 0){
            return tempArr;
        }

        String[] columns = new String[tempArr.length - Constant.ZDBJ_COLUMNS_VALUE.split(",").length];
        int i = 0;
        for(String column : tempArr){
            if(Constant.ZDBJ_COLUMNS_VALUE.indexOf(column) == -1){
                columns[i] = column;
                i++;
            }
        }

        return columns;
    }

    /**
     * 查询学生属性信息
     *
     * @param type  0：教学班  1：行政班
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/getStuExcelHeader", method = RequestMethod.GET)
    @ResponseBody
    public List<TenantConfigInstanceView> getStuExcelHeader(@RequestParam Integer type,
                                 @RequestParam Integer tnId) {

        List<TenantConfigInstanceView> tenantConfigInstances = exiTenantConfigInstanceService.getTenantConfigListByTnIdAndType(Constant.STUDENT, tnId);

        return getInitConfiguration(type,tenantConfigInstances);
    }

    /**
     * 根据班级类型组合字段属性
     *
     * @param type 0：教学班  1：行政班
     * @param tenantConfigInstances
     * @return
     */
    private List<TenantConfigInstanceView> getInitConfiguration(int type,List<TenantConfigInstanceView> tenantConfigInstances){

        if(type == 0){
            return tenantConfigInstances;
        }

        List<TenantConfigInstanceView> list = Lists.newArrayList();
        for(TenantConfigInstanceView view : tenantConfigInstances){
            if(Constant.ZDBJ_COLUMNS_KEY.indexOf(view.getEnName()) == -1){
                list.add(view);
            }
        }

        return list;
    }

    /**
     * 添加学生表头字段
     *
     * @param tnId
     * @param ids
     * @return
     */
    @RequestMapping(value = "/addStuExcelHeader", method = RequestMethod.POST)
    @ResponseBody
    public Map addStuExcelHeader(@RequestParam Integer tnId,
                            @RequestParam String ids) {

        String exMsg = exiTenantConfigInstanceService.createConfig(Constant.STUDENT, ids, tnId);

        Map resultMap = new HashMap();
        resultMap.put("result", exMsg);
        return resultMap;
    }

    /**
     * 移除租户表头字段
     *
     * @param tnId
     * @param ids
     * @return
     */
    @RequestMapping(value = "/removeStuExcelHeader", method = RequestMethod.POST)
    @ResponseBody
    public Map removeStuExcelHeader(@RequestParam Integer tnId,
                                 @RequestParam String ids) {

        boolean result = exiTenantConfigInstanceService.removeTeantConfigs(Constant.STUDENT, tnId, ids);
        Map resultMap = new HashMap();
        resultMap.put("result", result ? "SUCCESS" : "FAIL");
        return resultMap;
    }

    /**
     * excel模板上传
     *
     * @param type 0：教学班  1：行政班
     * @param tnId
     * @param myfile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadStuExcel",method = RequestMethod.GET)
    @ResponseBody
    public Map uploadStuExcel(@RequestParam String type,
                           @RequestParam Integer tnId,
                           @RequestParam("inputFile") MultipartFile myfile) throws IOException {
        String result = "系统错误";
        LOGGER.info("==================student excel上传 S==================");
        if (myfile.isEmpty()) {
            LOGGER.info("文件未上传");
        } else {
            LOGGER.info("文件长度: " + myfile.getSize());
            LOGGER.info("文件类型: " + myfile.getContentType());
            LOGGER.info("文件名称: " + myfile.getName());
            LOGGER.info("文件原名: " + myfile.getOriginalFilename());
            String realPath = env.getProp("configuration.excel.upload.url");
            FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
            result = exiTenantConfigInstanceService.uploadExcel(Constant.STUDENT, tnId, realPath + myfile.getOriginalFilename());

        }
        LOGGER.info("==================student excel上传 E==================");
        Map resultMap = new HashMap();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 根据班级类型查询学生数据
     *
     * @param type 0：教学班  1：行政班
     * @param tnId
     * @param grade 年级
     * @param star
     * @param row
     * @return
     */
    @RequestMapping(value = "/getStuInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map  getTenantCustomData(@RequestParam Integer type,
                                    @RequestParam Integer tnId,
                                    @RequestParam String grade,
                                    @RequestParam Integer star,
                                    @RequestParam Integer row) {

        Integer s = (star == null) ? null : Integer.valueOf(star),
                r=(row==null)?null:Integer.valueOf(row);
        Map resultMap = new HashMap();
        List<LinkedHashMap<String, Object>> tenantCustom = iexTenantCustomService.getStuInfo(type, tnId, grade, s,r);
        resultMap.put("result", tenantCustom);
        Integer count = iexTenantCustomService.getStuInfoCount(type, tnId, grade);
        resultMap.put("count", count);
        return resultMap;
    }

    /**
     * 新增学生数据
     *
     * @return
     */
    @RequestMapping(value = "/addStuInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map addStuInfo(@RequestBody Request request) {

        List<TeantCustom> teantCustoms = (List<TeantCustom>) request.getData().get("teantCustomList");
        Integer tnId = request.getDataInteger("tnId");
//        Integer type = request.getDataInteger("type");

        String tableName = ParamsUtils.combinationTableName(Constant.STUDENT, tnId);

        List<String> removeIds=reantCustomRepeat(tableName,teantCustoms);
        if (removeIds != null && removeIds.size() > 0)
            exiTenantConfigInstanceService.removeTenantCustomList(tableName, removeIds);

        boolean result = iexTenantCustomService.addTeantCustom(Constant.STUDENT, tnId, teantCustoms);

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? (removeIds != null && removeIds.size() > 0) ? "存在重复数据，已经覆盖更新" : "SUCCESS" : "FAIL"));
        return resultMap;
    }

    private List<String> reantCustomRepeat(String tableName,List<TeantCustom> teantCustoms) {

        Map map = ParamsUtils.getTeantCustomDataValue(teantCustoms, Constant.STUDENT);

        Map repeat = iexTenantCustomService.selectExistByCloumn(
                tableName,
                Constant.STUDENT,
                map.get("value1").toString(),
                map.get("value2").toString()
        );

        List<String> removeIds = (List<String>) repeat.get("repeat");

        if (removeIds != null && removeIds.size() > 0)
            return removeIds;

        return null;
    }

    /**
     * 修改学生数据
     *
     * @return
     */
    @RequestMapping(value = "/updateStuInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map updateStuInfo(@RequestBody Request request) {

        List<TeantCustom> teantCustoms=(List<TeantCustom>) request.getData().get("teantCustomList");
//        Integer type = request.getDataInteger("type");
        Integer tnId = request.getDataInteger("tnId");
        Integer pri = request.getDataInteger("pri");
        String tableName = ParamsUtils.combinationTableName(Constant.STUDENT, tnId);
        List<String> removeIds = reantCustomRepeat(tableName, teantCustoms);

        Map resultMap = new HashMap();
        if (removeIds != null && removeIds.size() > 0) {
            resultMap.put("result", "存在重复数据,请修正!");
        }else {
            boolean result = iexTenantCustomService.modifyTeantCustom(Constant.STUDENT, tnId, pri, teantCustoms);
            resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        }
        return resultMap;
    }

    /**
     * 删除学生数据
     * @return
     */
    @RequestMapping(value = "/removeStuInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map removeStuInfo(HttpServletRequest request) {
        String tnId = request.getParameter("tnId");
        String ids = request.getParameter("ids");

        boolean result = iexTenantCustomService.removeTenantCustom(Constant.STUDENT, Integer.valueOf(tnId), ids);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

}
