package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.ClassRooms;

import java.util.Map;

/**
 * Created by douzy on 16/10/24.
 */
public interface EXIClassRoomService {
    /**
     * 根据字段查找一个教室对象
     *
     * @param map
     * @return
     */
    ClassRooms selectClassRoomByTnId(Map map);

    /**
     * 新增教室设置
     * @param tnId 租户ID
     * @param nums 1:1-2:2-3:3-4:4-5:5
     * @return
     */
     boolean addClassRoom(Integer tnId, String nums);
}
