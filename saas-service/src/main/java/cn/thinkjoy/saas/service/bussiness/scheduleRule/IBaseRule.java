package cn.thinkjoy.saas.service.bussiness.scheduleRule;


import cn.thinkjoy.saas.dto.BaseRuleDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
public interface IBaseRule {

    List<BaseRuleDto> selectBaseRule(Map<String, Object> map);

    void updateBaseRule(String ruleTable,List<BaseRuleDto> baseRuleDtoList);
}
