package cn.thinkjoy.saas.service.impl.bussiness.scheduleRule;

import cn.thinkjoy.saas.dao.bussiness.scheduleRule.BaseRuleDAO;
import cn.thinkjoy.saas.dto.BaseRuleDto;
import cn.thinkjoy.saas.service.bussiness.scheduleRule.IBaseRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
@Service("baseRuleImpl")
public class BaseRuleImpl implements IBaseRule {

    @Autowired
    private BaseRuleDAO baseRuleDAO;

    @Override
    public List<BaseRuleDto> selectBaseRule(Map<String, Object> map) {
        return baseRuleDAO.selectBaseRule(map);
    }

    @Override
    public void updateBaseRule(String ruleTable,List<BaseRuleDto> baseRuleDtoList){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("ruleTable",ruleTable);
        for(BaseRuleDto baseRuleDto:baseRuleDtoList){
            paramMap.put("baseRuleDto",baseRuleDto);
            baseRuleDAO.updateBaseRule(paramMap);
        }
    }
}
