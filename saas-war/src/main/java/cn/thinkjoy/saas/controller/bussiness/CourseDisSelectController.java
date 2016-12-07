package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.domain.JwBaseRule;
import cn.thinkjoy.saas.domain.JwCourseGapRule;
import cn.thinkjoy.saas.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private IJwCourseGapRuleService jwCourseGapRuleService;

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

    @RequestMapping(value = "/getGapRule/{taskId}", method = RequestMethod.GET)
    public Map<String, String> getGapRule(@PathVariable String taskId)
    {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        JwCourseGapRule rule = (JwCourseGapRule)jwCourseGapRuleService.queryOne(params);
        Map<String, String> resultMap = new HashMap<>();
        if(null == rule || StringUtils.isEmpty(rule.getRule()))
        {
            resultMap.put("1-2节", "0");
            resultMap.put("2-3节", "0");
            resultMap.put("3-4节", "0");
            resultMap.put("4-5节", "0");
            resultMap.put("5-6节", "0");
            resultMap.put("6-7节", "0");
            resultMap.put("7-8节", "0");
        }
        else
        {
            String[] rules = rule.getRule().split("@@");
            int len = rules.length;
            if(len > 0)
            {
                for (int i = 0; i < len ; i++)
                {
                    String[] detail = rules[i].split(":");
                    if(detail.length<2)
                        continue;
                    resultMap.put(detail[0], detail[1]);
                }
            }
        }
        return resultMap;
    }

    @RequestMapping(value = "/addOrUpdateGapRule/{taskId}", method = RequestMethod.POST)
    public int addOrUpdateGapRule(@PathVariable String taskId, HttpServletRequest req)
    {
        int result = 0;
        Long taskIdValue;
        try
        {
            taskIdValue = Long.parseLong(taskId);
        }
        catch (NumberFormatException e)
        {
            throw new BizException("1122111", "taskId 参数错误！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        JwCourseGapRule rule = (JwCourseGapRule)jwCourseGapRuleService.queryOne(params);
        Enumeration<String> paramNames = req.getParameterNames();
        StringBuffer sb = new StringBuffer();
        while (paramNames.hasMoreElements())
        {
            String name = paramNames.nextElement();
            if(name.contains("节"))
            {
                sb.append("@@").append(name).append(":").append(req.getParameter(name));
            }
        }
        String rules = "";
        if(sb.toString().length()>=2)
        {
            rules = sb.substring(2);
        }
        if(null == rule)
        {
            rule = new JwCourseGapRule();
            rule.setTaskId(taskIdValue);
            rule.setRule(rules);
            rule.setCreateDate(System.currentTimeMillis());
            result = jwCourseGapRuleService.insert(rule);
        }
        else
        {
            rule.setRule(rules);
            result = jwCourseGapRuleService.update(rule);
        }
        return result;
    }

}
