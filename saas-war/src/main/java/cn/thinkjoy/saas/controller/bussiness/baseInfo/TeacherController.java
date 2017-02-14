package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.GradeConstant;
import cn.thinkjoy.saas.domain.DataDict;
import cn.thinkjoy.saas.domain.Grade;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.IDataDictService;
import cn.thinkjoy.saas.service.IGradeService;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/teacher")
public class TeacherController {

    /**
     * 通过科目查询年级
     * @param subject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryGradeBySubject")
    public Map<String,Object> getGradeBySubject(@RequestParam String subject){
        return null;
    }

//    /**
//     * 通过年级查询最大带班数
//     * @param subject
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "queryBySubject")
//    public Map<String,Object> getGradeBySubject(@RequestParam String subject){
//        return null;
//    }
}
