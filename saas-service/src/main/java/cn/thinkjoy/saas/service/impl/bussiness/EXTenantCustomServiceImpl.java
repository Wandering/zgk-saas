package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.IGradeDAO;
import cn.thinkjoy.saas.dao.bussiness.EXIGradeDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXTeantCustomDAO;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.service.bussiness.IEXTenantCustomService;
import cn.thinkjoy.saas.service.common.EnumUtil;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by douzy on 16/10/25.
 */
@Service("EXTenantCustomServiceImpl")
public class EXTenantCustomServiceImpl implements IEXTenantCustomService {

    @Resource
    IEXTeantCustomDAO iexTeantCustomDAO;

    @Resource
    EXIGradeDAO exiGradeDAO;


    /**
     * 租户自定义表头数据添加
     * @param type 模块分类
     * @param tnId 租户ID
     * @param teantCustoms key&value
     * @return
     */
    @Override
    public boolean addTeantCustom(String type ,Integer tnId,List<TeantCustom> teantCustoms) {

        if (tnId <= 0)
            return false;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return false;

        Integer result = iexTeantCustomDAO.insertTenantCustom(tableName, teantCustoms);

        return (result > 0 ? true : false);
    }

    /**
     *  租户自定义表头数据更新
     * @param type 模块分类
     * @param tnId 租户ID
     * @param pri 租户ID
     * @param teantCustoms key&value
     * @return
     */
    @Override
    public boolean modifyTeantCustom(String type,Integer tnId,Integer pri,List<TeantCustom> teantCustoms) {

        if (tnId <= 0 || pri <= 0)
            return false;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return false;

        Integer result = iexTeantCustomDAO.updateTenantCustom(tableName, pri, teantCustoms);

        return (result > 0 ? true : false);
    }


    /**
     *  租户自定义表头数据删除
     * @param type 模块分类
     * @param tnId 租户ID
     * @param pri 租户ID
     * @return
     */
    @Override
    public boolean removeTenantCustom(String type,Integer tnId,Integer pri) {

        if (tnId <= 0 || pri <= 0)
            return false;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return false;

        Integer result = iexTeantCustomDAO.removeTenantCustom(tableName, pri);

        return (result > 0 ? true : false);
    }

    /**
     * 查询租户自定义表头数据
     * @param type 模块分类
     * @param tnId 租户ID
     * @return
     */
    @Override
    public List<LinkedHashMap<String,Object>> getTenantCustom(String type,Integer tnId) {
        if (tnId <= 0)
            return null;

        String tableName = ParamsUtils.combinationTableName(type, tnId);

        if (StringUtils.isBlank(tableName))
            return null;

        List<LinkedHashMap<String, Object>> tenantCustoms = iexTeantCustomDAO.getTenantCustom(tableName);


        return tenantCustoms;
    }

    /**
     * excel内添加select
     * @param columnNames
     * @return
     */
    @Override
    public List<Map<Integer, Object>> isExcelAddSelect(Integer tnId ,String[] columnNames) {
        List<Map<Integer,Object>> lockSelectList = new ArrayList<>();
        if (columnNames.length > 0) {
            int i = 0;
            for (String str : columnNames) {
                Map<Integer, Object> map = new HashMap<>();

                Object value = null;
                switch (str) {
                    case EnumUtil.CLASS_MAJOR_TYPE: //班级类型
                        value = EnumUtil.CLASS_TYPE_ARR;
                        break;
                    case EnumUtil.CLASS_GRADE://所属年级
                        Map gradeMap = new HashMap();
                        gradeMap.put("tnId", tnId);
                        List<Grade> grades = exiGradeDAO.selectGradeByTnId(gradeMap);
                        value = converGradesArr(grades);
                        break;
                }

                if(!(value==null)) {
                    map.put(i, value);
                    lockSelectList.add(map);
                }
                i++;
            }
        }
        return lockSelectList;
    }

    private String[] converGradesArr(List<Grade> grades) {
        String[] arr=new String[grades.size()];

        int i=0;
        for(Grade grade:grades) {
            arr[i] = grade.getGrade();
            i++;
        }

        return arr;
    }
}
