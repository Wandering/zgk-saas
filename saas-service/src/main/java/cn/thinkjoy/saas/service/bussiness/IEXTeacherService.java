package cn.thinkjoy.saas.service.bussiness;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/25.
 */
public interface IEXTeacherService {
    /**
     * 当前租户步骤设置
     * @return
     */
    List<LinkedHashMap<String,Object>> getScheduleTeacherByTnIdAndTaskId(int tnId, int taskId, Map<String,Object> params);

}
