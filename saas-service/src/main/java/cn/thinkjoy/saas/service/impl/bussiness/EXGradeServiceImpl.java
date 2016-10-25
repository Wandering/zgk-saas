package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.IGradeDAO;
import cn.thinkjoy.saas.dao.bussiness.EXIGradeDAO;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/24.
 */
@Service("EXGradeServiceImpl")
public class EXGradeServiceImpl implements EXIGradeService {

    private static final Logger LOGGER= LoggerFactory.getLogger(EXGradeServiceImpl.class);

    @Resource
    EXIGradeDAO exiGradeDAO;

    @Resource
    IGradeDAO iGradeDAO;


    /**
     * 年级设置
     * @param tnId 租户ID
     * @param nums 年级信息
     * @return
     */
    @Override
    public boolean AddGrade(Integer tnId,String nums) {
        boolean result = false;
        if (tnId > 0 && !StringUtils.isBlank(nums)) {

            List<String> idsList = ParamsUtils.idsSplit(nums);
            if (idsList == null)
                return false;


            List<Grade> grades = new ArrayList<Grade>();

            for (int i = 0; i < idsList.size(); i++) {
                String grade = idsList.get(i);
                Grade g = new Grade();
                g.setTnId(tnId);
                g.setGrade(grade);

                grades.add(g);
            }

            Map delMap = new HashMap();
            delMap.put("tnId", tnId);

            deleteByMap(delMap);

            Integer addResu = exiGradeDAO.addGrade(grades);

            result = addResu > 0 ? true : false;
        }

        return result;
    }



    /**
     * 根据字段查找一个年级对象
     *
     * @param map
     * @return
     */
    @Override
    public Grade selectGradeByTnId(Map map) {
        return exiGradeDAO.selectGradeByTnId(map);
    }

    /**
     * 年级拖动排序
     * @param ids  需要调换的两个ID
     * @return
     */
    @Override
    public boolean gradeSortUpdate(String ids) {
        LOGGER.info("===============年级拖动排序 S==============");
        LOGGER.info("ids:" + ids);

        boolean result = false;

        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;

//        //调换表头顺序 必须保证ids size 为2
//        if (idsList.size() == 2) {
//            List<Grade> grades = new ArrayList<Grade>();
//
//            for (int i = 0; i < idsList.size(); i++) {
//                Map map = new HashMap();
//                map.put("id", idsList.get(i));
//                TenantConfigInstance tenantConfigInstance = iTenantConfigInstanceDAO.queryOne(map, "id", "asc");
//                if (tenantConfigInstance == null)
//                    return false;
//                grades.add(tenantConfigInstance);
//            }
//
//            List<Grade> sortResultTenantConfigInstances = new ArrayList<Grade>();
//            TenantConfigInstance tenantConfigInstanceStart = grades.get(0);
//            TenantConfigInstance tenantConfigInstanceEnd = grades.get(1);
//            //存储一个order
//            Integer temp = tenantConfigInstanceStart.getConfigOrder();
//            //调换另一个对象的order
//            tenantConfigInstanceStart.setConfigOrder(tenantConfigInstanceEnd.getConfigOrder());
//            tenantConfigInstanceStart.setModifyDate(System.currentTimeMillis());
//            //调换temporder
//            tenantConfigInstanceEnd.setConfigOrder(temp);
//            tenantConfigInstanceEnd.setModifyDate(System.currentTimeMillis());
//
//            sortResultTenantConfigInstances.add(tenantConfigInstanceStart);
//            sortResultTenantConfigInstances.add(tenantConfigInstanceEnd);
//
//            Integer sortResult = exiGradeDAO.gradeSortUpdate(sortResultTenantConfigInstances);
//            result = sortResult > 0 ? true : false;
//        }

        LOGGER.info("===============年级拖动排序 E==============");

        return result;
    }

    /**
     * 新增年级
     * @param grade
     * @return
     */
    @Override
    public boolean insertGrade(Integer tnId,String gradeName) {
        Grade grade = new Grade();
        grade.setTnId(tnId);
        grade.setGrade(gradeName);
        grade.setgOrder(0);
        Integer result = iGradeDAO.insert(grade);
        return (result > 0 ? true : false);
    }
    @Override
    public boolean updateGrade(Integer tnId,String gradeName,Integer gid) {
        Grade grade = new Grade();
        grade.setTnId(tnId);
        grade.setGrade(gradeName);
        grade.setgOrder(0);
        grade.setId(gid);
        Integer result = iGradeDAO.update(grade);
        return (result > 0 ? true : false);
    }
    /**
     * 删除年级
     * @param map
     * @return
     */
    @Override
    public Integer deleteByMap(Map map) {
        return exiGradeDAO.deleteByMap(map);
    }
}
