package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.EnrollingRatio;

import java.util.List;

/**
 * Created by douzy on 16/11/28.
 */
public interface EXIEnrollingRatioDAO {

    /**
     * 升学率排序
     *
     * @param enrollingRatios 需排序的对象集
     * @return
     */
    Integer sortRatioUpdate(List<EnrollingRatio> enrollingRatios);
}
