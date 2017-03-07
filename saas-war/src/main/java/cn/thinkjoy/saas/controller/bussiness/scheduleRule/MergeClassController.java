package cn.thinkjoy.saas.controller.bussiness.scheduleRule;

import cn.thinkjoy.saas.dao.bussiness.ICourseManageDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.dto.MergeClassInfoDto;
import cn.thinkjoy.saas.enums.ClassTypeEnum;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
import cn.thinkjoy.saas.service.bussiness.scheduleRule.IMergeClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/12/5.
 * 合班
 */
@Controller
@RequestMapping("mergeClassController")
public class MergeClassController {

    @Autowired
    private IMergeClass iMergeClass;

    @Autowired
    private IEXScheduleBaseInfoService exScheduleBaseInfoService;

    @Autowired
    private ICourseManageDAO iCourseManageDAO;

    @RequestMapping("addMergeInfo")
    @ResponseBody
    public Map<String,Object> addMergeInfo(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId,
                                           @RequestParam("courseId")String courseId,
                                           @RequestParam("classIds")String classIds,
                                           @RequestParam("classType")String classType){
        Map<String,Object> resultMap=new HashMap<>();
        if (!(StringUtils.isNotBlank(classIds)&&classIds.contains(","))){
            resultMap.put("massge", "请选择至少两个个班级");
            return resultMap;
        }
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        paramMap.put("courseId",courseId);
        paramMap.put("classIds",classIds);
        paramMap.put("classType", ClassTypeEnum.getCode(classType)==0?0:1);
        iMergeClass.insertMergeInfo(paramMap);
        return resultMap;
    }


    @RequestMapping("getMergeInfo")
    @ResponseBody
    public Map<String,Object> getMergeInfo(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId,
                                           @RequestParam("grade")String grade){
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("mergeClassInfoList",iMergeClass.selectMergeInfo(tnId,null,null,taskId));
        return resultMap;
    }

    @RequestMapping("deleteMergeInfo")
    @ResponseBody
    public Map<String,Object> deleteMergeInfo(@RequestParam("id")String id){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("id",id);
        iMergeClass.deleteMergeInfo(paramMap);
        Map<String,Object> resultMap=new HashMap<>();
        return resultMap;
    }

    @RequestMapping("getClassDtoByCourse")
    @ResponseBody
    public Map<String,Object> getClassDtoByCourse(@RequestParam("tnId") String tnId,
                                 @RequestParam("taskId") String taskId,
                                 @RequestParam("courseId") String courseId,
                                 @RequestParam("courseName") String courseName,
                                 @RequestParam("grade") String grade){
        Map<String,Object> resultMap = new HashMap<>();
        //根据tnId,courseId,gradeId查找classType
        Map<String,Object> condition=new HashMap<>();
        condition.put("tnId",tnId);
        condition.put("courseBaseId",courseId);
        condition.put("gradeId",grade);
        CourseManage courseManage=iCourseManageDAO.queryOne(condition, null, null);
        if (courseManage==null){
            resultMap.put("msg","saas_course_manage中没有找到课程类型");
            return resultMap;
        }
        String classType=courseManage.getCourseType();

        List<Map<String,Object>> maps = exScheduleBaseInfoService.getClassBaseDtosByCourse(
                Integer.valueOf(tnId),
                Integer.valueOf(grade),
                courseName,classType
        );

        List<MergeClassInfoDto> mergeClassInfoDtoList = iMergeClass.selectMergeInfo(tnId,courseId,classType,taskId);
        for (Map map : maps){
            map.put("isMerge","0");
            for (MergeClassInfoDto mergeClassInfoDto:mergeClassInfoDtoList){
                boolean flag=false;
                String[] classIds=mergeClassInfoDto.getClassIds().split(",");
                for(String classId:classIds){
                    if(classId.equals(String.valueOf(map.get("id")))){
                        map.put("isMerge","1");
                        flag=true;
                        break;
                    }
                }
                if (flag){
                    break;
                }
            }
        }

        resultMap.put("classBaseDtoList",maps);
        return resultMap;
    }


}
