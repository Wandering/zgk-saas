package cn.thinkjoy.saas.service.bussiness.reform;

import cn.thinkjoy.saas.dto.EnrollingInfoDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/1.
 */
public interface ITrineService {
    Map<String,Object> selectEnrollingNumberByBatch(Map map);
    List<EnrollingInfoDto> selectEnrollingInfo(Map map);
    int selectEnrollingInfoCount(Map map);
}
