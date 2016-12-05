package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.domain.JwBaseRule;
import cn.thinkjoy.saas.domain.JwClassRule;
import cn.thinkjoy.saas.domain.JwCourseRule;
import cn.thinkjoy.saas.domain.JwTeacherRule;
import cn.thinkjoy.saas.service.IDisSelectRoleService;
import cn.thinkjoy.saas.service.IJwClassRuleService;
import cn.thinkjoy.saas.service.IJwCourseRuleService;
import cn.thinkjoy.saas.service.IJwTeacherRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/listClassRule/{taskId}/{type}/{value}")
    public List<Map<String, String>> listClassRule(@PathVariable String taskId,
        @PathVariable String type, @PathVariable String value)
    {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put(type + "Id", value);
        return getServiceByType(type).queryList(params, "id", "asc");
    }

    @RequestMapping("/listClassRule/{taskId}/{type}")
    public int addRule(@PathVariable String taskId, @PathVariable String type,
        @RequestParam(value = "ids", required = true)String ids, JwBaseRule jwBaseRule)
    {
        String[] idArray =ids.split(",");
        for (int i = 0; i < idArray.length ; i++)
        {
            getDomainByType(type, idArray[i]);
        }
        return 1;
    }

    public int modifyClassRule()
    {
        return 1;
    }

    private IDisSelectRoleService getServiceByType(String type)
    {
        IDisSelectRoleService service;
        if("class".equals(type))
        {
            service = jwClassRuleService;
        }else if("course".equals(type))
        {
            service = jwCourseRuleService;
        }else if("teacher".equals(type))
        {
            service = jwTeacherRuleService;
        }
        else
        {
            throw new BizException("1100222", "type参数有误!");
        }
        return service;
    }

    private JwBaseRule getDomainByType(String type, String id)
    {
        JwBaseRule rule;
        Integer typeId = 0;
        try
        {
            typeId = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            throw new BizException("3311231", id + "不是合法类型!");
        }
        if("class".equals(type))
        {
            rule = new JwClassRule();

        }else if("course".equals(type))
        {
            rule = new JwCourseRule();
        }else if("teacher".equals(type))
        {
            rule = new JwTeacherRule();
        }
        else
        {
            throw new BizException("1100222", "type参数有误!");
        }
        rule.setTypeId(typeId);
        return rule;
    }
}
