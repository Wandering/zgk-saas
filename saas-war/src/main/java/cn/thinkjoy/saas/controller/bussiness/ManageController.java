package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.saas.domain.ClassRoomSetting;
import cn.thinkjoy.saas.domain.EnrollingRatio;
import cn.thinkjoy.saas.domain.bussiness.ClassView;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.service.IEnrollingRatioService;
import cn.thinkjoy.saas.service.bussiness.*;
import cn.thinkjoy.saas.service.common.ExcelUtils;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
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
    IEnrollingRatioService iEnrollingRatioService;

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
     * @param gid 年级标识
     * @param request
     * @return
     */
    @RequestMapping(value = "/grade/modify/{tnId}/{gid}",method = RequestMethod.POST)
    @ResponseBody
    public Map modifyGrade(@PathVariable Integer tnId,
                           @PathVariable Integer gid,
                           HttpServletRequest request) {

        String gradeName = request.getParameter("gradeName");

        boolean result = exiGradeService.updateGrade(tnId, gradeName,gid);

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 年级排序
     * @param tnId
     * @param ids
     * @return
     */
    @RequestMapping(value = "/sort/grade/{tnId}/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public Map sortGrade(@PathVariable Integer tnId,@PathVariable String ids){
        boolean result = exiGradeService.gradeSortUpdate(tnId,ids);

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 删除年级
     * @return
     */
    @RequestMapping(value = "/grade/delete/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public Map deleteGrade(@PathVariable String ids,
                           HttpServletRequest request) {

        boolean result = exiGradeService.removeGrades(ids);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
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
        String dayNum = request.getParameter("dayNum");
        boolean result = false;
        if (!StringUtils.isBlank(crNum)&&tnId>0&&gId>0) {
            Integer classRoomNum = Integer.valueOf(crNum),
                    dNum=Integer.valueOf(dayNum);
            result = exiClassRoomService.addClassRoom(tnId, gId, classRoomNum,dNum);
        }
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    @RequestMapping(value = "/classRoomStting/modify/{tnId}/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Map modifyClassRoomSetting(@PathVariable Integer tnId,@PathVariable Integer cid,HttpServletRequest request) {
        String maxNum = request.getParameter("maxNum");

        boolean result = false;

        if (!StringUtils.isBlank(maxNum)) {
            Integer mn = Integer.valueOf(maxNum);
            result = exiClassRoomService.updateClassRoomSetting(mn, cid, tnId);
        }

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }
    @RequestMapping(value = "/classRoomStting/{tnId}",method = RequestMethod.POST)
    @ResponseBody
    public Map getClassRoomSetting(@PathVariable Integer tnId) {


        Map resultMap = new HashMap();
        ClassRoomSetting classRoomSetting=exiClassRoomService.getClassRoomSetting(tnId);
        resultMap.put("result",classRoomSetting);
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
                num = request.getParameter("num"),
                dayNum = request.getParameter("dayNum");

        boolean result = false;

        if (!StringUtils.isBlank(gid) && !StringUtils.isBlank(num)) {

            Integer g = Integer.valueOf(gid),
                    n = Integer.valueOf(num),
                    d=Integer.valueOf(dayNum);

            result = exiClassRoomService.updateClassRoom(n,d, g, cid);

        }

        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 教室排序
     * @param tnId
     * @param ids
     * @return
     */
    @RequestMapping(value = "/sort/classRoom/{tnId}/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public Map sortClassRoom(@PathVariable Integer tnId,@PathVariable String ids){
        boolean result = exiClassRoomService.sortRoomOrderUpdate(tnId,ids);
        Map map = new HashMap();
        map.put("result", result);
        return map;
    }


    /**
     * 删除教室
     * @param ids 教室标识
     * @return
     */
    @RequestMapping(value = "/classRoom/delete/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public Map deleteClassRoom(@PathVariable String ids) {
        boolean result = exiClassRoomService.removeClassRoom(ids);
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
    public Map addEnrollingRatio(@RequestBody Request request) {

        EnrollingRatio enrollingRatio =JSON.parseObject(request.getData().get("EnrollingRatioObj").toString(),EnrollingRatio.class);
        boolean result = iexEnrollingRatioService.addEnrollingRatio(enrollingRatio);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 查询升学率
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/get/enrollingRatio/{tnId}",method = RequestMethod.GET)
    @ResponseBody
    public Map getEnrollingRatio(@PathVariable Integer tnId) {
        Map map = new HashMap();
        map.put("tnId", tnId);
        List<EnrollingRatio> enrollingRatios = iEnrollingRatioService.queryList(map, "ratio_order", "asc");
        Map resultMap = new HashMap();
        resultMap.put("result", enrollingRatios);
        return resultMap;
    }
    /**
     * 管理租户自定义表头
     * @param type
     * @param tnId
     * @param request
     * @return
     */
    @RequestMapping(value = "/import/{type}/{tnId}", method = RequestMethod.POST)
    @ResponseBody
    public Map addTenantCustomConfig(@PathVariable  String type, @PathVariable  Integer tnId, HttpServletRequest request) {
        final String ids = request.getParameter("ids");


//         String exMsg=transactionTemplate.execute(new TransactionCallback<String>() {
//            @Override
//            public String doInTransaction(TransactionStatus status) {
//                try {
        String exMsg = exiTenantConfigInstanceService.createColumn(type, ids, tnId);
//                } catch (Exception ex) {
//                    status.setRollbackOnly();
//                    return "FAIL";
//                }
//            }
//        });
        Map resultMap = new HashMap();
        resultMap.put("result", exMsg);
        return resultMap;
    }

    /**
     * 升学率排序
     * @param ids
     * @return
     */
    @RequestMapping(value = "/sort/enrollingRatio/{tnId}/{ids}",method = RequestMethod.POST)
    @ResponseBody
    public Map sortEnrollingRatio(@PathVariable  Integer tnId,@PathVariable String ids) {
        boolean result = iexEnrollingRatioService.sortRatioUpdate(tnId,ids);
        Map map = new HashMap();
        map.put("result", result);
        return map;
    }

//    /**
//     * 事务测试
//     * @return
//     */
//    @RequestMapping(value = "/tran/import",method = RequestMethod.GET)
//    @ResponseBody
//    public Map tranTest() {
//        String resul = transactionTemplate.execute(new TransactionCallback<String>() {
//            @Override
//            public String doInTransaction(TransactionStatus status) {
//                try {
//                    Map addMap = new HashMap();
//                    addMap.put("tableName", "saas_1_class_excel");
//                    addMap.put("columnName", "class_name");
//                    addMap.put("columnType", "VARCHAR(12)");
//                    exiTenantConfigInstanceService.addColumn(addMap);
//                    throw new RuntimeException("runtime e");
////                    return "SUCCESS";
//                } catch (Exception ex) {
//                    status.setRollbackOnly();
//                    return "FAIL";
//                }
//            }
//        });
//        Map resultMap = new HashMap();
//        resultMap.put("result", resul);
//        return resultMap;
//    }

    /**
     * 删除租户表头
     *
     * @param ids 表头ID
     * @return
     */
    @RequestMapping(value = "/tenant/remove/{type}/{tnId}/{ids}", method = RequestMethod.POST)
    @ResponseBody
    public Map removeTeantConfigs(@PathVariable String type,
                                  @PathVariable Integer tnId,
                                  @PathVariable String ids) {
        String result = exiTenantConfigInstanceService.removeColumn(type, ids,tnId);
        Map resultMap = new HashMap();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 更新升学率
     * @return
     */
    @RequestMapping(value = "/enrollingRatio/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map updateEnrollingRatio(@RequestBody Request request){
        EnrollingRatio enrollingRatio =JSON.parseObject(request.getData().get("EnrollingRatioObj").toString(),EnrollingRatio.class);
        boolean result = iexEnrollingRatioService.updateEnrollingRatio(enrollingRatio);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }


    /**
     * 新增租户自定义表头数据
     * @return
     */
    @RequestMapping(value = "/teant/custom/data/add",method = RequestMethod.POST)
    @ResponseBody
    public Map addTeantCustom(@RequestBody Request request) {
        Map resultMap = new HashMap();
        Map params = request.getData();
        List<TeantCustom> teantCustoms = (List<TeantCustom>) params.get("teantCustomList");
        String type = params.get("type").toString();
        Integer tnId = request.getDataInteger("tnId");

//        Map map = ParamsUtils.getTeantCustomDataValue(teantCustoms, type);
//
        String tableName = ParamsUtils.combinationTableName(type, tnId);
//
//        Map repeat = iexTenantCustomService.selectExistByCloumn(tableName, type, map.get("value1").toString(), map.get("value2").toString());
//
//        List<String> removeIds = (List<String>) repeat.get("repeat");
        List<String> removeIds=reantCustomRepeat(type,tableName,teantCustoms);
        if (removeIds != null && removeIds.size() > 0)
            exiTenantConfigInstanceService.removeTenantCustomList(tableName, removeIds);

        boolean result = iexTenantCustomService.addTeantCustom(type, tnId, teantCustoms);

        resultMap.put("result", (result ? (removeIds != null && removeIds.size() > 0) ? "存在重复数据，已经覆盖更新" : "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 更新租户自定义表头数据
     * @return
     */
    @RequestMapping(value = "/teant/custom/data/modify",method = RequestMethod.POST)
    @ResponseBody
    public Map modifyTeantCustom(@RequestBody Request request) {
        Map resultMap = new HashMap();
        Map params=request.getData();
        List<TeantCustom> teantCustoms=(List<TeantCustom>) params.get("teantCustomList");
        String type=params.get("type").toString();
        Integer tnId=request.getDataInteger("tnId"),
         pri=request.getDataInteger("pri");
        String tableName = ParamsUtils.combinationTableName(type, tnId);
        List<String> removeIds=reantCustomRepeat(type, tableName, teantCustoms);
        if (removeIds != null && removeIds.size() > 0) {
            resultMap.put("result", "存在重复数据,请修正!");
        }else {
            boolean result = iexTenantCustomService.modifyTeantCustom(type, tnId, pri, teantCustoms);
            resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        }
        return resultMap;
    }

    /**
     * 删除租户自定义表头数据
     * @param type 模块名
     * @param tnId 租户ID
     * @param ids  主键
     * @return
     */
    @RequestMapping(value = "/{type}/{tnId}/{ids}/remove",method = RequestMethod.POST)
    @ResponseBody
    public Map removeTenantCustom(@PathVariable String type,
                                  @PathVariable Integer tnId,
                                  @PathVariable String ids) {
        boolean result = iexTenantCustomService.removeTenantCustom(type, tnId, ids);
        Map resultMap = new HashMap();
        resultMap.put("result", (result ? "SUCCESS" : "FAIL"));
        return resultMap;
    }

    /**
     * 获取租户自定义数据
     * @param type
     * @param tnId
     * @return
     */
    @RequestMapping(value = "/{type}/{tnId}/getTenantCustomData",method = RequestMethod.GET)
    @ResponseBody
    public Map  getTenantCustomData(@PathVariable String type,
                                    @PathVariable Integer tnId,HttpServletRequest _req) {
        String grade = _req.getParameter("g"),
                star = _req.getParameter("s"),
                row = _req.getParameter("r");


        Integer s = (star == null) ? null : Integer.valueOf(star),
                r=(row==null)?null:Integer.valueOf(row);
        Map resultMap = new HashMap();
        List<LinkedHashMap<String, Object>> tenantCustom = iexTenantCustomService.getTenantCustom(type, tnId, grade, s,r);
        resultMap.put("result", tenantCustom);
        Integer count = iexTenantCustomService.getTenantCustomCount(type, tnId, grade);
        resultMap.put("count", count);
        return resultMap;
    }

    /**
     * 获取班级类型
     * @param type
     * @param tnId
     * @param grade
     * @return
     */
    @RequestMapping(value = "/get/classType/{type}/{tnId}/{grade}",method =RequestMethod.GET )
    @ResponseBody
    public Map getClassType(@PathVariable String type,
                            @PathVariable Integer tnId,
                            @PathVariable String grade) {
        List<ClassView> classViews = exiTenantConfigInstanceService.selectClassTypeByGrade(type, tnId, grade);
        Map resultMap = new HashMap();
        resultMap.put("result", classViews);
        return resultMap;
    }

    /**
     * 获取班级类型
     * @param type
     * @param tnId
     * @param grade
     * @return
     */
    @RequestMapping(value = "/get/className/{type}/{tnId}/{grade}/{classType}",method =RequestMethod.GET )
    @ResponseBody
    public Map getClassName(@PathVariable String type,
                            @PathVariable Integer tnId,
                            @PathVariable String grade,
                            @PathVariable String classType) {
        List<ClassView> classViews = exiTenantConfigInstanceService.selectClassNameByGradeAndType(type, tnId, grade,classType);
        Map resultMap = new HashMap();
        resultMap.put("result", classViews);
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
//        List<LinkedHashMap<String, Object>> tenantCustoms=iexTenantCustomService.getTenantCustom(type, tnId,null,null,null);
        List<Map<Integer,Object>> maps=iexTenantCustomService.isExcelAddSelect(type,tnId, columnNames);



        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
//            ExcelUtils.createWorkBook(columnNames,tenantCustoms,maps).write(os);
            ExcelUtils.createWorkBook(columnNames, maps).write(os);
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
            if (out !=null)
                out.close();
            LOGGER.info("===============导出租户excel模板 E================");
        }
        return null;
    }


    private List<String> reantCustomRepeat(String type,String tableName,List<TeantCustom> teantCustoms) {
        Map map = ParamsUtils.getTeantCustomDataValue(teantCustoms, type);

        Map repeat = iexTenantCustomService.selectExistByCloumn(tableName, type, map.get("value1").toString(), map.get("value2").toString());

        List<String> removeIds = (List<String>) repeat.get("repeat");

        if (removeIds != null && removeIds.size() > 0)
            return removeIds;
        return null;
    }
}
