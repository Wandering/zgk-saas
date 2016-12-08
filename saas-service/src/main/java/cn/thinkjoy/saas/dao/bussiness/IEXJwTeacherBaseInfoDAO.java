package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.JwTeacherBaseInfo;

import java.util.List;

/**
 * Created by douzy on 16/12/7.
 */
public interface IEXJwTeacherBaseInfoDAO {
    /**
     * 同步教室基础信息
     * @param jwTeacherBaseInfoList
     * @return
     */
    Integer syncTeacherInfo(List<JwTeacherBaseInfo> jwTeacherBaseInfoList);
}
