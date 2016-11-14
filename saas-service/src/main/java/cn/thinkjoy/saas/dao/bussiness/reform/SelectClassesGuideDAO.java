package cn.thinkjoy.saas.dao.bussiness.reform;

import cn.thinkjoy.saas.dto.*;

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
}
