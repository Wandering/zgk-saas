package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.ClassRooms;
import cn.thinkjoy.saas.domain.bussiness.ClassRoomView;
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
    List<ClassRoomView> selectClassRoomByTnId(Map map);

    /**
     * 新增教室设置
     * @param classRoomsList
     * @return
     */
    Integer addClassRoom(@Param("ClassRoomsList")List<ClassRooms> classRoomsList);


    /**
     * 删除教室
     * @param ids
     * @return
     */
    Integer removeClassRooms(List<String> ids);

    /**
     * 新增教室设置
     * @param classRooms
     * @return
     */
    Integer insertClassRoom(ClassRooms classRooms);
}
