package cn.thinkjoy.saas.dao.bussiness.scheduleRule;

import cn.thinkjoy.saas.domain.bussiness.MergeClassInfo;
import cn.thinkjoy.saas.dto.BaseRuleDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
public interface BaseRuleDAO {

    List selectJaqpByCourseId(Map<String,Object> map);
    List updateJaqpById(Map<String,Object> map);
    List selectWeekByCourseId(Map<String,Object> map);
    List updateWeekById(Map<String,Object> map);
    List selectDayByCourseId(Map<String,Object> map);
    List updateDayById(Map<String,Object> map);
    void updateBaseRule(Map<String,Object> map);
    List<BaseRuleDto> selectBaseRule(Map<String,Object> map);
}
