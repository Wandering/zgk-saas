package cn.thinkjoy.saas.service.impl.bussiness.reform;

import cn.thinkjoy.saas.dao.bussiness.reform.TrineDAO;
import cn.thinkjoy.saas.dto.*;
import cn.thinkjoy.saas.service.bussiness.reform.ITrineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/11/1.
 */
@Service("trineServiceImpl")
public class TrineServiceImpl implements ITrineService {

    @Autowired
    private TrineDAO trineDAO;

    @Override
    public Map<String,Object> selectEnrollingNumberByBatch(Map map) {
        List<TrineDto> trineDtoList=trineDAO.selectEnrollingNumberByBatch(map);
        int count=0;
        for (TrineDto trineDto:trineDtoList){
            count=count+Integer.valueOf(trineDto.getMajorNumber());
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("enrollingNumberByBatch", trineDtoList);
        returnMap.put("count",count);
        return returnMap;
    }

    @Override
    public List<EnrollingInfoDto> selectEnrollingInfo(Map map) {
        return trineDAO.selectEnrollingInfo(map);
    }

    @Override
    public int selectEnrollingInfoCount(Map map) {
        return trineDAO.selectEnrollingInfoCount(map);
    }
}
