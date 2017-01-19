package cn.thinkjoy.saas.dao.bussiness.reform;

import cn.thinkjoy.saas.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/1.
 */
public interface SelectClassesGuideDAO {
    UniversityAndMajorNumberDto selectUniversityAndMajorNumber(Map map);
    List<MajorDto> selectMajorByUniversityNameAndBatch(Map map);
    int selectMajorByUniversityNameAndBatchCount(Map map);
    List<PlanEnrollingDto> selectPlanEnrollingByProperty(Map map);
    int selectPlanEnrollingByPropertyCount(Map map);
    List<AnalysisDto> selectAnalysisBatch(Map map);
    int selectAnalysisBatchCount(Map map);
    List<AnalysisDto> selectAnalysisDiscipline(Map map);
    int selectAnalysisDisciplineCount(Map map);

    List<Map<String, Object>> queryDictList(Map map);
    List<TrineDto> selectEnrollingNumberByBatch(Map map);
    List<EnrollingNumberDto> selectEnrollingNumber(Map map);

    /**
     * 根据年级查询学校内学生选课情况
     *
     * @param grade 年级
     * @param table 表名(动态生成)
     * @return
     */
    List<Map<String, Object>> getAnalysisGroup(
            @Param("grade") String grade,
            @Param("table") String table
    );

    int selectEnName(Map map);
    List<Map<String, String>> selectStudentExcel(Map map);

    String getEnrollingPercent(Map map);
    String selectExamId(Map map);
    int selectStudentNumber(Map map);
    List<String> selectLimitStudent(Map map);

    List<Map<String,Integer>> selectUndergraduateEnrollingNumber(Map map);

}
