package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IClassRoomSettingDAO;
import cn.thinkjoy.saas.dao.IClassRoomsDAO;
import cn.thinkjoy.saas.dao.bussiness.EXIClassRoomDAO;
import cn.thinkjoy.saas.dao.bussiness.EXITenantConfigInstanceDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
import cn.thinkjoy.saas.domain.ClassRoomSetting;
import cn.thinkjoy.saas.domain.ClassRooms;
import cn.thinkjoy.saas.domain.bussiness.ClassRoomView;
import cn.thinkjoy.saas.service.bussiness.EXIClassRoomService;
import cn.thinkjoy.saas.service.bussiness.IEXTeacherService;
import cn.thinkjoy.saas.service.bussiness.IEXTenantService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by douzy on 16/10/24.
 */
@Service("EXTeacherServiceImpl")
public class EXTeacherServiceImpl implements IEXTeacherService {
    @Resource
    EXITenantConfigInstanceDAO exiTenantConfigInstanceDAO;

    @Resource
    IEXTeantCustomDAO iexTeantCustomDAO;

    /**
     * 当前租户步骤设置
     *
     * @param tnId
     * @param taskId
     * @param params
     * @return
     */
    @Override
    public List<LinkedHashMap<String, Object>> getScheduleTeacherByTnIdAndTaskId(int tnId, int taskId, Map<String, Object> params) {
        String tableName = ParamsUtils.combinationTableName(Constant.TABLE_TYPE_TEACHER, tnId);
            List<Map<String,Object>> maps = new ArrayList<>();
            Map<String,Object> map;
            Iterator<Map.Entry<String,Object>> $i =params.entrySet().iterator();
            while ($i.hasNext()) {
                Map.Entry<String,Object> entry = $i.next();
                map = new HashMap<>();
                map.put("key", "tb."+entry.getKey());
                map.put("op", "=");
                map.put("value",entry.getValue());
                maps.add(map);
            }
            return iexTeantCustomDAO.likeJwTeacherByParams(tableName,tnId,taskId,maps);

    }

}
