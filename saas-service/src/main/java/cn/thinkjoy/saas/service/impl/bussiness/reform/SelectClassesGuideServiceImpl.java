package cn.thinkjoy.saas.service.impl.bussiness.reform;

import cn.thinkjoy.saas.dao.bussiness.reform.SelectClassesGuideDAO;
import cn.thinkjoy.saas.dto.AnalysisDto;
import cn.thinkjoy.saas.dto.MajorDto;
import cn.thinkjoy.saas.dto.PlanEnrollingDto;
import cn.thinkjoy.saas.dto.UniversityAndMajorNumberDto;
import cn.thinkjoy.saas.service.bussiness.reform.ISelectClassesGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/1.
 */
@Service("selectClassesGuideServiceImpl")
public class SelectClassesGuideServiceImpl implements ISelectClassesGuideService {

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
}