package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.domain.DataDict;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.*;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import cn.thinkjoy.saas.core.GradeConstant;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/12/10.
 */
@Controller
@RequestMapping("/grade")
public class GradeController {


    @Autowired
    private IGradeService gradeService;
    @Autowired
    private EXIGradeService exiGradeService;
    @Autowired
    private IDataDictService dataDictService;

    /**
     *  添加年级信息
     * @param gradesStr
     *      grades
     *      [grade|id|gradeType|year,grade|id|gradeType|year,grade|id|gradeType|year]
     *      eg:[1|1|1|2016,2|2|3|2015,3|3|3|2014]
     * @return
     */
    @RequestMapping(value = "insertGrade",method = RequestMethod.POST)
    @ResponseBody
    public boolean insertGrade(@RequestParam String gradesStr){

        String[] grades = new String[0];
        try {
            grades = JSON.parse(gradesStr,String[].class);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * 数据校验
         */
        if (grades.length != GradeConstant.DEFULT_GRADE_NUM){
            throw new BizException(ErrorCode.DATA_FORMAT_ERROR.getCode(),ErrorCode.DATA_FORMAT_ERROR.getMessage());
        }

        String tnId = UserContext.getCurrentUser().getTnId();
        Grade grade = null;
        String[] params = null;
        List<Grade> gradeList = new ArrayList<>(GradeConstant.DEFULT_GRADE_NUM);
        for (String s : grades){
            grade = new Grade();
            params = s.split("\\|");

            /**
             * 数据校验
             */
            if (params.length != GradeConstant.DEFULT_GRADE_ADD_PARAM_NUM){
                throw new BizException(ErrorCode.DATA_FORMAT_ERROR.getCode(),ErrorCode.DATA_FORMAT_ERROR.getMessage());
            }

            grade.setTnId(Integer.valueOf(tnId));

            grade.setGradeCode(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_GRADE_POSITION]));
            grade.setGorder(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_GRADE_POSITION]));
            grade.setId(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_ID_POSITION]));
            grade.setClassType(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_CLASSTYPE_POSITION]));
            grade.setYear(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_YEAR_POSITION]));

            gradeList.add(grade);
        }
        exiGradeService.updateGrade(gradeList);
        return true;
    }


    /**
     * 更新年级信息
     * @param gradesStr
     * grades
     *      [grade|id|gradeType|year,grade|id|gradeType|year,grade|id|gradeType|year]
     *      eg:[1|1|1|2016,2|2|3|2015,3|3|3|2014]
     * @return
     */
    @RequestMapping(value = "updateGrade",method = RequestMethod.POST)
    @ResponseBody
    public boolean updateGrade(@RequestParam String gradesStr){
        String[] grades = new String[0];
        try {
            grades = JSON.parse(gradesStr,String[].class);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * 数据校验
         */
        if (grades.length != GradeConstant.DEFULT_GRADE_NUM){
            throw new BizException(ErrorCode.DATA_FORMAT_ERROR.getCode(),ErrorCode.DATA_FORMAT_ERROR.getMessage());
        }

        String tnId = UserContext.getCurrentUser().getTnId();
        Grade grade = null;
        String[] params = null;
        List<Grade> gradeList = new ArrayList<>(GradeConstant.DEFULT_GRADE_NUM);
        for (String s : grades){
            grade = new Grade();
            params = s.split("\\|");

            /**
             * 数据校验
             */
            if (params.length != GradeConstant.DEFULT_GRADE_UPDATE_PARAM_NUM){
                throw new BizException(ErrorCode.DATA_FORMAT_ERROR.getCode(),ErrorCode.DATA_FORMAT_ERROR.getMessage());
            }

            grade.setTnId(Integer.valueOf(tnId));

            grade.setGradeCode(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_GRADE_POSITION]));
            grade.setId(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_ID_POSITION]));
            grade.setClassType(Integer.valueOf(params[GradeConstant.DEFULT_PARAM_CLASSTYPE_POSITION]));
            gradeList.add(grade);
        }
        exiGradeService.updateGrade(gradeList);
        return true;
    }

    /**
     * 获取年级信息
     * @return
     */
    @RequestMapping(value = "getGrade",method = RequestMethod.GET)
    @ResponseBody
    public List<Grade> getGrade(){
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());

        List<Grade> gradeList = gradeService.findList("tnId",tnId);
        if (gradeList.size() == 0){
            gradeList =  exiGradeService.init(tnId);
        }
        return gradeList;
    }


    /**
     * 获取年级类型字典
     * @return
     */
    @RequestMapping(value = "getGradeTypeDict",method = RequestMethod.GET)
    @ResponseBody
    public List<DataDict> getGradeTypeDict(){

        List<DataDict> dictList = dataDictService.findList("type",GradeConstant.GRADE_TYPE);
        return dictList;
    }

    /**
     * 检测租户是否存在教学班
     * @return
     */
    @RequestMapping(value = "getTnClassType",method = RequestMethod.GET)
    @ResponseBody
    public Map getTnClassType(){

        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        List<Grade> gradeList = gradeService.findList("tnId",tnId);
        Map<String,Boolean> map = Maps.newHashMap();
        boolean flag = false;
        for(Grade grade : gradeList){
            if(grade.getClassType() == 2){
                flag = true;
            }
        }
        map.put("result",flag);

        return map;
    }
}
