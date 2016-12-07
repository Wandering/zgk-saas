package cn.thinkjoy.saas.dao.bussiness.scheduleRule;

import cn.thinkjoy.saas.domain.bussiness.MergeClassInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
public interface MergeClassDAO {

    void insertMergeInfo(Map<String,Object> map);

    List<MergeClassInfo> selectMergeInfo(Map<String,Object> map);

    void deleteMergeInfo(Map<String,Object> map);
}
