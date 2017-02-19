package cn.thinkjoy.saas.dao.bussiness.scheduleRule;

import cn.thinkjoy.saas.domain.bussiness.MergeClassInfoVo;
import cn.thinkjoy.saas.dto.MergeClassInfoDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
public interface MergeClassDAO {

    void insertMergeInfo(Map<String,Object> map);

    List<MergeClassInfoDto> selectMergeInfo(Map<String,String> map);

    void deleteMergeInfo(Map<String,Object> map);

    List<MergeClassInfoVo> selectMergeClassInfo(Map map);

    Integer checkIsRepeat(Map<String,Object> map);
}
