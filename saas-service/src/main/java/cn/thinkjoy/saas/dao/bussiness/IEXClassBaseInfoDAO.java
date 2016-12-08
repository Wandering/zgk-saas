package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.JwClassBaseInfo;

import java.util.List;

/**
 * Created by douzy on 16/12/8.
 */
public interface IEXClassBaseInfoDAO {
    /**
     * 同步教室基础信息
     * @param jwClassBaseInfos
     * @return
     */
    Integer syncClassInfo(List<JwClassBaseInfo> jwClassBaseInfos);
}
