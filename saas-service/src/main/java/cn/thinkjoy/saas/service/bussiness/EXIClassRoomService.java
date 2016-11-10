package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.ClassRooms;
import cn.thinkjoy.saas.domain.bussiness.ClassRoomView;

import java.util.List;
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
    List<ClassRoomView> selectClassRoomByTnId(Map map);

    /**
     * 新增教室设置
     * @param tnId 租户ID
     * @param nums 1:1-2:2-3:3-4:4-5:5
     * @return
     */
     boolean addClassRoom(Integer tnId, String nums);

    /**
     * 新增教室
     * @param tnId
     * @param gradeId
     * @param classRoomNum
     * @return
     */
    boolean addClassRoom(Integer tnId,Integer gradeId,Integer classRoomNum);


    /**
     * 更新教室
     * @param num 教室数量
     * @param gid 年级ID
     * @param cid 教室标识
     * @return
     */
    boolean updateClassRoom(Integer num,Integer gid,Integer cid);


    /**
     * 删除教室
     * @param ids 教室标识
     * @return
     */
    boolean removeClassRoom(String ids);

    /**
     * 新增教室
     * @return
     */
    boolean insertClassRoom(ClassRooms classRooms);


}
