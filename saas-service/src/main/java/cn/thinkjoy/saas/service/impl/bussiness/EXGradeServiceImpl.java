package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.dao.IGradeDAO;
import cn.thinkjoy.saas.dao.bussiness.EXIGradeDAO;
import cn.thinkjoy.saas.domain.EnrollingRatio;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import cn.thinkjoy.saas.service.bussiness.IEXTenantService;
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
    IEXTenantService iexTenantService;

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
        if (result)
            iexTenantService.stepSetting(tnId,false);


        return result;
    }



    /**
     * 根据字段查找一个年级对象
     *
     * @param map
     * @return
     */
    @Override
    public List<Grade> selectGradeByTnId(Map map) {
        return exiGradeDAO.selectGradeByTnId(map);
    }

    /**
     * 年级拖动排序
     * @param ids  需要调换的两个ID
     * @return
     */
    @Override
    public boolean gradeSortUpdate(Integer tnId,String ids) {
        LOGGER.info("===============年级排序 S==============");
        LOGGER.info("ids:" + ids);

        boolean result = false;

        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;

        List<Grade> grades = new ArrayList<Grade>();

        for (int i = 0; i < idsList.size(); i++) {
            Map map = new HashMap();
            map.put("id", idsList.get(i));
            map.put("tnId",tnId);
            Grade grade = iGradeDAO.queryOne(map, "id", "asc");
            if (grade == null)
                return false;
            grade.setgOrder(i);
            grades.add(grade);
        }
        Integer sortResult = exiGradeDAO.gradeSortUpdate(grades);
        result = sortResult > 0 ? true : false;

        LOGGER.info("===============年级 E==============");

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

    /**
     * 年级批量删除
     * @param ids 年级标识
     * @return
     */
    @Override
    public boolean removeGrades(String ids) {
        List<String> idsList = ParamsUtils.idsSplit(ids);
        if (idsList == null)
            return false;
        return (exiGradeDAO.removeGrades(idsList) > 0);
    }
}
