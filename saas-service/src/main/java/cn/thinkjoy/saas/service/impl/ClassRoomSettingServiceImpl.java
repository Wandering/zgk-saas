package cn.thinkjoy.saas.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.saas.dao.IClassRoomSettingDAO;
import cn.thinkjoy.saas.domain.ClassRoomSetting;
import cn.thinkjoy.saas.service.IClassRoomSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by douzy on 17/1/18.
 */

@Service("ClassRoomSettingServiceImpl")
public class ClassRoomSettingServiceImpl extends AbstractPageService<IBaseDAO<ClassRoomSetting>, ClassRoomSetting> implements IClassRoomSettingService<IBaseDAO<ClassRoomSetting>,ClassRoomSetting> {

    @Autowired
    private IClassRoomSettingDAO iClassRoomSettingDAO;

    @Override
    public IBaseDAO<ClassRoomSetting> getDao() {
        return iClassRoomSettingDAO;
    }
}
