package cn.thinkjoy.saas.service.impl.bussiness.reform;

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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Map<Integer,Map<String, Integer>> selectNumberByYear(int tnId){
        Map<String, Object> map=new HashMap<>();
        map.put("tnId",tnId);
        map.put("domain","student");
        map.put("enName","student_major_type");
        if(selectClassesGuideDAO.selectEnName(map)<1){
            ExceptionUtil.throwException(ErrorCode.TABLE_NOT_EXIST);
        }
        String tableName = "saas_"+tnId+"_student_excel";
        List<Map<String,String>> mapList = Lists.newArrayList();
        map.put("tableName",tableName);
        mapList = selectClassesGuideDAO.selectStudentExcel(map);
//        Map<String, Integer> map2=new HashMap<>();
        Map<Integer, Map<String, Integer>> yearMap=new HashMap<>();
        for (Map<String,String> map1:mapList){
            int year=Integer.valueOf(String.valueOf(map1.get("student_class_in_year")))+3;
            if(!yearMap.containsKey(year)){
                yearMap.put(year,new HashMap<String, Integer>());
            }
            String[] types=map1.get("student_major_type").split("-");
            for(String type:types){
                if(!yearMap.get(year).containsKey(type)){
                    yearMap.get(year).put(type, 0);
                }
                yearMap.get(year).put(type, yearMap.get(year).get(type)+1);
            }

        }
        return yearMap;
    }

    @Override
    public Map<String,Object> selectTypeAnalysis(int tnId,String studentGrade){
        Map<String, Object> map=new HashMap<>();
        String tableName = "saas_"+tnId+"_student_excel";
        map.put("tableName",tableName);
        map.put("student_grade",studentGrade);
        List<Map<String,String>> mapList = Lists.newArrayList();
        mapList = selectClassesGuideDAO.selectStudentExcel(map);
        Map<String, Integer> typeMap=new HashMap<>();
        for(Map<String,String> map1:mapList){
            String[] types=map1.get("student_major_type").split("-");
            for(String type:types){
                if(!typeMap.containsKey(type)){
                    typeMap.put(type, 0);
                }
                typeMap.put(type, typeMap.get(type) + 1);
            }
        }


        //从saas_enrolling_ratio中获取上线率
        String t=selectClassesGuideDAO.getEnrollingPercent();
        map.put("tnId",tnId);
        map.put("grade",studentGrade);
        map.put("examId",selectClassesGuideDAO.selectExamId(map));
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

}
