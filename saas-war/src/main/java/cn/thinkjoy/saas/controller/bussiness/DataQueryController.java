package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.restful.apigen.annotation.ApiParam;
import cn.thinkjoy.gk.api.IMajoredApi;
import cn.thinkjoy.gk.api.IUniversityApi;
import cn.thinkjoy.gk.domain.GkAdmissionLine;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.Province;
import cn.thinkjoy.saas.dto.UserInfoDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.IProvinceService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.GkProfessional;
import cn.thinkjoy.zgk.dto.GkProfessionDTO;
import cn.thinkjoy.zgk.remote.IGkProfessionalService;
import cn.thinkjoy.zgk.remote.IUniversityService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangguorong on 16/10/26.
 *
 * 数据查询模块
 */
@Controller
@RequestMapping("/data")
public class DataQueryController {

    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private IUniversityApi iUniversityApi;

    @Autowired
    private IMajoredApi iMajoredApi;

    @Autowired
    private IUniversityService universityService;

    @Autowired
    private IGkProfessionalService gkProfessionalService;

    @Autowired
    private RedisRepository<String, Object> redis;

    /**
     * 获取所有省份
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProvinceList",method = RequestMethod.GET)
    public List<Province> getProvinceList(){

        Object object = redis.get(Constant.PROVINCE_KEY);
        if (object == null) {
            List<Province> list = provinceService.findAll();
            redis.set(Constant.PROVINCE_KEY, JSONArray.toJSON(list));
            return list;
        }

        return JSONArray.parseArray(object.toString(),Province.class);
    }

    /**
     * 根据年份和区域获取批次
     *
     * @param year
     * @param provinceId
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/getBatchByYearAndArea",method = RequestMethod.GET)
    public List<Map<String,Object>> getBatchByYearAndArea(@RequestParam String year,@RequestParam String provinceId,HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if(provinceId == null || "".equals(provinceId)){
            provinceId = "-1";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("year",year);
        map.put("areaId",provinceId);
        map.put("currAreaId",getUserProvinceId(request,response));

        return iUniversityApi.getBatchByYearAndArea(map) ;
    }

    /**
     * 获取当前用户的省份ID
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private long getUserProvinceId(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        Object object = request.getSession().getAttribute(Constant.USER_SESSION_KEY);
        if(object == null){
            response.sendRedirect("/login");
        }

        UserInfoDto dto = JSON.parseObject(
                object.toString(),
                UserInfoDto.class
        );
        if(dto == null){
            response.sendRedirect("/login");
        }
        return Long.valueOf(dto.getCountyId()/10000+"0000");
    }

    /**
     * 获取院校特征
     *
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getRemoteDataDictList", method = RequestMethod.GET)
    public List getDataDictList(@RequestParam(value = "type", required = true) String type) {

        String key = Constant.DATA_DICT_KEY + type;
        Object object = redis.get(key);
        if (object == null) {
            List list = iUniversityApi.getDataDictList(type);
            redis.set(key, JSONArray.toJSON(list));
            return list;
        }
        return JSONArray.parseArray(object.toString());
    }


    /**
     * 根据省份ID获取录取年份
     *
     * @return
     */
    @RequestMapping(value = "/getYears",method = RequestMethod.GET)
    @ResponseBody
    public List getYears(@RequestParam(value = "schoolProId") String schoolProId, HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        long tmpSchoolProId = -1l;
        if(schoolProId != null && !"".equals(schoolProId)){
            tmpSchoolProId = Long.valueOf(schoolProId);
        }
        long currentProId = getUserProvinceId(request,response);
        return iUniversityApi.getEnrollingYearsByProvinceId(currentProId,tmpSchoolProId);
    }

    @ApiDesc(value = "获取分数线", owner = "杨永平")
    @RequestMapping(value = "/getGkAdmissionLineList",method = RequestMethod.GET)
    @ResponseBody
    public BizData4Page<GkAdmissionLine> getGkAdmissionLineList(@ApiParam(param="universityName", desc="标题模糊查询",required = false) @RequestParam(required = false) String universityName,
                                                                @ApiParam(param="year", desc="年份",required = false) @RequestParam(required = false) String year,
                                                                @ApiParam(param="areaId", desc="区域ID",required = false) @RequestParam(required = false) String areaId,
                                                                @ApiParam(param="property", desc="院校特征",required = false) @RequestParam(required = false) String property,
                                                                @ApiParam(param="batch", desc="批次",required = false) @RequestParam(required = false) Integer batch,
                                                                @ApiParam(param="type", desc="科类",required = false) @RequestParam(defaultValue = "1",required = false) Integer type,
                                                                @ApiParam(param="page", desc="页数",required = false) @RequestParam(defaultValue = "1",required = false) Integer page,
                                                                @ApiParam(param="rows", desc="每页条数",required = false) @RequestParam(defaultValue = "10",required = false) Integer rows,
                                                                HttpServletRequest request,HttpServletResponse response) throws IOException {

        //默认参数设置
        Map<String,Object> map=new HashMap<>();
        map.put("groupOp","and");
        map.put("orderBy","lastModDate");
        map.put("sortBy","desc");
//        年份
        if(year!=null &&!"".equals(year)) {
            QueryUtil.setMapOp(map, "enrollingyear", "=", year);
        }
//        院校名称 模糊
        if(universityName!=null &&!"".equals(universityName)) {
            QueryUtil.setMapOp(map, "universityname", "like", "%" + universityName + "%");
        }
//        地区
        if(areaId!=null &&!"".equals(areaId)) {
            QueryUtil.setMapOp(map, "universityareaid", "=", areaId);
        }
//        特征
        if(property!=null &&!"".equals(property)) {
            QueryUtil.setMapOp(map, "universityproperty", "like", "%" + property + "%");
        }
//        批次
        if(batch!=null) {
            QueryUtil.setMapOp(map, "enrollingbatch", "=", batch);
        }

        Long area=getUserProvinceId(request,response);
        if(area!=null) {
            QueryUtil.setMapOp(map,"enrollingareaid","=",area);
        }
//        文史/理工
        QueryUtil.setMapOp(map, "entype", "=", type);
        map.put("orderBy","rank IS NULL,rank,year");
        map.put("sortBy","asc");


//        BizData4Page<GkAdmissionLine> bizData4Page=getGkAdmissionLineList.getGkAdmissionLineList(map,page,rows);
        BizData4Page<GkAdmissionLine> bizData4Page=iUniversityApi.getAdmissionLineList(map,page,rows);
        for(GkAdmissionLine gkAdmissionLine:bizData4Page.getRows()){
            String[] propertys2= null;
            Map<String,Object> propertyMap=new HashMap();
            if(StringUtils.isNotEmpty(gkAdmissionLine.getProperty().toString())){
                propertys2=gkAdmissionLine.getProperty().split(",");
                Map<String,Object> propertysMap =getPropertys();

                for(String str:propertys2){
                    Iterator<String> propertysIterator=propertysMap.keySet().iterator();
                    while (propertysIterator.hasNext()){
                        String key = propertysIterator.next();
                        String value=propertysMap.get(key).toString();
                        if(str.indexOf(value)>-1){
                            propertyMap.put(key,value);
                        }
                    }
                }
            }
            gkAdmissionLine.setPropertys(propertyMap);
        }


        return bizData4Page;
    }

    private Map<String,Object> getPropertys(){
        List<Map<String,Object>> list=null;
        Map<String,Object> propertysMap=new HashMap<>();

        String key="universityPropertys";
        boolean flag=redis.exists(key);
        if(flag){
            propertysMap= JSON.parseObject(redis.get(key).toString(), Map.class);
        }else {
            list = iUniversityApi.getDataDictList("FEATURE");
            for(Map<String,Object> map:list){
                propertysMap.put(map.get("dictId").toString(),map.get("name").toString());
            }
            redis.set(
                    key,JSON.toJSON(propertysMap),
                    Constant.TOKEN_EXPIRE_TIME,
                    TimeUnit.SECONDS
            );
        }
        return propertysMap;
    }

    /**
     * 专业招生信息条件查询
     *
     * @param universityId
     * @return
     */
    @RequestMapping(value = "/getMpConditions", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> getMpcConditions(@RequestParam long universityId,HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        long provinceId = getUserProvinceId(request,response);
        String key = String.format(Constant.CONDITION_KEY, universityId, provinceId);
        if(redis.exists(key))
        {
            return (Map<String, List<String>>) redis.get(key);
        }
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("areaId", String.valueOf(provinceId));
        paramMap.put("universityId",universityId+"");
        List<Map<String, Object>> list = iUniversityApi.getMajorPlanConditions(paramMap);
        Map<String, List<String>> resultMap = new LinkedHashMap<>();
        for (Map<String, Object> map: list) {
            String year =  map.get("year") + "";
            String majorType =  map.get("majorType")+"";
            List<String> majorTypeList = resultMap.get(year);
            if(null == majorTypeList)
            {
                majorTypeList = new ArrayList<>();
            }
            majorTypeList.add(majorType);
            resultMap.put(year, majorTypeList);
        }
        redis.set(key, resultMap);
        return  resultMap;
    }

    /**
     * 根据类型获取分类
     * @param type
     * @return
     */
    @RequestMapping(value = "getMajoredCategory",method = RequestMethod.GET)
    @ResponseBody
    public Object getMajoredCategory(@RequestParam(value = "type",required = true)long type){
        return iMajoredApi.getMajoredCategory(type);
    }

    /**
     * 根据分类获取专业
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "getCategoryMajoredList",method = RequestMethod.GET)
    @ResponseBody
    public Object getCategoryMajoredList(@RequestParam(value = "categoryId",required = true)long categoryId){
        return iMajoredApi.getCategoryMajoredList(categoryId);
    }

    /**
     * 根据专业名称模糊查找专业
     * @param majoredName
     * @param type
     * @return
     */
    @RequestMapping(value = "getMajoredByName",method = RequestMethod.GET)
    @ResponseBody
    public Object getMajoredByName(@RequestParam(value = "majoredName",required = true)String majoredName,@RequestParam(value = "type",required = true)String type){
        return iMajoredApi.getMajoredByName(majoredName,type);
    }

    /**
     * 根据专业查询开设院校
     *
     * @param majoredId
     * @param majorType
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getMajorOpenUniversityList",method = RequestMethod.GET)
    @ResponseBody
    public Object getMajorOpenUniversityList(@RequestParam(value = "majoredId",required = true)String majoredId,
                                             @RequestParam(value = "majorType",required = true,defaultValue = "1")Integer majorType,
                                             @RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                                             @RequestParam(value = "rows",required = false,defaultValue = "10")Integer rows){

        List<Map<String,Object>> getUniversityList=iMajoredApi.getMajorOpenUniversityList(majoredId,majorType,page-1,rows);
        int count=iMajoredApi.getMajorOpenUniversityCount(majoredId, majorType);
        for (Map<String, Object> university : getUniversityList) {
            String[] propertys=new String[1];
            propertys[0]=university.get("property").toString();

            university.put("property",propertys);
            university.put("isCollect",0);
            String[] propertys2 = null;
            Map<String, Object> propertyMap = new HashMap();
            if (StringUtils.isNotEmpty(university.get("property").toString())) {
                propertys2 = propertys[0].toString().split(",");
                Map<String, Object> propertysMap = getPropertys();

                for (String str : propertys2) {
                    Iterator<String> propertysIterator = propertysMap.keySet().iterator();
                    while (propertysIterator.hasNext()) {
                        String key = propertysIterator.next();
                        String value = propertysMap.get(key).toString();
                        if (str.indexOf(value) > -1) {
                            propertyMap.put(key, value);
                        }
                    }
                }
            }

            university.put("propertys", propertyMap);
        }
        Map<String,Object> returnMap=Maps.newHashMap();
        returnMap.put("universityList", getUniversityList);
        returnMap.put("count", count);
        return returnMap;
    }

    @RequestMapping(value = "/getJobOrientation",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getJobOrientation(@RequestParam(value = "majoredId",required = true)int majoredId){
        return iMajoredApi.getJobOrientation(majoredId);
    }

    @ApiDesc(value = "获取职业分类", owner = "杨永平")
    @RequestMapping(value = "/getProfessionCategory",method = RequestMethod.GET)
    @ResponseBody
    public Object getProfessionCategory(@ApiParam(param="pid", desc="父Id",required = true) @RequestParam(required = false) String pid){
        Map<String,Object> map = new HashMap<>();
        if(pid!=null && !"".equals(pid)) {
            map.put("pid", pid);
        }
        return gkProfessionalService.getProfessionCategory(map);
    }

    @ApiDesc(value = "获取职业列表", owner = "杨永平")
    @RequestMapping(value = "/getProfessionalList",method = RequestMethod.GET)
    @ResponseBody
    public cn.thinkjoy.zgk.domain.BizData4Page<GkProfessional> getProfessionalList(@ApiParam(param="queryparam", desc="搜索框查询字段") @RequestParam(required = false) String queryparam,
                                                            @ApiParam(param="professionTypeId", desc="大类型") @RequestParam(required = false) Integer professionTypeId,
                                                            @ApiParam(param="professionSubTypeId", desc="子类型") @RequestParam(required = false) Integer professionSubTypeId,
                                                            @ApiParam(param="page", desc="当前页数") @RequestParam(defaultValue = "1",required = false) Integer page,
                                                            @ApiParam(param="rows", desc="每页行数") @RequestParam(defaultValue = "6",required = false) Integer rows){
        Map<String,Object> map = new HashMap<>();
        if(queryparam!=null &&!"".equals(queryparam)) {
            QueryUtil.setMapOp(map, "professionName", "like", queryparam);
        }
        if(professionTypeId!=null) {
            QueryUtil.setMapOp(map, "professionType", "=", professionTypeId);
        }
        if(professionSubTypeId!=null) {
            QueryUtil.setMapOp(map, "professionSubType", "=", professionSubTypeId);
        }
        return gkProfessionalService.getProfessionalList(map, page, rows);
    }

    @ApiDesc(value = "根据ID获取职业详情", owner = "杨永平")
    @RequestMapping(value = "/getProfessionalInfo",method = RequestMethod.GET)
    @ResponseBody
    public GkProfessionDTO getProfessionalInfo(@ApiParam(param="id", desc="主键ID",required = true) @RequestParam("id") String id){
        GkProfessionDTO gkProfessionDTO=gkProfessionalService.getProfessionalInfo(new HashMap<String, Object>(),id);
        return gkProfessionDTO;
    }

    @RequestMapping(value = "getRemoteUniversityById", method = RequestMethod.GET)
    @ResponseBody
    public Object getUniversityDTOById(@RequestParam(value = "universityId", required = true) long universityId) {
        String key = "zgk_uy:" + universityId;
        Object object = redis.get(key);
        if (object == null || "null".equals(object)) {
            Map<String, Object> map = (Map<String, Object>) universityService.getUniversityById(universityId);
            Map<String, Object> propertyMap = new HashMap();
            if (StringUtils.isNotEmpty(map.get("property").toString())) {
                String[] propertys = map.get("property").toString().toString().split(",");
                Map<String, Object> propertysMap = getPropertys();

                for (String str : propertys) {
                    Iterator<String> propertysIterator = propertysMap.keySet().iterator();
                    while (propertysIterator.hasNext()) {
                        String tmpkey = propertysIterator.next();
                        String value = propertysMap.get(tmpkey).toString();
                        if (str.indexOf(value) > -1) {
                            propertyMap.put(tmpkey, value);
                        }
                    }
                }
            }
            map.put("propertys", propertyMap);
            redis.set(key, JSON.toJSONString(map));
            return map;
        }
        return JSON.parseObject(object.toString(), Object.class);
    }

    /**
     * 招生计划列表获取
     *
     * @param universityId
     * @param year
     * @param batch
     * @param majorType
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "getUniversityMajorEnrollingPlanList", method = RequestMethod.GET)
    @ResponseBody
    public List getUniversityMajorEnrollingPlanList(@RequestParam(value = "universityId", required = true) long universityId,
                                                    @RequestParam(value = "year", required = false) String year,//年份
                                                    @RequestParam(value = "batch", required = false) Integer batch,//批次
                                                    @RequestParam(value = "majorType", required = false) String majorType,//科类
                                                    @RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "rows") Integer rows,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        if (rows>50){
            ExceptionUtil.throwException(ErrorCode.ROWS_TOO_LONG);
        }
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("universityId", String.valueOf(universityId));
        if (StringUtils.isNotBlank(year)) {
            condition.put("year", year);
        }
        if (batch != null) {
            condition.put("batch", batch);
        }
        if (StringUtils.isNotBlank(majorType)) {
            condition.put("majorType", majorType);
        }
        condition.put("areaId",getUserProvinceId(request,response));
        condition.put("offset", page-1);
        condition.put("rows", rows);
        List ll = iUniversityApi.getUniversityMajorEnrollingPlanList(condition);
        return ll;
    }

    /**
     * 查询专业详情
     * @param majoredCode
     * @return
     */
    @RequestMapping(value = "getMajoredInfoByCode",method = RequestMethod.GET)
    @ResponseBody
    public Map getMajoredInfoByCode(@RequestParam(value = "majoredCode", required = true) String majoredCode){
        //专业Id、专业名称、就业率、薪资、专业代码、授予学位、修学年限、开设课程、专业解读、
        return iMajoredApi.getMajoredInfoByCode(majoredCode);
    }

}
