package cn.thinkjoy.saas.service.bussiness.reform;

import cn.thinkjoy.saas.dto.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/1.
 */
public interface ISelectClassesGuideService {
    UniversityAndMajorNumberDto selectUniversityAndMajorNumber(Map map);
    List<MajorDto> selectMajorByUniversityNameAndBatch(Map map);
    int selectMajorByUniversityNameAndBatchCount(Map map);
    List<PlanEnrollingDto> selectPlanEnrollingByProperty(Map map);
    int selectPlanEnrollingByPropertyCount(Map map);
    List<AnalysisDto> selectAnalysisBatch(Map map);
    int selectAnalysisBatchCount(Map map);
    List<AnalysisDto> selectAnalysisDiscipline(Map map);
    int selectAnalysisDisciplineCount(Map map);
    List<Map<String, Object>> queryDictList(Map<String, Object> map);
    Map<String,Object> selectEnrollingNumberByBatch(Map map);
    List<EnrollingNumberDto> selectEnrollingNumber(Map map);

    /**
     * 根据年级和租户查询学校内学生选课情况
     *
     * @param grade 年级
     * @param tnId 租户ID
     * @return
     */
    List<Map<String, Object>> getAnalysisGroup(String grade,String tnId);

    Map<Integer,Map<String, Integer>> selectNumberByYear(int tnId);

    public Map<String,Object> selectTypeAnalysis(int tnId,String studentGrade);
}
