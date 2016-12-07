package cn.thinkjoy.saas.service.impl.bussiness.scheduleRule;

import cn.thinkjoy.saas.dao.bussiness.scheduleRule.MergeClassDAO;
import cn.thinkjoy.saas.domain.bussiness.MergeClassInfo;
import cn.thinkjoy.saas.service.bussiness.scheduleRule.IMergeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
@Service("mergeClassImpl")
public class MergeClassImpl implements IMergeClass {

    @Autowired
    private MergeClassDAO mergeClassDAO;

    @Override
    public void insertMergeInfo(Map<String, Object> map) {
        mergeClassDAO.insertMergeInfo(map);
    }

    @Override
    public List<MergeClassInfo> selectMergeInfo(Map<String, Object> map) {
        return mergeClassDAO.selectMergeInfo(map);
    }

    @Override
    public void deleteMergeInfo(Map<String, Object> map) {
        mergeClassDAO.deleteMergeInfo(map);
    }
}
