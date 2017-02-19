package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.IClassRoomSettingDAO;
import cn.thinkjoy.saas.dao.IClassRoomsDAO;
import cn.thinkjoy.saas.dao.bussiness.EXIClassRoomDAO;
import cn.thinkjoy.saas.domain.ClassRoomSetting;
import cn.thinkjoy.saas.domain.ClassRooms;
import cn.thinkjoy.saas.domain.bussiness.ClassRoomView;
import cn.thinkjoy.saas.service.bussiness.EXIClassRoomService;
import cn.thinkjoy.saas.service.bussiness.IEXTenantService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/24.
 */
@Service("EXClassRoomServiceImpl")
public class EXClassRoomServiceImpl implements EXIClassRoomService {
    private static final Logger LOGGER= LoggerFactory.getLogger(EXClassRoomServiceImpl.class);
    @Resource
    IClassRoomsDAO iClassRoomsDAO;

    @Resource
    EXIClassRoomDAO exiClassRoomDAO;

    @Resource
    IEXTenantService iexTenantService;

    @Resource
    IClassRoomSettingDAO iClassRoomSettingDAO;

    /**
     * 根据字段查找一个教室对象
     *
     * @param map
     * @return
     */
    @Override
    public List<ClassRoomView> selectClassRoomByTnId(Map map) {
        return exiClassRoomDAO.selectClassRoomByTnId(map);

    }

    @Override
    public boolean addClassRoom(Integer tnId,Integer gradeId,Integer classRoomNum,Integer dayNum) {
        ClassRooms classRooms = new ClassRooms();
        classRooms.setTnId(tnId);
        classRooms.setCreateDate(System.currentTimeMillis());
        classRooms.setExecutiveNumber(classRoomNum);
        classRooms.setDayNumber(dayNum);
        classRooms.setGradeId(gradeId);
        return (exiClassRoomDAO.insertClassRoom(classRooms) > 0 ? true : false);
    }

    /**
     * 新增教室设置
     * @param tnId   租户ID
     * @param nums
     * @return
     */
    @Override
    public boolean addClassRoom(Integer tnId, String nums,Integer maxNum) {

        boolean result = false;

        if (tnId > 0 && !StringUtils.isBlank(nums)&&maxNum>0) {

            List<String> idsList = ParamsUtils.idsSplit(nums);

            List<ClassRooms> classRoomsList = new ArrayList<>();

            if (idsList == null)
                return false;
            for (int i = 0; i < idsList.size(); i++) {
                String row = idsList.get(i);
                String[] rowArr = row.split(ParamsUtils.CLASSROOM_GRADE_COMBIN_CHAR);

                String[] number=rowArr[1].split(ParamsUtils.CLASSROOM_NUMBER_COMBIN_CHAR);

                ClassRooms classRooms = new ClassRooms();
                classRooms.setTnId(tnId);
                classRooms.setCreateDate(System.currentTimeMillis());
                classRooms.setGradeId(Integer.valueOf(rowArr[0]));
                classRooms.setExecutiveNumber(Integer.valueOf(number[0]));
                classRooms.setDayNumber(Integer.valueOf(number[1]));
                classRoomsList.add(classRooms);
            }

            Integer addResu = exiClassRoomDAO.addClassRoom(classRoomsList);
            result = addResu > 0 ? true : false;

            ClassRoomSetting classRoomSetting=new ClassRoomSetting();
            classRoomSetting.setCreateDate(System.currentTimeMillis());
            classRoomSetting.setMaxNumber(maxNum);
            classRoomSetting.setTnId(tnId);
            iClassRoomSettingDAO.insert(classRoomSetting);
        }
        if (result)
            iexTenantService.stepSetting(tnId,false);
        return result;
    }

    /**
     * 更新教室
     * @param num 教室数量
     * @param gid 年级ID
     * @param cid 教室标识
     * @return
     */
    @Override
    public boolean updateClassRoom(Integer num, Integer dNum ,Integer gid,Integer cid) {

        ClassRooms classRooms = new ClassRooms();
        classRooms.setGradeId(gid);
        classRooms.setExecutiveNumber(num);
        classRooms.setDayNumber(dNum);
        classRooms.setId(cid);
        return (iClassRoomsDAO.update(classRooms) > 0 ? true : false);
    }

    @Override
    public boolean updateClassRoomSetting(Integer maxNum,Integer cid,Integer tnId) {

        ClassRoomSetting classRoomSetting=new ClassRoomSetting();
        classRoomSetting.setMaxNumber(maxNum);
        classRoomSetting.setTnId(tnId);
        classRoomSetting.setId(cid);
        return (iClassRoomSettingDAO.update(classRoomSetting) > 0 ? true : false);
    }
    @Override
    public ClassRoomSetting getClassRoomSetting(Integer tnId){
        return  iClassRoomSettingDAO.findOne("tn_Id",tnId,"id","asc");
    }

    /**
     * 删除教室
     * @param ids 教室标识
     * @return
     */
    @Override
    public boolean removeClassRoom(String ids) {
        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;
        return (exiClassRoomDAO.removeClassRooms(idsList) > 0);
    }


    /**
     * 新增教室设置
     * @return
     */
    public boolean insertClassRoom(ClassRooms classRooms) {
        return (exiClassRoomDAO.insertClassRoom(classRooms) > 0);
    }

    /**
     * 教室排序
     * @param tnId 租户ID
     * @param ids  排序集
     * @return
     */
    @Override
    public boolean sortRoomOrderUpdate(Integer tnId,String ids){
        LOGGER.info("===============升学率排序 S==============");
        LOGGER.info("ids:" + ids);

        boolean result = false;

        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;

        List<ClassRooms> classRoomses = new ArrayList<ClassRooms>();

        for (int i = 0; i < idsList.size(); i++) {
            Map map = new HashMap();
            map.put("id", idsList.get(i));
            map.put("tnId",tnId);
            ClassRooms classRooms= iClassRoomsDAO.queryOne(map, "id", "asc");
            if (classRooms == null)
                return false;
            classRooms.setRoomOrder(i);
            classRoomses.add(classRooms);
        }
        Integer sortResult = exiClassRoomDAO.sortRoomOrderUpdate(classRoomses);
        result = sortResult > 0 ? true : false;

        LOGGER.info("===============升学率 E==============");

        return result;
    }
}
