package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.*;
import cn.thinkjoy.saas.dao.bussiness.*;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.domain.bussiness.ClassView;
import cn.thinkjoy.saas.domain.bussiness.SyncClass;
import cn.thinkjoy.saas.domain.bussiness.SyncCourse;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.common.ConvertUtil;
import cn.thinkjoy.saas.service.common.EnumUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import cn.thinkjoy.saas.service.common.ReadExcel;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by douzy on 16/10/13.
 */
@Service("EXITenantConfigInstanceServiceImpl")
public class EXITenantConfigInstanceServiceImpl extends AbstractPageService<IBaseDAO<TenantConfigInstance>, TenantConfigInstance> implements EXITenantConfigInstanceService<IBaseDAO<TenantConfigInstance>,TenantConfigInstance> {

    private static final Logger LOGGER= LoggerFactory.getLogger(EXITenantConfigInstanceServiceImpl.class);

    @Autowired
    EXITenantConfigInstanceDAO exiTenantConfigInstanceDAO;

    @Resource
    ITenantConfigInstanceDAO iTenantConfigInstanceDAO;
    @Resource
    IConfigurationDAO iConfigurationDAO;
    @Resource
    ITenantDAO iTenantDAO;
    @Resource
    IEXJwTeacherBaseInfoDAO iexJwTeacherBaseInfoDAO;
    @Resource
    IJwTeacherBaseInfoDAO iJwTeacherBaseInfoDAO;
    @Resource
    IEXTeantCustomDAO iexTeantCustomDAO;
    @Resource
    IEXCourseBaseInfoDAO iexCourseBaseInfoDAO;
    @Resource
    IJwCourseBaseInfoDAO iJwCourseBaseInfoDAO;
    @Resource
    IJwCourseDAO iJwCourseDAO;
    @Resource
    IJwTeacherDAO iJwTeacherDAO;

    @Resource
    IEXClassBaseInfoDAO iexClassBaseInfoDAO;
    @Resource
    IJwClassBaseInfoDAO jwClassBaseInfoDAO;

    @Resource
    IJwBaseConRuleDAO iJwBaseConRuleDAO;

    @Resource
    IJwBaseDayRuleDAO iJwBaseDayRuleDAO;

    @Resource
    IJwBaseJaqpRuleDAO iJwBaseJaqpRuleDAO;

    @Resource
    IJwBaseWeekRuleDAO iJwBaseWeekRuleDAO;


    @Override
    public IBaseDAO<TenantConfigInstance> getDao() {
        return exiTenantConfigInstanceDAO;
    }

    /**
     * 租户是否已经选择表头
     * @param tnId
     * @return
     */
    public boolean isExistConfigDataByTnId(String type,Integer tnId) {
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("domain",type);
        TenantConfigInstance tenantConfigInstance = iTenantConfigInstanceDAO.queryOne(map, "id", "asc");
        return tenantConfigInstance == null ? false : true;
    }

    /**
     * 批量删除租户已选表头  by 租户ID
     * @param tnId
     * @return
     */
    public boolean removeConfigDataByTnId(String type,Integer tnId) {
        Map map = new HashMap();
        map.put("tnId", tnId);
        map.put("domain",type);
        Integer result = iTenantConfigInstanceDAO.deleteByCondition(map);
        return result > 0 ? true : false;
    }

    /**
     * 逐条删除租户表头  by
     * @param id  表头ID
     * @return
     */
    @Override
    public boolean removeConfigDataById(Integer id) {
        Map map = new HashMap();
        map.put("id", id);
        Integer result = iTenantConfigInstanceDAO.deleteByCondition(map);
        return result > 0 ? true : false;
    }

    /**
     * 批量删除租户表头
     *
     * @param ids 需要删除的表头ID串  -号分隔
     */
    @Override
    public boolean removeTeantConfigs(String type,Integer tnId,String ids) {
        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;

        if(isExsitsTeantCustomTable(type,tnId)) {
            LOGGER.info("该字段已经使用，请勿移除!");
            return false;
        }
        return exiTenantConfigInstanceDAO.removeTeantConfigs(idsList) > 0 ? true : false;
    }

    /**
     * 租户表头排序
     * @param type class:班级 teacher:教师
     * @param ids 需要调换的两个表头ID
     * @return
     */
    @Override
    public boolean sortTeantConfig(String type,String ids) {
        LOGGER.info("===============租户表头排序 S==============");
        LOGGER.info("type:" + type);
        LOGGER.info("ids:" + ids);

        boolean result = false;

        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;

        List<TenantConfigInstance> tenantConfigInstances = new ArrayList<TenantConfigInstance>();

        for (int i = 0; i < idsList.size(); i++) {
            Map map = new HashMap();
            map.put("id", idsList.get(i));
            map.put("domain", type);
            TenantConfigInstance tenantConfigInstance = iTenantConfigInstanceDAO.queryOne(map, "id", "asc");
            if (tenantConfigInstance == null)
                return false;
            tenantConfigInstance.setConfigOrder(i);
            tenantConfigInstances.add(tenantConfigInstance);
        }
//
//        List<TenantConfigInstance> sortResultTenantConfigInstances = new ArrayList<TenantConfigInstance>();
//        TenantConfigInstance tenantConfigInstanceStart = tenantConfigInstances.get(0);
//        TenantConfigInstance tenantConfigInstanceEnd = tenantConfigInstances.get(1);
//        //存储一个order
//        Integer temp = tenantConfigInstanceStart.getConfigOrder();
//        //调换另一个对象的order
//        tenantConfigInstanceStart.setConfigOrder(tenantConfigInstanceEnd.getConfigOrder());
//        tenantConfigInstanceStart.setModifyDate(System.currentTimeMillis());
//        //调换temporder
//        tenantConfigInstanceEnd.setConfigOrder(temp);
//        tenantConfigInstanceEnd.setModifyDate(System.currentTimeMillis());
//
//        sortResultTenantConfigInstances.add(tenantConfigInstanceStart);
//        sortResultTenantConfigInstances.add(tenantConfigInstanceEnd);

        Integer sortResult = exiTenantConfigInstanceDAO.sortConfigUpdate(tenantConfigInstances);
        result = sortResult > 0 ? true : false;

        LOGGER.info("===============租户表头排序 E==============");

        return result;
    }

    /**
     * 查询当前租户&当前模块下的表头信息
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    @Override
    public List<TenantConfigInstanceView> getTenantConfigListByTnIdAndType(String type, Integer tnId) {

        if (StringUtils.isBlank(type) || tnId <= 0)
            return null;

        Map map = new HashMap();
        map.put("domain", type);
        map.put("tnId", tnId);
        List<TenantConfigInstanceView> tenantConfigInstances = exiTenantConfigInstanceDAO.selectTeanConfigList(map);

        return tenantConfigInstances;
    }

    /**
     * 获取当前租户表头 数组 - 用于导出表头excel
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    @Override
    public String[] getTenantConfigListArrByTnIdAndType(String type, Integer tnId) {
        List<TenantConfigInstanceView> tenantConfigInstanceViews = this.getTenantConfigListByTnIdAndType(type, tnId);
        String[] configArr = new String[tenantConfigInstanceViews.size()];
        for (int i = 0; i < tenantConfigInstanceViews.size(); i++) {
            TenantConfigInstanceView tenantConfigInstanceView = tenantConfigInstanceViews.get(i);
            configArr[i] = tenantConfigInstanceView.getName();
            LOGGER.info("表头[" + i + "]:" + tenantConfigInstanceView.getName());
        }
        return configArr;
    }
    /**
     * 生成租户自选表头
     * @param ids  表头id集
     * @param tnId 租户
     *
     * @return
     *
     * 0:成功
     * -1:失败,系统错误
     * 1:参数错误
     * 2:该租户不存在
     * 3:选项集校验失败
     */
    @Override
    public String createConfig(String type,String ids,Integer tnId) {
        String result = EnumUtil.ErrorCode.getDesc(EnumUtil.GLOBAL_SYSTEMERROR);

        LOGGER.info("===============生成租户自选表头 S==============");
        LOGGER.info("ids:" + ids);
        LOGGER.info("tnId:" + tnId);
        List<String> idsList = ParamsUtils.idsSplit(ids);

        if (tnId <= 0 || idsList == null) {
            LOGGER.info("result:1[参数错误]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_PARAMSERROR);
        }
        if(isExsitsTeantCustomTable(type,tnId)) {
            LOGGER.info("该租户已上传模板,表头无法修改!");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_TEANTCUSTOM_EXCEL);
        }

        if (isExistConfigDataByTnId(type,tnId)) {
            LOGGER.info("tnId:" + tnId + "=[该租户存在历史表头数据.]");
            boolean removeResult = removeConfigDataByTnId(type,tnId);
            LOGGER.info("removeResult:" + removeResult);

            if (!removeResult)
                return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_REMOVEHISTORYERROR);
        }

        Tenant tenant = iTenantDAO.findOne("id", tnId, "id", "asc");

        if (tenant == null) {
            LOGGER.info("result:2[该租户不存在]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_TEANTNULL);
        }

        List<TenantConfigInstance> tenantConfigInstances = new ArrayList<TenantConfigInstance>();

        boolean isNext = true;
        for (String configId : idsList) {
            Map map = new HashMap();
            map.put("id", configId);
            map.put("domain",type);
            Configuration configuration = iConfigurationDAO.queryOne(map, "id", "asc");
            if (configuration == null) {
                LOGGER.info("[ERROR]-configId:" + configId);
                isNext = false;
                break;
            }
            TenantConfigInstance tenantConfigInstance = configStructure(configuration, tnId);

            tenantConfigInstances.add(tenantConfigInstance);
        }
        LOGGER.info("isNext:[配置选项集校验结果]" + isNext);

        if (isNext) {

            Integer addResult = exiTenantConfigInstanceDAO.addConfigs(tenantConfigInstances);

            result = addResult > 0 ? EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_SUCCESS) : result;

        } else {
            LOGGER.info("result:[选项集校验失败]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_CONFIGSVALIDERROR);
        }

        LOGGER.info("===============生成租户自选表头 E==============");
        return result;
    }

    @Override
    public Configuration queryConfigurationOne(Map map) {
        return iConfigurationDAO.queryOne(map, "id", "asc");
    }

    @Override
    public boolean existColumn(Map map) {
        return exiTenantConfigInstanceDAO.existColumn(map)>0;
    }
    @Override
    public Integer addConfigs(List<TenantConfigInstance> tenantConfigInstances) {
        return exiTenantConfigInstanceDAO.addConfigs(tenantConfigInstances);
    }
    @Override
    public void addColumn(Map map){
        exiTenantConfigInstanceDAO.addColumn(map);
    }
    @Override
    public Integer teantConfigDeleteByCondition(Map map){
        return iTenantConfigInstanceDAO.deleteByCondition(map);
    }
    @Override
    public void removeColumn(Map map){
        exiTenantConfigInstanceDAO.removeColumn(map);
    }
    /**
     *  新增表头
     * @param type
     * @param ids
     * @param tnId
     * @return
     */
    @Override
    public String createColumn( String type,String ids,  Integer tnId) {


        LOGGER.info("===============新增租户自选表头 S==============");
        LOGGER.info("ids:" + ids);
        LOGGER.info("tnId:" + tnId);

         List<String> idsList = ParamsUtils.idsSplit(ids);
        if (tnId <= 0 || idsList == null) {
            LOGGER.info("result:1[参数错误]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_PARAMSERROR);
        }
         String tableName = ParamsUtils.combinationTableName(type, tnId);


        for (String configId : idsList) {

            Map map = new HashMap();
            map.put("id", configId);
            map.put("domain", type);
            Configuration configuration = queryConfigurationOne(map);

            Map paramsMap = new HashMap();
            paramsMap.put("tableName", tableName);
            paramsMap.put("columnName", configuration.getEnName());
            boolean isExist = existColumn(paramsMap);

            if (isExist)
                continue;

            List<TenantConfigInstance> tenantConfigInstances = new ArrayList<TenantConfigInstance>();
            TenantConfigInstance tenantConfigInstance = configStructure(configuration, tnId);
            tenantConfigInstances.add(tenantConfigInstance);

            addConfigs(tenantConfigInstances);

            Map addMap = new HashMap();
            addMap.put("tableName", tableName);
            addMap.put("columnName", configuration.getEnName());
            addMap.put("columnType", configuration.getMetaType());
            addColumn(addMap);
        }

//        System.out.print(1 / 0);

                isExsitsColumn(idsList,tableName,type,tnId);


        LOGGER.info("===============新增租户自选表头 E==============");
        return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_SUCCESS);
    }

    /**
     * 删除表头
     * @param type
     * @param ids
     * @param tnId
     * @return
     */
    @Override
    public String removeColumn(String type,String ids,Integer tnId) {
        LOGGER.info("===============删除表头 S==============");
        LOGGER.info("ids:" + ids);
        LOGGER.info("tnId:" + tnId);

        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (tnId <= 0 || idsList == null) {
            LOGGER.info("result:1[参数错误]");
            return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_PARAMSERROR);
        }

        String tableName = ParamsUtils.combinationTableName(type, tnId);


        for (String configId : idsList) {
            Map map = new HashMap();
            map.put("id", configId);
            map.put("domain", type);
            TenantConfigInstance tenantConfigInstance = iTenantConfigInstanceDAO.queryOne(map, "id", "asc");

            Map configMap = new HashMap();
            configMap.put("id", tenantConfigInstance.getConfigKey());
            configMap.put("domain", type);
            Configuration configuration = iConfigurationDAO.queryOne(configMap, "id", "asc");


            Map paramsMap = new HashMap();
            paramsMap.put("tableName", tableName);
            paramsMap.put("columnName", configuration.getEnName());
            exiTenantConfigInstanceDAO.removeColumn(paramsMap);

        }
        Integer result = exiTenantConfigInstanceDAO.removeTeantConfigs(idsList);
        LOGGER.info("===============删除表头 E==============");

        return EnumUtil.ErrorCode.getDesc(EnumUtil.IMPORTCONFIG_SUCCESS);
    }


    /**
     * 创建租户动态表
     * @param type class:班级  teacher:教师
     * @param tnId 租户ID
     * @return
     */
    @Override
    public String createTenantCombinationTable(String type,Integer tnId) {
        LOGGER.info("===========创建租户动态表 S===========");
        LOGGER.info("type:" + type);
        LOGGER.info("tnId:" + tnId);
        if (StringUtils.isBlank(type) || tnId <= 0)
            return null;

        String tableName = ParamsUtils.combinationTableName(type, tnId);
        LOGGER.info("tableName:" + tableName);
        if (StringUtils.isBlank(tableName))
            return null;
        boolean isValid = this.tableNameValid(tableName);
        LOGGER.info("isValid:" + isValid);
        if (!isValid)
            return null;

        try {
            List<TenantConfigInstanceView> tenantConfigInstanceViews = this.getTenantConfigListByTnIdAndType(type, tnId);
            List<Configuration> configurations = new ArrayList<Configuration>();
            for (TenantConfigInstanceView tenantConfigInstanceView : tenantConfigInstanceViews) {
                Map map = new HashMap();
                map.put("domain", tenantConfigInstanceView.getDomain());
                map.put("id", tenantConfigInstanceView.getConfigKey());
                Configuration configuration = iConfigurationDAO.queryOne(map, "id", "asc");
                configuration.setConfigOrder(tenantConfigInstanceView.getConfigOrder());
                configurations.add(configuration);
            }

            Integer createResult = exiTenantConfigInstanceDAO.createConfigTable(tableName, configurations);

            LOGGER.info("动态表创建结果:" + createResult);

            return tableName;

        } catch (Exception ex) {
            return null;
        } finally {
            LOGGER.info("===========创建租户动态表 E===========");
        }

    }

    /**
     * 解析excel 且将对应的值插入动态表
     * @param type
     * @param tnId
     * @return
     */
    @Override
    public String  uploadExcel(String type,Integer tnId,String excelPath) {
        LOGGER.info("===========解析excel S===========");
        LOGGER.info("type:" + type);
        LOGGER.info("tnId:" + tnId);
        ReadExcel readExcel = new ReadExcel();
        Map map = new HashMap();
        map.put("domain", type);
        map.put("tnId", tnId);
        List<TenantConfigInstanceView> tenantConfigInstances = exiTenantConfigInstanceDAO.selectTeanConfigList(map);

        if(tenantConfigInstances==null||tenantConfigInstances.size()<=0)
            return "系统错误";

        Integer columnLen=tenantConfigInstances.size();

        List<LinkedHashMap<String, String>> configTeantComList = readExcel.readExcelFile(excelPath,columnLen);
        if (configTeantComList == null||configTeantComList.size()<=0)
            return "输入的数据不完整，请完善数据后再上传";
        LOGGER.info("excel序列化 总数:" + configTeantComList.size());
        List<TenantConfigInstanceView> tenantConfigInstanceViews = this.getTenantConfigListByTnIdAndType(type, tnId);
        if (tenantConfigInstanceViews == null)
            return "系统错误";

        String excelValid = ParamsUtils.excelValueValid(configTeantComList, tenantConfigInstanceViews);




        if(type.equals("student")&&excelValid.equals("SUCCESS"))
            excelValid =ParamsUtils.repeatStudentNo(configTeantComList);


        String reuslt = "系统错误";

        if (excelValid.equals("SUCCESS")) {

            String tableName = this.createTenantCombinationTable(type, tnId);
            LOGGER.info("tableName:" + tableName);

            if (StringUtils.isBlank(tableName))
                return "系统错误";

            boolean repeat=isRepeat(tableName,type,configTeantComList, tenantConfigInstanceViews);

            Integer insertResult = exiTenantConfigInstanceDAO.insertTenantConfigCom(tableName, tenantConfigInstanceViews, configTeantComList);
            if (insertResult > 0) {
                reuslt =repeat?"存在重复数据，已经覆盖更新": "SUCCESS";
                syncProcedureData(type, tnId);

            }
        } else
            reuslt = excelValid;
        LOGGER.info("===========解析excel E===========");
        return reuslt;
    }

    public boolean isRepeat(String tableName,String type,List<LinkedHashMap<String, String>> excelValues,List<TenantConfigInstanceView> tenantConfigInstanceViews) {
        boolean result = false;

        if (excelValues == null)
            return result;

        Integer excelLen = excelValues.size();

        for (int x = 0; x < excelLen; x++) {

            LinkedHashMap<String, String> rowsMap = excelValues.get(x);

            Iterator iter = rowsMap.entrySet().iterator();

            int y = 0;
            String key1=null,key2=null,value1=null,value2=null;
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = entry.getKey().toString();
                String val = entry.getValue().toString();

                TenantConfigInstanceView tenantConfigInstanceView = tenantConfigInstanceViews.get(y);
                String configurationId = tenantConfigInstanceView.getConfigKey();


                Map searchMap = new HashMap();
                searchMap.put("id", configurationId);
                Configuration configuration = iConfigurationDAO.queryOne(searchMap, "id", "asc");


                switch (type) {
                    case "class":
                        if (configuration.getEnName().equals("class_grade")) {
                            key1 = "class_grade";
                            value1=val;
                        }
                        if (configuration.getEnName().equals("class_name")) {
                            key2 = "class_name";
                            value2=val;
                        }
                        break;
                    case "teacher":
                        if (configuration.getEnName().equals("teacher_name")) {
                            key1 = "teacher_name";
                            value1=val;
                        }
                        if (configuration.getEnName().equals("teacher_major_type")) {
                            key2 = "teacher_major_type";
                            value2=val;
                        }
                        break;
                    case "student":
                        if (configuration.getEnName().equals("student_no")) {
                            key1 = "student_no";
                            value1=val;
                        }
                        if (configuration.getEnName().equals("student_name")) {
                            key2 = "student_name";
                            value2=val;
                        }
                        break;
                }

                if(!StringUtils.isBlank(value1)&&!StringUtils.isBlank(value2)) {
                    Map map = new HashMap();
                    map.put("tableName",tableName);
                    map.put("key1",key1);
                    map.put("key2",key2);
                    map.put("value1",value1);
                    map.put("value2",value2);
                    Integer count = iexTeantCustomDAO.selectExistByCloumn(map);
                    if (count > 0)
                        return true;
                }


                y++;
            }
        }

        return result;
    }
    /**
     * 流程数据同步
     * @param type
     * @return
     */
    @Override
    public void syncProcedureData(String type,Integer tnId) {
        String tableName = ParamsUtils.combinationTableName(type, tnId);
        Map map=new HashMap();
        map.put("tableName",tableName);
        List<LinkedHashMap<String,Object>> linkedHashMaps=iexTeantCustomDAO.getTenantCustom(map);

        Map removeMap=new HashMap();
        removeMap.put("tnId",tnId);
        switch (type){
            case "teacher":
                if(linkedHashMaps!=null&& linkedHashMaps.size()>0) {

                    List<JwTeacherBaseInfo> jwTeacherBaseInfos=new ArrayList<>();
                    for(LinkedHashMap<String,Object> linkedHashMap:linkedHashMaps) {
                        JwTeacherBaseInfo jwTeacherBaseInfo = new JwTeacherBaseInfo();
                        jwTeacherBaseInfo.setTnId(tnId);
                        jwTeacherBaseInfo.setGrade(ConvertUtil.converGrade(linkedHashMap.get("teacher_grade").toString()));
                        jwTeacherBaseInfo.setTeacherName(linkedHashMap.get("teacher_name").toString());
                        jwTeacherBaseInfo.setTeacherClass(linkedHashMap.get("teacher_class").toString());
                        jwTeacherBaseInfo.setTeacherCourse(linkedHashMap.get("teacher_major_type").toString());
                        jwTeacherBaseInfos.add(jwTeacherBaseInfo);
                    }
                    iJwTeacherBaseInfoDAO.deleteByCondition(removeMap);
                    // 清除教师排课信息
                    iJwTeacherDAO.deleteByProperty("tn_id",tnId);
                    // 清除教案齐平规则信息
                    iJwBaseJaqpRuleDAO.deleteByProperty("tn_id",tnId);
                    // 清除周任课规则
                    iJwBaseWeekRuleDAO.deleteByProperty("tn_id",tnId);
                    // 清除日任课规则
                    iJwBaseDayRuleDAO.deleteByProperty("tn_id",tnId);
                    // 清除连上规则
                    iJwBaseConRuleDAO.deleteByProperty("tn_id",tnId);

                    iexJwTeacherBaseInfoDAO.syncTeacherInfo(jwTeacherBaseInfos);
                }
                break;
            case "student":
                if(linkedHashMaps!=null&& linkedHashMaps.size()>0) {
                    //同步课程
                    List<SyncCourse> syncCourses = iexTeantCustomDAO.selectCourseGroup(map);
                    if (syncCourses != null) {
                        List<JwCourseBaseInfo> jwCourseBaseInfos = new ArrayList<>();
                        List<Integer> grades = Lists.newArrayList();
                        for (SyncCourse syncCourse : syncCourses) {
                            JwCourseBaseInfo jwCourseBaseInfo = new JwCourseBaseInfo();
                            jwCourseBaseInfo.setTnId(tnId);
                            jwCourseBaseInfo.setCourseName(syncCourse.getMajor());
                            Integer grade = ConvertUtil.converGrade(syncCourse.getGrade());
                            jwCourseBaseInfo.setGrade(grade.toString());
                            jwCourseBaseInfo.setCourseType(2);
                            jwCourseBaseInfos.add(jwCourseBaseInfo);

                            if(!grades.contains(grade)){
                                grades.add(grade);
                            }

                        }
                        iJwCourseBaseInfoDAO.deleteByCondition(removeMap);
                        iJwCourseDAO.deleteByProperty("tn_id",tnId);
                        for(Integer grade : grades){
                            jwCourseBaseInfos.addAll(convertCourseList(tnId,grade.toString()));
                        }
                        iexCourseBaseInfoDAO.syncCourseInfo(jwCourseBaseInfos);
                    }
                    List<SyncClass> syncClasses = iexTeantCustomDAO.selectClassGroup(map);
                    List<JwClassBaseInfo> dayJwClassBaseInfos = new ArrayList<>();
                    //同步走读、行政班级
                    if (syncClasses != null) {

                        for (SyncClass syncClass : syncClasses) {
                            JwClassBaseInfo jwClassBaseInfo = new JwClassBaseInfo();
                            jwClassBaseInfo.setTnId(tnId);
                            jwClassBaseInfo.setClassName(syncClass.getMajorClass());
                            jwClassBaseInfo.setClassType(2);
                            jwClassBaseInfo.setGrade(ConvertUtil.converGrade(syncClass.getGrade()));
                            dayJwClassBaseInfos.add(jwClassBaseInfo);
                        }

                    }
                    List<SyncClass> syncExecutiveClasses = iexTeantCustomDAO.selectExecutiveClassGroup(map);
                    List<JwClassBaseInfo> executiveJwClassBaseInfos = new ArrayList<>();
                    if (syncExecutiveClasses != null) {
                        for (SyncClass syncClass : syncExecutiveClasses) {
                            JwClassBaseInfo jwClassBaseInfo = new JwClassBaseInfo();
                            jwClassBaseInfo.setTnId(tnId);
                            jwClassBaseInfo.setClassName(syncClass.getMajorClass());
                            jwClassBaseInfo.setClassType(1);
                            jwClassBaseInfo.setGrade(ConvertUtil.converGrade(syncClass.getGrade()));
                            executiveJwClassBaseInfos.add(jwClassBaseInfo);
                        }
                    }
                    if (dayJwClassBaseInfos != null || executiveJwClassBaseInfos != null)
                        jwClassBaseInfoDAO.deleteByCondition(removeMap);

                    if (dayJwClassBaseInfos != null && dayJwClassBaseInfos.size() > 0)
                        iexClassBaseInfoDAO.syncClassInfo(dayJwClassBaseInfos);

                    if (executiveJwClassBaseInfos != null && executiveJwClassBaseInfos.size() > 0)
                        iexClassBaseInfoDAO.syncClassInfo(executiveJwClassBaseInfos);

                }
                break;
        }
    }

    /**
     * 组装基本课程信息(语,数,外)
     *
     * @param tnId
     * @param grade
     * @return
     */
    private List<JwCourseBaseInfo> convertCourseList(int tnId,String grade){
        List<JwCourseBaseInfo> infos = Lists.newArrayList();
        infos.add(convertCourse(tnId,grade,"语文"));
        infos.add(convertCourse(tnId,grade,"数学"));
        infos.add(convertCourse(tnId,grade,"英语"));
        return infos;
    }

    /**
     * 组装单个课程信息
     *
     * @param tnId
     * @param grade
     * @param courseName
     * @return
     */
    private JwCourseBaseInfo convertCourse(int tnId,String grade,String courseName){
        JwCourseBaseInfo info = new JwCourseBaseInfo();
        info.setTnId(tnId);
        info.setCourseName(courseName);
        info.setCourseType(1);
        info.setGrade(grade);
        return info;
    }

    /**
     * 租户动态表名校验  -表级别校验  存在则删除
     * @param tableName 表名
     * @return
     */
    @Override
    public boolean tableNameValid(String tableName) {
        boolean isValid = false;

        Integer tableCount = exiTenantConfigInstanceDAO.existTable(tableName);

        if (tableCount > 0)
             exiTenantConfigInstanceDAO.dropTable(tableName);
        isValid = true;


        return isValid;
    }

    /**
     * 封装租户已选表头对象
     * @param configuration 租户已选配置对象
     * @param tnId 租户ID
     * @return
     */
    public  TenantConfigInstance configStructure(Configuration configuration,Integer tnId) {
        TenantConfigInstance tenantConfigInstance = new TenantConfigInstance();
        tenantConfigInstance.setCheckRule(configuration.getCheckRule());
        tenantConfigInstance.setConfigKey(configuration.getId().toString());
        tenantConfigInstance.setConfigOrder(configuration.getConfigOrder());
        tenantConfigInstance.setTnId(tnId);
        tenantConfigInstance.setDomain(configuration.getDomain());
        tenantConfigInstance.setCreateDate(System.currentTimeMillis());
        tenantConfigInstance.setDataType(configuration.getDataType());
        tenantConfigInstance.setDataUrl(configuration.getDataUrl());
        tenantConfigInstance.setDataValue(configuration.getDataValue());
        tenantConfigInstance.setIsRetain(configuration.getIsRetain());
        tenantConfigInstance.setModifyDate(null);
        return tenantConfigInstance;
    }

    @Override
    public boolean isExsitsTeantCustomTable(String type,Integer tnId){
        String tableName=ParamsUtils.combinationTableName(type,tnId);
        Integer count=exiTenantConfigInstanceDAO.existTable(tableName);
        return (count>0?true:false);
    }

    @Override
    public List<ClassView> selectClassTypeByGrade(String type,Integer tnId,String grade) {
        String tableName = ParamsUtils.combinationTableName(type, tnId);
        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("classGrade", grade);
        return exiTenantConfigInstanceDAO.selectClassTypeByGrade(map);
    }

    @Override
    public List<ClassView> selectClassNameByGradeAndType(String type,Integer tnId,String grade,String classType) {
        String tableName = ParamsUtils.combinationTableName(type, tnId);
        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("classGrade", grade);
        map.put("classType", classType);
        return exiTenantConfigInstanceDAO.selectClassNameByGradeAndType(map);
    }

    public boolean isExsitsColumn(List<String> configIds,String tableName,String type,Integer tnId ) {

        List<String> configViews = new ArrayList<>();

        List<TenantConfigInstanceView> tenantConfigInstanceViews = getTenantConfigListByTnIdAndType(type, tnId);

        for (TenantConfigInstanceView tenantConfigInstanceView : tenantConfigInstanceViews) {
            configViews.add(tenantConfigInstanceView.getConfigKey());
        }
        List<String> diffIds = getDifferent(configIds, configViews);

        for(String configId:diffIds) {
            Map map = new HashMap();
            map.put("configKey", configId);
            map.put("tnId", tnId);
            map.put("domain", type);
            teantConfigDeleteByCondition(map);
            Map conMap = new HashMap();
            conMap.put("id", configId);
            conMap.put("domain", type);
            Configuration configuration = queryConfigurationOne(map);

            Map paramsMap = new HashMap();
            paramsMap.put("tableName", tableName);
            paramsMap.put("columnName", configuration.getEnName());
            removeColumn(paramsMap);
        }

        return false;
    }

    public static List<String> getDifferent(List<String> prelist, List<String> curlist) {
        List<String> diff = new ArrayList<String>();

        Map<String, Integer> map = new HashMap<String, Integer>(curlist.size());
        for (String stu : curlist) {
            map.put(stu, 1);
        }
        for (String stu : prelist) {
            if (map.get(stu) != null) {
                map.put(stu, 2);
                continue;
            }
            diff.add(stu);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;

    }
    /**
     * 查找学号是否重复
     * @return
     */
    public Integer selectCountByStudentNo(String type,Integer tnId,String studentNo) {
        String tableName = ParamsUtils.combinationTableName(type, tnId);
        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("studentNo", studentNo);
        return exiTenantConfigInstanceDAO.selectCountByStudentNo(map);
    }
    public static void main(String[] args) {
        Map<Object,String> map = new HashMap<Object, String>();
        map.put(1,"22");


        System.out.print(map.get(1));

    }
    public boolean selectExistByCloumn(String tableName,String type,String value1,String value2) {

        String key1=null,key2=null;

        switch (type){
            case "class":
                key1="class_grade";
                key2="class_name";
                break;
            case "teacher":
                key1="teacher_name";
                key2="teacher_major_type";
                break;
            case "student":
                key1="student_no";
                key2="student_name";
                break;
        }

        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("key1",key1);
        map.put("key2",key2);
        map.put("value1",value1);
        map.put("value2",value2);

        return iexTeantCustomDAO.selectExistByCloumn(map)>0;
    }

}
