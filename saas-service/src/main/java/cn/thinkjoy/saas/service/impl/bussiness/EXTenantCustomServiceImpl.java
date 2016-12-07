package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.bussiness.EXIGradeDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.service.bussiness.IEXTenantCustomService;
import cn.thinkjoy.saas.service.common.EnumUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by douzy on 16/10/25.
 */
@Service("EXTenantCustomServiceImpl")
public class EXTenantCustomServiceImpl implements IEXTenantCustomService {

    @Resource
    IEXTeantCustomDAO iexTeantCustomDAO;

    @Resource
    EXIGradeDAO exiGradeDAO;


    /**
     * 租户自定义表头数据添加
     * @param type 模块分类
     * @param tnId 租户ID
     * @param teantCustoms key&value
     * @return
     */
    @Override
    public boolean addTeantCustom(String type ,Integer tnId,List<TeantCustom> teantCustoms) {

        if (tnId <= 0)
            return false;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return false;

        Integer result = iexTeantCustomDAO.insertTenantCustom(tableName, teantCustoms);

        return (result > 0 ? true : false);
    }

    /**
     *  租户自定义表头数据更新
     * @param type 模块分类
     * @param tnId 租户ID
     * @param pri 租户ID
     * @param teantCustoms key&value
     * @return
     */
    @Override
    public boolean modifyTeantCustom(String type,Integer tnId,Integer pri,List<TeantCustom> teantCustoms) {

        if (tnId <= 0 || pri <= 0)
            return false;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return false;

        Integer result = iexTeantCustomDAO.updateTenantCustom(tableName, pri, teantCustoms);

        return (result > 0 ? true : false);
    }


    /**
     *  租户自定义表头数据删除
     * @param type 模块分类
     * @param tnId 租户ID
     * @param ids 租户ID
     * @return
     */
    @Override
    public boolean removeTenantCustom(String type,Integer tnId,String ids) {

        if (tnId <= 0 || StringUtils.isBlank(ids))
            return false;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return false;

        List<String> idsList = ParamsUtils.idsSplit(ids);


        Integer result = iexTeantCustomDAO.removeTenantCustomList(tableName, idsList);
        //.removeTenantCustom(tableName, pri);

        return (result > 0);
    }

    /**
     * 查询租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @return
     */
    @Override
    public List<LinkedHashMap<String,Object>> getTenantCustom(String type,Integer tnId,String g,Integer s,Integer r) {
        if (tnId <= 0)
            return null;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return null;

        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("searchKey",ParamsUtils.getGradeKey(type));
        map.put("searchValue",g);
        map.put("offset",s);
        map.put("rows",r);
        List<LinkedHashMap<String, Object>> tenantCustoms = iexTeantCustomDAO.getTenantCustom(map);


        return tenantCustoms;
    }
    @Override
    public Integer getTenantCustomCount(String type,Integer tnId,String g) {
        if (tnId <= 0)
            return null;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return null;

        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("searchKey", ParamsUtils.getGradeKey(type));
        map.put("searchValue", g);
        return iexTeantCustomDAO.getTenantCustomCount(map);
    }
    /**
     * excel内添加select
     * @param columnNames
     * @return
     */
    @Override
    public List<Map<Integer, Object>> isExcelAddSelect(Integer tnId ,String[] columnNames) {
        List<Map<Integer,Object>> lockSelectList = new ArrayList<>();
        if (columnNames.length > 0) {
            int i = 0;
            for (String str : columnNames) {
                Map<Integer, Object> map = new HashMap<>();

                Object value = null;
                switch (str) {
                    case EnumUtil.CLASS_MAJOR_TYPE: //班级类型
                        value = EnumUtil.CLASS_TYPE_ARR;
                        break;
                    case EnumUtil.CLASS_GRADE://所属年级
                        Map gradeMap = new HashMap();
                        gradeMap.put("tnId", tnId);
                        List<Grade> grades = exiGradeDAO.selectGradeByTnId(gradeMap);
                        value = converGradesArr(grades);
                        break;
                    case EnumUtil.CLASS_ENROLL_YEAR://入学年份
                        value = getEnrollYear(2012, 2020);
                        break;
                    case EnumUtil.TEACHER_SCHOOL_ENROLL_YEAR://教师-入校年份
                        value = getEnrollYear(2012, 2020);
                        break;
                    case EnumUtil.TEACHER_EDUCATION_GRADE://教师-所教年级
                        Map gradeYear = new HashMap();
                        gradeYear.put("tnId", tnId);
                        List<Grade> gradeList = exiGradeDAO.selectGradeByTnId(gradeYear);
                        value = converGradesArr(gradeList);
                        break;
                    case EnumUtil.TEACHER_EDUCATION_CLASS://教师-所教班级
                        String tableName= ParamsUtils.combinationTableName("class", tnId);
                        Map maplin=new HashMap();
                        maplin.put("tableName",tableName);
                        List<LinkedHashMap<String, Object>> linkedHashMapList =iexTeantCustomDAO.getTenantCustom(maplin);
                        value = converClassName(linkedHashMapList);
                        break;
                    case EnumUtil.TEACHER_EDUCATION_MAJOYTYPE://教师-所教科目
                        value=EnumUtil.TEACHER_EDUCATION_MAJOYTYPE_ARR;
                        break;
                    case EnumUtil.STUDENT_EDUCATION_MAJOYTYPE://学生-选择科目
                        value=EnumUtil.STUDENT_EDUCATION_MAJOYTYPE_ARR;

                }

                if(!(value==null)) {
                    map.put(i, value);
                    lockSelectList.add(map);
                }
                i++;
            }
        }
        return lockSelectList;
    }


    /**
     * 入学年份
     * @param start
     * @param end
     * @return
     */
    private String[] getEnrollYear(Integer start,Integer end) {

        Integer size = end - start;

        String[] arr = new String[size+1];

        for (int i = 0; i <= size; i++) {
            arr[i] = start + "";
            start++;
        }

        return arr;
    }

    /**
     * 所属年级
     * @param grades
     * @return
     */
    private String[] converGradesArr(List<Grade> grades) {
        String[] arr=new String[grades.size()];

        int i=0;
        for(Grade grade:grades) {
            arr[i] = grade.getGrade();
            i++;
        }

        return arr;
    }

    /**
     * 班级信息
     * @param tenantCustomsLinks
     * @return
     */
    private String[] converClassName(List<LinkedHashMap<String,Object>> tenantCustomsLinks) {
        String[] arr = new String[tenantCustomsLinks.size()];
        int i = 0;
        for (LinkedHashMap<String, Object> linkedHashMap : tenantCustomsLinks) {
            arr[i] = linkedHashMap.get("class_name").toString();
            i++;
        }
        return arr;
    }
}
