package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.JwCourseBaseInfo;
import cn.thinkjoy.saas.domain.JwTeacherBaseInfo;

import java.util.List;

/**
 * Created by douzy on 16/12/8.
 */
public interface IEXCourseBaseInfoDAO {
    /**
     * 同步教室基础信息
     * @param jwTeacherBaseInfoList
     * @return
     */
    Integer syncCourseInfo(List<JwCourseBaseInfo> jwCourseBaseInfos);
}
