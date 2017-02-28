package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.JwScheduleTask;

import java.util.Map;

/**
 * Created by douzy on 17/2/22.
 */
public interface IEXJwScheduleTaskDAO {
    JwScheduleTask selectScheduleTaskPath(Map map);
}
