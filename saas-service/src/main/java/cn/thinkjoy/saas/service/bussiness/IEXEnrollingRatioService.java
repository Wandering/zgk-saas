package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.EnrollingRatio;

/**
 * Created by douzy on 16/10/25.
 */
public interface IEXEnrollingRatioService {

    /**
     * 新增升学率
     * @param enrollingRatio
     * @return
     */
    boolean addEnrollingRatio(EnrollingRatio enrollingRatio);

    /**
     * 更新升学率
     * @param enrollingRatio
     * @return
     */
    boolean updateEnrollingRatio(EnrollingRatio enrollingRatio);

    /**
     * 升学率排序
     *
     * @param ids 需排序的对象集
     * @return
     */
     boolean sortRatioUpdate(Integer tnId,String ids);

}
