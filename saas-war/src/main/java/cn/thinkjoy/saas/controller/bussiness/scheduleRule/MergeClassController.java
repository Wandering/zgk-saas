package cn.thinkjoy.saas.controller.bussiness.scheduleRule;

import cn.thinkjoy.saas.service.bussiness.scheduleRule.IMergeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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

    @RequestMapping("addMergeInfo")
    @ResponseBody
    public Map<String,Object> addMergeInfo(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId,
                                           @RequestParam("courseId")String courseId,
                                           @RequestParam("classIds")String classIds){

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        paramMap.put("courseId",courseId);
        paramMap.put("classIds",classIds);
        iMergeClass.insertMergeInfo(paramMap);
        Map<String,Object> resultMap=new HashMap<>();
        return resultMap;
    }


    @RequestMapping("getMergeInfo")
    @ResponseBody
    public Map<String,Object> getMergeInfo(@RequestParam("tnId")String tnId,
                                           @RequestParam("taskId")String taskId){
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("tnId",tnId);
        paramMap.put("taskId",taskId);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("mergeClassInfoList",iMergeClass.selectMergeInfo(paramMap));
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


}
