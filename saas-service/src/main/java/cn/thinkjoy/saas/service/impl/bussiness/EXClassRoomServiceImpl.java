package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.IClassRoomsDAO;
import cn.thinkjoy.saas.dao.bussiness.EXIClassRoomDAO;
import cn.thinkjoy.saas.domain.ClassRooms;
import cn.thinkjoy.saas.service.bussiness.EXIClassRoomService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/24.
 */
@Service("EXClassRoomServiceImpl")
public class EXClassRoomServiceImpl implements EXIClassRoomService {
    @Resource
    IClassRoomsDAO iClassRoomsDAO;

    @Resource
    EXIClassRoomDAO exiClassRoomDAO;

    /**
     * 根据字段查找一个教室对象
     *
     * @param map
     * @return
     */
    @Override
    public ClassRooms selectClassRoomByTnId(Map map) {
        return exiClassRoomDAO.selectClassRoomByTnId(map);

    }

    /**
     * 新增教室设置
     * @param tnId   租户ID
     * @param nums
     * @return
     */
    @Override
    public boolean addClassRoom(Integer tnId, String nums) {

        boolean result = false;

        if (tnId > 0 && !StringUtils.isBlank(nums)) {

            List<String> idsList = ParamsUtils.idsSplit(nums);

            List<ClassRooms> classRoomsList = new ArrayList<>();

            if (idsList == null)
                return false;
            for (int i = 0; i < idsList.size(); i++) {
                String row = idsList.get(i);
                String[] rowArr = row.split(ParamsUtils.CLASSROOM_GRADE_COMBIN_CHAR);

                ClassRooms classRooms = new ClassRooms();
                classRooms.setTnId(tnId);
                classRooms.setCreateDate(System.currentTimeMillis());
                classRooms.setGradeId(Integer.valueOf(rowArr[0]));
                classRooms.setNumber(Integer.valueOf(rowArr[1]));
                classRoomsList.add(classRooms);
            }

            Integer addResu = exiClassRoomDAO.addClassRoom(classRoomsList);
            result = addResu > 0 ? true : false;

        }

        return result;
    }
}
