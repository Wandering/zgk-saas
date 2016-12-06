package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.domain.JwBaseRule;
import cn.thinkjoy.saas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by liusven on 2016/12/5.
 */
@RestController
@RequestMapping("/disSelectRule")
public class CourseDisSelectController
{
    @Autowired
    private IJwClassRuleService jwClassRuleService;

    @Autowired
    private IJwCourseRuleService jwCourseRuleService;

    @Autowired
    private IJwTeacherRuleService jwTeacherRuleService;

    @RequestMapping(value = "/getRule/{taskId}/{type}/{id}", method = RequestMethod.GET)
    public List<Map<String, String>> getRule(@PathVariable String taskId,
        @PathVariable String type, @PathVariable String id)
    {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put(type + "Id", id);
        return getServiceByType(type).queryList(params, "id", "asc");
    }

    @RequestMapping(value = "/addOrUpdateRule/{taskId}/{type}", method = RequestMethod.POST)
    public int addOrUpdateRule(@PathVariable String taskId, @PathVariable String type,
        @RequestParam(value = "ids", required = true) String ids, JwBaseRule jwBaseRule)
    {
        int result = 0;
        try
        {
            List<Map<String, String>> ruleList = getRuleList(taskId, type, ids, jwBaseRule);
            if (ruleList.size() > 0)
            {
                for (Map<String, String> map : ruleList)
                {
                    try
                    {
                        result += getServiceByType(type).insertMap(map);
                    }
                    catch (DuplicateKeyException e)
                    {
                        Map<String, String> condition = new HashMap<>();
                        condition.put("taskId", map.get("taskId"));
                        condition.put(type +"Id", map.get(type +"Id"));
                        result += getServiceByType(type).updateByCondition(map, condition);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new BizException("1100223", "参数错误！");
        }
        return result;
    }

    private List<Map<String, String>> getRuleList(String taskId, String type, String ids, JwBaseRule jwBaseRule)
    {
        String[] idArray = ids.split(",");
        List<Map<String, String>> ruleList = new ArrayList<>();
        for (String id : idArray)
        {
            Map<String, String> rule = getDomainByType(taskId, type, id, jwBaseRule);
            ruleList.add(rule);
        }
        return ruleList;
    }

    private IDisSelectRoleService getServiceByType(String type)
    {
        IDisSelectRoleService service;
        if ("class".equals(type))
        {
            service = jwClassRuleService;
        }
        else if ("course".equals(type))
        {
            service = jwCourseRuleService;
        }
        else if ("teacher".equals(type))
        {
            service = jwTeacherRuleService;
        }
        else
        {
            throw new BizException("1100222", "type参数有误!");
        }
        return service;
    }

    private Map<String, String> getDomainByType(String taskId, String type, String id, JwBaseRule jwBaseRule)
    {
        Map<String, String> rule = new LinkedHashMap<>();
        if ("class".equals(type))
        {
            rule.put("classId", id);

        }
        else if ("course".equals(type))
        {
            rule.put("courseId", id);
        }
        else if ("teacher".equals(type))
        {
            rule.put("teacherId", id);
        }
        else
        {
            throw new BizException("1100222", "type参数有误!");
        }
        rule.put("mon", jwBaseRule.getMon());
        rule.put("tues", jwBaseRule.getTues());
        rule.put("wed", jwBaseRule.getWed());
        rule.put("thur", jwBaseRule.getThur());
        rule.put("fri", jwBaseRule.getFri());
        rule.put("sut", jwBaseRule.getSut());
        rule.put("sun", jwBaseRule.getSun());
        rule.put("taskId", taskId);
        rule.put("createDate", System.currentTimeMillis()+ "");
        return rule;
    }
}
