package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.IEnrollingRatioDAO;
import cn.thinkjoy.saas.domain.EnrollingRatio;
import cn.thinkjoy.saas.service.bussiness.IEXEnrollingRatioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by douzy on 16/10/25.
 */
@Service("EXEnrollingRatioServiceImpl")
public class EXEnrollingRatioServiceImpl implements IEXEnrollingRatioService {
    @Resource
    IEnrollingRatioDAO iEnrollingRatioDAO;

    /**
     * 新增升学率
     * @param enrollingRatio
     * @return
     */
    public boolean addEnrollingRatio(EnrollingRatio enrollingRatio) {
        return (iEnrollingRatioDAO.insert(enrollingRatio) > 0 ? true : false);
    }

    /**
     * 更新升学率
     * @param enrollingRatio
     * @return
     */
    public boolean updateEnrollingRatio(EnrollingRatio enrollingRatio){
        return (iEnrollingRatioDAO.update(enrollingRatio) > 0 ? true : false);
    }
}
