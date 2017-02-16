package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.bussiness.EXIGradeDAO;
import cn.thinkjoy.saas.dao.bussiness.EXITenantConfigInstanceDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.domain.bussiness.SyncClass;
import cn.thinkjoy.saas.domain.bussiness.SyncCourse;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.dto.ClassBaseDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.service.bussiness.IEXTenantCustomService;
import cn.thinkjoy.saas.service.common.EnumUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
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

    @Resource
    EXITenantConfigInstanceDAO exiTenantConfigInstanceDAO;


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

        boolean flag = result > 0;

//        if(flag) {
//            switch (type) {
//                case "teacher":
//                    List<TeantCustom> customs = JSONArray.parseArray(teantCustoms.toString(), TeantCustom.class);
//                    Map<String, Object> map = Maps.newHashMap();
//                    for (TeantCustom custom : customs) {
//                        map.put(custom.getKey(), custom.getValue());
//                    }
//
//                    JwTeacherBaseInfo info = new JwTeacherBaseInfo();
//                    info.setTnId(tnId);
//                    info.setGrade(ConvertUtil.converGrade(map.get("teacher_grade").toString()));
//                    info.setTeacherName(map.get("teacher_name").toString());
//                    info.setTeacherClass(map.get("teacher_class").toString());
//                    info.setTeacherCourse(map.get("teacher_major_type").toString());
//                    iJwTeacherBaseInfoDAO.insert(info);
//                    break;
//            }
//
//        }


        return flag;
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
    public List<LinkedHashMap<String, Object>> getStuInfo(Integer type, Integer tnId, String g, Integer s, Integer r) {
        if (tnId <= 0) {
            return null;
        }
        String tableName = ParamsUtils.combinationTableName(Constant.STUDENT, tnId);

        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("type",type);
        map.put("grade",g);
        map.put("offset",s);
        map.put("rows",r);
        List<LinkedHashMap<String, Object>> tenantCustoms = iexTeantCustomDAO.getStuInfo(map);

        return tenantCustoms;
    }

    @Override
    public List<TeacherBaseDto> getTeacherInfos(Integer tnId, String grade) {
        if (tnId <= 0) {
            return null;
        }
        String tableName = ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER, tnId);

        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("grade",grade);
        map.put("id",null);
        List<TeacherBaseDto> teacherBaseDtos = iexTeantCustomDAO.getTeacherInfos(map);

        return teacherBaseDtos;
    }

    @Override
    public TeacherBaseDto getTeacherInfo(Integer tnId, Integer id) {
        if (tnId <= 0) {
            return null;
        }
        String tableName = ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER, tnId);

        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("grade",null);
        map.put("id",id);
        return iexTeantCustomDAO.getTeacherInfos(map).get(0);
    }

    @Override
    public List<ClassBaseDto> getClassInfos(String type, Integer tnId, String course, String grade) {
        if (tnId <= 0) {
            return null;
        }
        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("grade",grade);
        map.put("course",course);

        List<ClassBaseDto> classBaseDtos = Lists.newArrayList();
        if(grade != null && course == null){
            classBaseDtos = iexTeantCustomDAO.getClassAdmInfos(map);
        }

        if(grade == null && course != null){
            classBaseDtos = iexTeantCustomDAO.getClassEduInfos(map);
        }

        return classBaseDtos;
    }

    @Override
    public Integer getStuInfoCount(Integer type, Integer tnId, String g) {
        if (tnId <= 0) {
            return null;
        }
        String tableName = ParamsUtils.combinationTableName(Constant.STUDENT, tnId);

        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("type",type);
        map.put("grade",g);
        Integer count = iexTeantCustomDAO.getStuInfoCount(map);

        return count;
    }

    /**
     * 查询租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @return
     */
    @Override
    public List<LinkedHashMap<String,Object>> getTenantCustom(String type,Integer tnId,String selectKey,String selectValue,Integer s,Integer r) {
        if (tnId <= 0)
            return null;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return null;

        Map map=new HashMap();
        map.put("tableName",tableName);
        map.put("searchKey",selectKey);
        map.put("searchValue",selectValue);
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
    public List<Map<Integer, Object>> isExcelAddSelect(String type,Integer tnId ,String[] columnNames) {
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
                        String tableName = ParamsUtils.combinationTableName("class", tnId);
                        Map maplin = new HashMap();
                        maplin.put("tableName", tableName);
                        List<LinkedHashMap<String, Object>> linkedHashMapList = iexTeantCustomDAO.getTenantCustom(maplin);
                        value = converClassName(linkedHashMapList);
                        break;
                    case EnumUtil.TEACHER_EDUCATION_MAJOYTYPE://教师-所教科目
                        value = EnumUtil.TEACHER_EDUCATION_MAJOYTYPE_ARR;
                        break;
                    case EnumUtil.STUDENT_EDUCATION_MAJOYTYPE://学生-选择科目
                        value = EnumUtil.STUDENT_EDUCATION_MAJOYTYPE_ARR;
                        break;
                    case EnumUtil.STUDENT_IN_GRADE://学生-所在年级
                        Map stuGradeYear = new HashMap();
                        stuGradeYear.put("tnId", tnId);
                        List<Grade> stuGradeList = exiGradeDAO.selectGradeByTnId(stuGradeYear);
                        value = converGradesArr(stuGradeList);
                        break;
                    case EnumUtil.STUDENT_CLASS_NAME ://学生-班级名称
                          if( type.equals("student")) {
                              String stuTableName = ParamsUtils.combinationTableName("class", tnId);
                              Map mapStu = new HashMap();
                              mapStu.put("tableName", stuTableName);
                              List<LinkedHashMap<String, Object>> stuLinkedHashMapList = iexTeantCustomDAO.getTenantCustom(mapStu);
                              value = converClassName(stuLinkedHashMapList);
                          }
                        break;
                    case EnumUtil.STUDENT_CHECK_MAJOYTYPE1:
                        value = EnumUtil.STUDENT_EDUCATION_MAJOYTYPE_ARR;
                        break;
                    case EnumUtil.STUDENT_CHECK_MAJOYTYPE2:
                        value = EnumUtil.STUDENT_EDUCATION_MAJOYTYPE_ARR;
                        break;
                    case EnumUtil.STUDENT_CHECK_MAJOYTYPE3:
                        value = EnumUtil.STUDENT_EDUCATION_MAJOYTYPE_ARR;
                        break;
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


    /**
     * 判断表中列是否存在数据
     * @return
     */
    @Override
    public Map<String,Object> existDataCount(Map map) {
        return iexTeantCustomDAO.existDataCount(map);
    }


    @Override
    public List<SyncCourse> selectCourseGroup(Map map) {
        return iexTeantCustomDAO.selectCourseGroup(map);
    }

    @Override
    public List<SyncClass> selectClassGroup(Map map) {
        return iexTeantCustomDAO.selectClassGroup(map);
    }


    @Override
    public List<SyncClass> selectExecutiveClassGroup(Map map){
        return iexTeantCustomDAO.selectExecutiveClassGroup(map);
    }

    @Override
    public Map selectExistByCloumn(String tableName,String type,String value1,String value2) {

        Map repeatMap = new HashMap();

        String key1 = null, key2 = null;

        switch (type) {
            case "class":
                key1 = "class_grade";
                key2 = "class_name";
                break;
            case "teacher":
                key1 = "teacher_name";
                key2 = "teacher_major_type";
                break;
            case "student":
                key1 = "student_no";
                key2 = "student_name";
                break;
        }

        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("key1", key1);
        map.put("key2", key2);
        map.put("value1", value1);
        map.put("value2", value2);

        List<LinkedHashMap<String, Object>> linkedHashMaps = iexTeantCustomDAO.selectExistByCloumn(map);
        if (linkedHashMaps != null && linkedHashMaps.size() > 0) {
            List<String> list = new ArrayList<>();
            list.add(linkedHashMaps.get(0).get("id").toString());
            repeatMap.put("repeat", list);
        }
        return repeatMap;
    }
}
