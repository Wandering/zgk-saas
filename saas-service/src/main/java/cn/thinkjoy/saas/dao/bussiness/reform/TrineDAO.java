package cn.thinkjoy.saas.dao.bussiness.reform;

import cn.thinkjoy.saas.dto.EnrollingInfoDto;
import cn.thinkjoy.saas.dto.TrineDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/1.
 */
public interface TrineDAO {
    List<TrineDto> selectEnrollingNumberByBatch(Map map);
    List<EnrollingInfoDto> selectEnrollingInfo(Map map);
    int selectEnrollingInfoCount(Map map);
}
