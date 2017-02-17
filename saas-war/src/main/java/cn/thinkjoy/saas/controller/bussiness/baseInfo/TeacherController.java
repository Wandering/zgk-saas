package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.service.common.EduClassUtil;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.dto.CourseManageDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.enums.GradeTypeEnum;
import cn.thinkjoy.saas.service.IGradeService;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import cn.thinkjoy.saas.service.bussiness.EXITenantConfigInstanceService;
import cn.thinkjoy.saas.service.bussiness.IEXCourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by yangyongping on 2016/12/10.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private IGradeService gradeService;
    @Autowired
    private EXIGradeService exGradeService;
    @Autowired
    private EXITenantConfigInstanceService exiTenantConfigInstanceService;
    @Autowired
    private IEXCourseManageService iexCourseManageService;

    /**
     * 查询科目
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "querySubject")
    public Map<String, Object> querySubject() {
        Map<String, Object> rtnMap = new HashMap();
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        List<CourseManageDto> courseManageDtos = iexCourseManageService.getCourseByTnId(tnId);
        if (courseManageDtos.size() == 0) {
            throw new BizException(ErrorCode.SUBJECT_NULL.getCode(),ErrorCode.SUBJECT_NULL.getMessage());
        }
        Set<String> subjects = new HashSet<>();
        Map<String,Object> map;
        for (CourseManageDto courseManageDto : courseManageDtos) {
            subjects.add(courseManageDto.getCourseBaseName());
        }
        rtnMap.put("subject", subjects);
        return rtnMap;
    }

    /**
     * 通过科目查询年级
     * <p>
     * 模板中及单条上传时均为选框形式填写，选项内容根据选择的“所教科目”的不同而变化，来源于课程设置中设置的该课程的开课年级
     * 例：课程设置中设置的物理的开课年级为：高二年级、高三年级，
     * 若添加教师信息时，选择所教科目为“物理”，则“所带年级”选框的选项为：高二年级、高三年级
     *
     * @param subject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryGradeBySubject")
    public Map<String, Object> getGradeBySubject(@RequestParam String subject) {
        Map<String, Object> rtnMap = new HashMap();
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        List<CourseManageDto> courseManageDtos = iexCourseManageService.getCourseGradeByTnIdAndSubject(tnId,subject);
        if (courseManageDtos.size() == 0) {
            throw new BizException(ErrorCode.SUBJECT_NULL.getCode(),ErrorCode.SUBJECT_NULL.getMessage());
        }
        Set<Integer> gradeCodes = new HashSet<>(3);
        for (CourseManageDto courseManageDto : courseManageDtos) {
            gradeCodes.add(courseManageDto.getGradeId());
        }
        List<Grade> grades = exGradeService.getGradeByTnIdAndGradeCode(tnId,gradeCodes);
        if (grades.size() == 0) {
            throw new BizException(ErrorCode.GRADE_FORMAT_ERROR.getCode(),ErrorCode.GRADE_FORMAT_ERROR.getMessage());
        }
        for (Grade grade : grades){
            grade.setGorder(null);
            grade.setTnId(null);
            grade.setYear(null);
            grade.setId(null);
            grade.setClassType(null);
        }
        rtnMap.put("grade", grades);

        return rtnMap;
    }


    /**
     * 通过年级查询最大带班数
     * <p>
     * 模板中及单条上传时均为选框形式填写，
     * 选项内容根据选择的“所教科目”及“所带年级”的不同而变化。
     * 若该年级班级类型中不存在教学班，则此处最大带班数为该年级所有行政班数目。
     * 若所选的“所带年级”不存在教学班，则选项的最大值为所选的“所带年级”的所有行政班级，
     * 例该年级共9个行政班，则此选项内容为：1，2，3，4，5，6，7，8，9
     * 若所选年级存在教学班，且选择的所教科目为：
     * 该年级教学班设置中设置的“教学科目”时，
     * 则选项的最大值为教学科目为此科目的教学班的数目，
     * 教学科目为的物理走读班的数量为3个时，
     * 最大带班数选框中的选项为：1，2，3.
     * 若所选年级存在教学班，
     * 但选择的所教科目不是教学班设置中设置的“教学科目”时，
     * 则选项的最大值为所选的“所带年级”的所有行政班级
     *
     * @param gradeCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryMaxClassByGradeAndSubject")
    public Map<String, Object> getMaxClassBySubject(@RequestParam String gradeCode,
                                                    @RequestParam String subject) {
        Map<String, Object> rtnMap = new HashMap();

        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        Grade gradeServiceOne = getGradeByGradeCodeAndTnId(tnId, gradeCode);
        //判定是否存在教学班
        Integer gradeType = gradeServiceOne.getClassType();
        if (gradeType == null)
            throw new BizException(ErrorCode.GRADE_FORMAT_ERROR.getCode(), ErrorCode.GRADE_FORMAT_ERROR.getMessage());
        String grade = gradeServiceOne.getGrade();
        boolean isExistTeaching = GradeTypeEnum.Teaching.getCode().equals(gradeType);
        boolean isMove = EduClassUtil.isEduSubject(subject);
        int classCount = 0;
        if (isExistTeaching && isMove) {
            //是教学班+是走班课程
            classCount = exiTenantConfigInstanceService.countBySubjectAndGrade(tnId, grade, subject, Constant.CLASS_EDU);
        } else {
            //是教学班+不是走班课程
            classCount = exiTenantConfigInstanceService.countBySubjectAndGrade(tnId, grade, subject, Constant.CLASS_ADM);
            //不教学班+是走班课程
        }

        rtnMap.put("maxClass", classCount);
        return rtnMap;
    }

    /**
     * 模板中及单条上传时均为多选框。根据“所教科目”、“所带年级”显示该科目在该年级所有的开课班级，添加教师弹层中：未选定“所教科目”及“所带年级”前不展示“所带班级”选框。
     * 若所选的“所带年级”不存在教学班，则此处的选项为：所选的“所带年级”的所有行政班级
     * 若所选年级存在教学班，且选择的所教科目为：教学班设置中设置的“教学科目”时，则此处的选项为：教学科目为此科目的所有教学班级
     * 若所选年级存在教学班，但选择的所教科目不是教学班设置中设置的“教学科目”时，则此处的选项为：所选的“所带年级”的所有行政班级
     * 最大带班数填的是n个，则所带班级最多能勾选n个
     *
     * @param gradeCode
     * @param subject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryClassByGradeCodeAndSubject")
    public Map<String, Object> getClassByGradeCodeAndSubject(@RequestParam String gradeCode,
                                                             @RequestParam String subject) {
        Map<String, Object> rtnMap = new HashMap();
        int tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        Grade gradeServiceOne = getGradeByGradeCodeAndTnId(tnId, gradeCode);
        //判定是否存在教学班
        int gradeType = gradeServiceOne.getClassType();
        String grade = gradeServiceOne.getGrade();
        boolean isExistTeaching = GradeTypeEnum.Teaching.getCode().equals(gradeType);
        boolean isMove = EduClassUtil.isEduSubject(subject);
        List<LinkedHashMap<String, Object>> classes = new ArrayList();
        if (isExistTeaching && isMove) {
            //是教学班+是走班课程
            classes = exiTenantConfigInstanceService.getBySubjectAndGrade(tnId, grade, subject, Constant.CLASS_EDU);
        } else {
            //是教学班+不是走班课程
            classes = exiTenantConfigInstanceService.getBySubjectAndGrade(tnId, grade, subject, Constant.CLASS_ADM);
            //不教学班+是走班课程
        }
        List<Map<String, Object>> rtnList = new ArrayList<>();
        Map<String, Object> map;
        for (Map<String, Object> link : classes) {
            map = new HashMap<>();
            map.put("className", link.get("class_name"));
            rtnList.add(map);
        }

        rtnMap.put("class", rtnList);
        return rtnMap;
    }

    private Grade getGradeByGradeCodeAndTnId(int tnId, String gradeCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("gradeCode", gradeCode);
        param.put("tnId", tnId);
        Grade gradeServiceOne = (Grade) gradeService.queryOne(param);
        if (gradeServiceOne == null)
            throw new BizException(ErrorCode.GRADE_FORMAT_ERROR.getCode(), ErrorCode.GRADE_FORMAT_ERROR.getMessage());
        return gradeServiceOne;
    }


}
