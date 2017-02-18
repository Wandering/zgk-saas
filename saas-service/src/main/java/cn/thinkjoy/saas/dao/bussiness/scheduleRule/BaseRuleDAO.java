package cn.thinkjoy.saas.dao.bussiness.scheduleRule;

import cn.thinkjoy.saas.domain.bussiness.MergeClassInfo;
import cn.thinkjoy.saas.dto.BaseRuleDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
public interface BaseRuleDAO {

    void updateBaseRule(Map<String,Object> map);
    List<BaseRuleDto> selectBaseRule(Map<String,Object> map);
}
