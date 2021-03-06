package cn.thinkjoy.saas.service.bussiness.scheduleRule;

import cn.thinkjoy.saas.domain.bussiness.MergeClassInfo;
import cn.thinkjoy.saas.dto.MergeClassInfoDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
public interface IMergeClass {
    void insertMergeInfo(Map<String,Object> map);

    List<MergeClassInfoDto> selectMergeInfo(String tnId,String courseId,String classType,String taskId);

    void deleteMergeInfo(Map<String,Object> map);
}
