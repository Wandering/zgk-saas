package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.domain.JwBaseRule;
import cn.thinkjoy.saas.domain.JwCourseGapRule;
import cn.thinkjoy.saas.domain.JwTeachDate;
import cn.thinkjoy.saas.dto.CourseBaseDto;
import cn.thinkjoy.saas.dto.TeacherBaseDto;
import cn.thinkjoy.saas.service.*;
import cn.thinkjoy.saas.service.bussiness.IEXScheduleBaseInfoService;
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

    @Autowired
    private IJwTeachDateService jwTeachDateService;

    @Autowired
    private IEXScheduleBaseInfoService iexScheduleBaseInfoService;

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
                        condition.put(type + "Id", map.get(type + "Id"));
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
        rule.put("createDate", System.currentTimeMillis() + "");
        return rule;
    }

    @RequestMapping(value = "/getGapRule/{taskId}", method = RequestMethod.GET)
    public Map<String, String> getGapRule(@PathVariable String taskId)
    {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        JwCourseGapRule rule = (JwCourseGapRule)jwCourseGapRuleService.queryOne(params);
        Map<String, String> resultMap = new HashMap<>();
        if (null == rule || StringUtils.isEmpty(rule.getRule()))
        {
            List<JwTeachDate> list = jwTeachDateService.queryList(params, "tid", "asc");
            if(list.size()>0)
            {
                JwTeachDate d = list.get(0);
                String detail =d.getTeachDetail();
                if(detail.contains("1"))
                {
                    int c = detail.length() - detail.replaceAll("1", "").length();
                    for (int i = 1; i < c ; i++)
                    {
                        resultMap.put(i + "-" + (i + 1) + "节", "0");
                    }
                }
            }
        }
        else
        {
            String[] rules = rule.getRule().split("@@");
            int len = rules.length;
            if (len > 0)
            {
                for (int i = 0; i < len; i++)
                {
                    String[] detail = rules[i].split(":");
                    if (detail.length < 2)
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
        int unCheckNum = 0;
        while (paramNames.hasMoreElements())
        {
            String name = paramNames.nextElement();
            if (name.contains("节"))
            {
                String v1 = req.getParameter(name);
                if ("0".equals(v1))
                {
                    unCheckNum++;
                }
                sb.append("@@").append(name).append(":").append(req.getParameter(name));
            }
        }
        List<JwTeachDate> list = jwTeachDateService.queryList(params, "tid", "asc");
        List<Map<String, String>> linkList = getLinkList(taskId);
        if (list != null && list.size() > 0 && linkList.size() > 0)
        {
            int maxLink = 0;
            for (Map<String, String> m : linkList)
            {
                try
                {
                    int link = Integer.parseInt(m.get("linkCount"));
                    if (link > maxLink)
                    {
                        maxLink = link;
                    }
                }
                catch (Exception e)
                {
                }
            }
            int maxLinkPerDay = getCeilValue(maxLink, list.size());
            if(unCheckNum < maxLinkPerDay)
            {
                throw new BizException("890929", "冲突提示：您设置的不连堂节次与已设连堂科目课时冲突！");
            }
        }
        String rules = "";
        if (sb.toString().length() >= 2)
        {
            rules = sb.substring(2);
        }
        if (null == rule)
        {
            rule = new JwCourseGapRule();
            rule.setRule(rules);
            rule.setTaskId(taskIdValue);
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

    @RequestMapping(value = "/getLinkList/{taskId}", method = RequestMethod.GET)
    public List<Map<String, String>> getLinkList(@PathVariable String taskId)
    {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        List<Map<String, Object>> l = jwCourseGapRuleService.queryLinkList(params);
        for (Map<String, Object> m : l)
        {
            String c = (String)m.get("course_hour");
            if (StringUtils.isNotEmpty(c) && c.contains("+"))
            {
                String[] cs = c.split("\\+");
                if (cs.length < 2)
                {
                    continue;
                }
                String singleCount = cs[0];
                String linkCount = cs[1];
                Map<String, String> map = new HashMap<>();
                map.put("courseName", m.get("course_name") + "");
                map.put("singleCount", singleCount);
                map.put("linkCount", linkCount);
                map.put("hour", m.get("chour") + "");
                list.add(map);
            }
        }
        return list;
    }

    @RequestMapping(value = "/list/{type}/{taskId}", method = RequestMethod.GET)
    public List<Map<String, String>> getList(@PathVariable Integer taskId, @PathVariable String type
        , @RequestParam(value = "teacherCourse", required = false) String teacherCourse)
    {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId + "");
        if ("class".equals(type))
        {
            list = jwCourseGapRuleService.queryClassList(params);
        }
        else if ("course".equals(type))
        {
            list = jwCourseGapRuleService.queryCourseList(params);
        }
        else if ("teacher".equals(type))
        {
            params.put("teacherCourse", teacherCourse);
            List<TeacherBaseDto> dtos = iexScheduleBaseInfoService.queryTeacherByTaskId(taskId);
            if(null != dtos && dtos.size() >0)
            {
                for (TeacherBaseDto dto: dtos)
                {
                    if(teacherCourse.equals(dto.getCourseName()))
                    {
                        Map<String, String> map = new HashMap<>();
                        map.put("name", dto.getTeacherName());
                        map.put("teacherCourse", dto.getCourseName());
                        map.put("id", dto.getTeacherId() + "");
                        list.add(map);
                    }
                }
            }
        }
        else
        {
            throw new BizException("1100222", "type参数有误!");
        }
        return list;
    }

    @RequestMapping(value = "/teacherCourseList/{taskId}", method = RequestMethod.GET)
    public List<String> getList(@PathVariable Integer taskId)
    {
        List<CourseBaseDto> dtos = iexScheduleBaseInfoService.queryCourseInfoByTaskId(taskId);
        List<String> list = new ArrayList<>();
        for (CourseBaseDto dto: dtos)
        {
            list.add(dto.getCourseName());
        }
        return list;
    }

    private int getCeilValue(int a, int b)
    {
        return a % b == 0 ? a / b : a / b + 1;
    }
}
