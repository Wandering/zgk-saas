package cn.thinkjoy.saas.service.impl.bussiness.scheduleRule;

import cn.thinkjoy.saas.dao.IJwClassBaseInfoDAO;
import cn.thinkjoy.saas.dao.bussiness.scheduleRule.MergeClassDAO;
import cn.thinkjoy.saas.domain.JwClassBaseInfo;
import cn.thinkjoy.saas.dto.MergeClassInfoDto;
import cn.thinkjoy.saas.service.bussiness.scheduleRule.IMergeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 */
@Service("mergeClassImpl")
public class MergeClassImpl implements IMergeClass {

    @Autowired
    private MergeClassDAO mergeClassDAO;

    @Autowired
    private IJwClassBaseInfoDAO iJwClassBaseInfoDAO;

    @Override
    public void insertMergeInfo(Map<String, Object> map) {
        mergeClassDAO.insertMergeInfo(map);
    }

    @Override
    public List<MergeClassInfoDto> selectMergeInfo(Map<String, Object> map) {
        Map<String,Object> condition=new HashMap<>();
        condition.put("grade",map.get("grade"));
        List<JwClassBaseInfo> jwCourseBaseInfoList=iJwClassBaseInfoDAO.queryList(condition,null,null);
        Map<String,Object> classNameMap=new HashMap<>();
        for (JwClassBaseInfo jwClassBaseInfo:jwCourseBaseInfoList){
            classNameMap.put(jwClassBaseInfo.getId().toString(),jwClassBaseInfo.getClassName());
        }
        List<MergeClassInfoDto> mergeClassInfoDtoList=mergeClassDAO.selectMergeInfo(map);
        for (MergeClassInfoDto mergeClassInfoDto:mergeClassInfoDtoList){
            String[] classIds=mergeClassInfoDto.getClassIds().split(",");
            String classNames="";
            for(String classId:classIds){
                classNames=classNames+"、"+classNameMap.get(classId);
            }
            if (classNames.length()>0) {
                mergeClassInfoDto.setClassNames(classNames.substring(1));
            }
        }
        return mergeClassInfoDtoList;
    }

    @Override
    public void deleteMergeInfo(Map<String, Object> map) {
        mergeClassDAO.deleteMergeInfo(map);
    }
}
