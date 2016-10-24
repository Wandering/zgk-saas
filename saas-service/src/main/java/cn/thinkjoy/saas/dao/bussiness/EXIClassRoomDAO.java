package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.ClassRooms;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/24.
 */
public interface EXIClassRoomDAO {

    /**
     * 根据字段查找一个教室对象
     *
     * @param map
     * @return
     */
    ClassRooms selectClassRoomByTnId(Map map);

    /**
     * 新增教室设置
     * @param classRoomsList
     * @return
     */
    Integer addClassRoom(@Param("ClassRoomsList")List<ClassRooms> classRoomsList);
}
