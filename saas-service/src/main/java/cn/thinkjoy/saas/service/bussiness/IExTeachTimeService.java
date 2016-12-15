package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by yangyongping on 2016/12/13.
 */
public interface IExTeachTimeService {

    boolean saveTeachTime(Integer taskId,
                          String teachDate,
                          String teachTime,
                          Integer tnId);

    Map<String, Object> queryTeachTime(Integer taskId,Integer tnId);

    boolean queryTeachTimeStatus(Integer taskId);
}
