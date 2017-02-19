package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.IEnrollingRatioDAO;
import cn.thinkjoy.saas.dao.bussiness.EXIEnrollingRatioDAO;
import cn.thinkjoy.saas.domain.EnrollingRatio;
import cn.thinkjoy.saas.service.bussiness.IEXEnrollingRatioService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/25.
 */
@Service("EXEnrollingRatioServiceImpl")
public class EXEnrollingRatioServiceImpl implements IEXEnrollingRatioService {

    private static final Logger LOGGER= LoggerFactory.getLogger(EXEnrollingRatioServiceImpl.class);
    @Resource
    IEnrollingRatioDAO iEnrollingRatioDAO;
    @Resource
    EXIEnrollingRatioDAO exiEnrollingRatioDAO;

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

    /**
     * 升学率排序
     *
     * @return
     */
    @Override
    public boolean sortRatioUpdate(Integer tnId,String ids) {
        LOGGER.info("===============升学率排序 S==============");
        LOGGER.info("ids:" + ids);

        boolean result = false;

        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;

        List<EnrollingRatio> enrollingRatios = new ArrayList<EnrollingRatio>();

        for (int i = 0; i < idsList.size(); i++) {
            Map map = new HashMap();
            map.put("id", idsList.get(i));
            map.put("tnId",tnId);
            EnrollingRatio enrollingRatio = iEnrollingRatioDAO.queryOne(map, "id", "asc");
            if (enrollingRatio == null)
                return false;
            enrollingRatio.setRatioOrder(i);
            enrollingRatios.add(enrollingRatio);
        }
        Integer sortResult = exiEnrollingRatioDAO.sortRatioUpdate(enrollingRatios);
        result = sortResult > 0 ? true : false;

        LOGGER.info("===============升学率 E==============");

        return result;
    }
}

