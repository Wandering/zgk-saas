package cn.thinkjoy.saas.service.impl.bussiness.scheduleRule;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IJwCourseGapRuleDAO;
import cn.thinkjoy.saas.dao.bussiness.scheduleRule.MergeClassDAO;
import cn.thinkjoy.saas.dto.MergeClassInfoDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.bussiness.scheduleRule.IMergeClass;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IJwCourseGapRuleDAO jwCourseGapRuleDAO;

    @Override
    public void insertMergeInfo(Map<String, Object> map) {

        Integer count = mergeClassDAO.checkIsRepeat(map);
        if(count != null && count > 0){
            ExceptionUtil.throwException(ErrorCode.MEEGE_CLASS_REPEAT);
        }
        mergeClassDAO.insertMergeInfo(map);
    }

    @Override
    public List<MergeClassInfoDto> selectMergeInfo(String tnId,String courseId,String taskId) {

        Map<String,String> paramMap = Maps.newHashMap();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        paramMap.put("courseId",courseId);
        List<MergeClassInfoDto> dtos = mergeClassDAO.selectMergeInfo(paramMap);

        for (MergeClassInfoDto dto : dtos){
            String [] classIds = dto.getClassIds().split(",");
            String classNames = "";
            for(String classId:classIds){

                paramMap.clear();
                paramMap.put("tableName", ParamsUtils.combinationTableName(
                        dto.getClassType()==1?Constant.CLASS_ADM:Constant.CLASS_EDU,
                        Integer.valueOf(tnId))
                );
                paramMap.put("searchKey","id");
                paramMap.put("searchValue",classId);

                List<Map<String,Object>> maps = jwCourseGapRuleDAO.queryClassList(paramMap);
                classNames = classNames + "、" + maps.get(0).get("name");
            }
            if (classNames.length()>0) {
                dto.setClassNames(classNames.substring(1));
            }
        }
        return dtos;
    }

    @Override
    public void deleteMergeInfo(Map<String, Object> map) {
        mergeClassDAO.deleteMergeInfo(map);
    }
}
