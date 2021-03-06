package cn.thinkjoy.saas.service.impl.bussiness.reform;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.bussiness.reform.SelectClassesGuideDAO;
import cn.thinkjoy.saas.dto.*;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.dto.AnalysisDto;
import cn.thinkjoy.saas.dto.MajorDto;
import cn.thinkjoy.saas.dto.PlanEnrollingDto;
import cn.thinkjoy.saas.dto.UniversityAndMajorNumberDto;
import cn.thinkjoy.saas.service.bussiness.reform.ISelectClassesGuideService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/1.
 */
@Service("selectClassesGuideServiceImpl")
public class SelectClassesGuideServiceImpl implements ISelectClassesGuideService {

    private static final Logger logger = Logger.getLogger(SelectClassesGuideServiceImpl.class);

    @Autowired
    private SelectClassesGuideDAO selectClassesGuideDAO;

    @Override
    public UniversityAndMajorNumberDto selectUniversityAndMajorNumber(Map map) {
        return selectClassesGuideDAO.selectUniversityAndMajorNumber(map);
    }

    @Override
    public List<MajorDto> selectMajorByUniversityNameAndBatch(Map map) {
        return selectClassesGuideDAO.selectMajorByUniversityNameAndBatch(map);
    }

    @Override
    public int selectMajorByUniversityNameAndBatchCount(Map map) {
        return selectClassesGuideDAO.selectMajorByUniversityNameAndBatchCount(map);
    }

    @Override
    public List<PlanEnrollingDto> selectPlanEnrollingByProperty(Map map) {
        return selectClassesGuideDAO.selectPlanEnrollingByProperty(map);
    }

    @Override
    public int selectPlanEnrollingByPropertyCount(Map map) {
        return selectClassesGuideDAO.selectPlanEnrollingByPropertyCount(map);
    }

    @Override
    public List<AnalysisDto> selectAnalysisBatch(Map map) {
        return selectClassesGuideDAO.selectAnalysisBatch(map);
    }

    @Override
    public int selectAnalysisBatchCount(Map map) {
        return selectClassesGuideDAO.selectAnalysisBatchCount(map);
    }

    @Override
    public List<AnalysisDto> selectAnalysisDiscipline(Map map) {
        return selectClassesGuideDAO.selectAnalysisDiscipline(map);
    }

    @Override
    public int selectAnalysisDisciplineCount(Map map) {
        return selectClassesGuideDAO.selectAnalysisDisciplineCount(map);
    }

    @Override
    public List<Map<String, Object>> queryDictList(Map<String, Object> map) {
        return selectClassesGuideDAO.queryDictList(map);
    }

    @Override
    public Map<String,Object> selectEnrollingNumberByBatch(Map map) {
        List<TrineDto> trineDtoList=selectClassesGuideDAO.selectEnrollingNumberByBatch(map);
        int count=0;
        int enrollingCount=0;
        for(TrineDto trineDto:trineDtoList){
            count=count+trineDto.getMajorNumber();
            enrollingCount=enrollingCount+trineDto.getPlanEnrollingNumber();
        }
        for(TrineDto trineDto:trineDtoList){
            double precent=Math.round((double)trineDto.getMajorNumber()*100/count)/100.0;
            trineDto.setPercent(String.valueOf(precent));
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("enrollingNumberByBatch",trineDtoList);
        returnMap.put("majorCount",count);
        returnMap.put("enrollingCount",enrollingCount);
        return returnMap;
    }

    @Override
    public List<EnrollingNumberDto> selectEnrollingNumber(Map map) {
        return selectClassesGuideDAO.selectEnrollingNumber(map);
    }

    @Override
    public List<Map<String, Object>> getAnalysisGroup(String grade, String tnId) {
        String table = "saas_"+tnId+"_student_excel";
        List<Map<String,Object>> groups = Lists.newArrayList();

        try{
            groups = selectClassesGuideDAO.getAnalysisGroup(grade,table);
        }catch (Exception e){
            logger.error(table+"不存在,或者字段不存在",e);
            ExceptionUtil.throwException(ErrorCode.TABLE_NOT_EXIST);
        }

        return groups;
    }

    @Override
    public Map<String,Object> selectNumberByYear(int tnId){
        Map<String, Object> map=new HashMap<>();
        map.put("tnId",tnId);
        map.put("domain","student");
//        map.put("enName","student_major_type");
//        if(selectClassesGuideDAO.selectEnName(map)<1){
//            ExceptionUtil.throwException(ErrorCode.TABLE_NOT_EXIST);
//        }
        String tableName = "saas_"+tnId+"_student_excel";
        String classTableName1 = "saas_"+tnId+"_class_adm_excel";
        String classTableName2 = "saas_"+tnId+"_class_edu_excel";
        List<Map<String,String>> mapList = Lists.newArrayList();
        map.put("tableName",tableName);
        map.put("classTableName1",classTableName1);
        map.put("classTableName2",classTableName2);
        try{
            mapList = selectClassesGuideDAO.selectStudentExcel(map);
        }catch (Exception e){
            ExceptionUtil.throwException(ErrorCode.TABLE_NOT_EXIST);
        }
//        Map<String, Integer> map2=new HashMap<>();
        Map<String, Map<String, Integer>> yearMap=new HashMap<>();
        Map<String,Integer> yearCountMap=new HashMap<>();
        for (Map<String,String> map1:mapList){
            String year=String.valueOf(Integer.valueOf(String.valueOf(map1.get("class_in_year")))+3);
            String[] types=map1.get("student_major_type") == null ? null : map1.get("student_major_type").split("-");
            if (types == null){
                continue;
            }
            if(!yearMap.containsKey(year)){
                Map<String,Integer> map2=new HashMap<String, Integer>();
                map2.put("物理",0);
                map2.put("化学",0);
                map2.put("生物",0);
                map2.put("政治",0);
                map2.put("历史",0);
                map2.put("地理",0);
                map2.put("通用技术",0);
                yearMap.put(year,map2);
            }
            for(String type:types){
                if(!yearMap.get(year).containsKey(type)){
                    yearMap.get(year).put(type, 0);
                }
                yearMap.get(year).put(type, yearMap.get(year).get(type)+1);
            }
            if(!yearCountMap.containsKey(year)) {
                yearCountMap.put(year, 0);
            }
            int number=yearCountMap.get(year).intValue()+1;
            yearCountMap.put(year,number);
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("yearMap",yearMap);
        returnMap.put("yearCountMap",yearCountMap);
        return returnMap;
    }

    @Override
    public Map<String,Object> selectTypeAnalysis(int tnId,String studentGrade){
        Map<String, Object> map=new HashMap<>();
        map.put("tnId",tnId);
        map.put("domain","student");
        map.put("enName","student_grade");
        if(selectClassesGuideDAO.selectEnName(map)<1){
            ExceptionUtil.throwException(ErrorCode.TABLE_NOT_EXIST);
        }
        String tableName = "saas_"+tnId+"_student_excel";
        map.put("tableName",tableName);
        map.put("grade",studentGrade);
        //从saas_enrolling_ratio中获取上线率
        String t=selectClassesGuideDAO.getEnrollingPercent(map);
        if (t == null) {
            throw new BizException("error","请先设置升学率");
        }
        map.put("examId",selectClassesGuideDAO.selectExamId(map));
        List<String> selectCoursesAll=selectClassesGuideDAO.selectLimitStudent(map);
        Map<String, Integer> typeMap=new HashMap<>();
        for(String selectCourses:selectCoursesAll){
            String[] types=selectCourses.split("@");
            for(String type:types){
                if(!typeMap.containsKey(type)){
                    typeMap.put(type, 0);
                }
                typeMap.put(type, typeMap.get(type) + 1);
            }
        }

        int count=selectClassesGuideDAO.selectStudentNumber(map);
        Long top=Math.round(count * Double.valueOf(t));
        map.put("top",top.intValue());
        List<String> selectCoursesList=selectClassesGuideDAO.selectLimitStudent(map);
        Map<String, Integer> limitTypeMap=new HashMap<>();
        for(String selectCourses:selectCoursesList){
            String[] types=selectCourses.split("@");
            for(String type:types){
                if(!limitTypeMap.containsKey(type)){
                    limitTypeMap.put(type, 0);
                }
                limitTypeMap.put(type, limitTypeMap.get(type) + 1);
            }
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("oneTypeMap",typeMap);
        returnMap.put("count",count);
        returnMap.put("top",top.intValue());
        returnMap.put("limitTypeMap",limitTypeMap);

        return returnMap;
    }

    @Override
    public List<CourseAndTeacherDto> queryTeachersByGrade(String grade, String tnId) {

        String table = "saas_"+tnId+"_teacher_excel";
        List<CourseAndTeacherDto> ctDtos = Lists.newArrayList();

        // [1] 检测数据完整性
        List<TeacherAndClassDto> tcDtos = Lists.newArrayList();
        try{
            tcDtos = selectClassesGuideDAO.queryTeachersByGrade(grade,table);
        }catch (Exception e){
            logger.error(table+"不存在,或者字段不存在",e);
            ExceptionUtil.throwException(ErrorCode.TABLE_NOT_EXIST);
        }

        if(tcDtos.size() == 0){
            return ctDtos;
        }

        Integer maxNum = selectClassesGuideDAO.queryClassRoomByTnId(Integer.valueOf(tnId));
        if(maxNum == null){
            // 教室容量默认50人
            maxNum = 50;
        }

        // [2] 按科目重新组装教师带班信息
        Map<String,CourseAndTeacherDto> ctMap = Maps.newHashMap();
        for(TeacherAndClassDto tcDto : tcDtos){

            // 只展示可选课的科目
            if(!Constant.COURSEES.contains(tcDto.getCourseName())){
                continue;
            }

            CourseAndTeacherDto ctDtoTmp = ctMap.get(tcDto.getCourseName().trim());
            if(ctDtoTmp != null){
                ctDtoTmp.setClassMaxNum(ctDtoTmp.getClassMaxNum()+tcDto.getMaxClass());
                ctDtoTmp.setStuMaxNum(ctDtoTmp.getStuMaxNum()+tcDto.getMaxClass()*maxNum);
                ctDtoTmp.getTeachers().add(tcDto);
                continue;
            }

            CourseAndTeacherDto ctDto = new CourseAndTeacherDto();
            ctDto.setClassMaxNum(tcDto.getMaxClass());
            ctDto.setCourseName(tcDto.getCourseName());
            ctDto.setStuMaxNum(tcDto.getMaxClass()*maxNum);
            ctDto.getTeachers().add(tcDto);

            ctMap.put(tcDto.getCourseName().trim(),ctDto);
        }

        // [3] 组装科目与教师信息（按科目分类）
        for(CourseAndTeacherDto dto : ctMap.values()){
            dto.setTeacherNum(dto.getTeachers().size());
            ctDtos.add(dto);
        }

        return ctDtos;
    }

    public Map<String,Object> getUndergraduateEnrollingNumber(int tnId,List<String> yearList){
        Map<String,Object> map=new HashMap<>();
        map.put("tnId",tnId);
        List<Map<String,Integer>> undergraduateEnrollingNumberList=selectClassesGuideDAO.selectUndergraduateEnrollingNumber(map);
        int maxNumber=0;
        String maxYear="";
        List<Map<String,Integer>> returnUndergraduateEnrollingNumberList=new ArrayList<>();
        for(Map<String,Integer> undergraduateEnrollingNumber:undergraduateEnrollingNumberList){
            String year = ((Number)undergraduateEnrollingNumber.get("year")).toString();
            if(yearList.contains(year)) {
                int tmp = ((Number) undergraduateEnrollingNumber.get("number")).intValue();
                if (maxNumber < tmp) {
                    maxNumber = tmp;
                    maxYear = year;
                }
                returnUndergraduateEnrollingNumberList.add(undergraduateEnrollingNumber);
            }
        }
        map.put("undergraduateEnrollingNumberList",returnUndergraduateEnrollingNumberList);
        map.put("maxYear",maxYear);
        return map;
    }
}
