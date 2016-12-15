package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.JwTeachDate;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.*;
import cn.thinkjoy.saas.service.bussiness.IExTeachTimeService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/12/7.
 */
@Controller
@RequestMapping("/teachTime")
public class TeachTimeController {
    @Autowired
    IExTeachTimeService teachTimeService;

    @ResponseBody
    @RequestMapping(value = "/saveTeachTime",method = RequestMethod.POST)
    public boolean saveTeachTime(@RequestParam Integer taskId,
                                 @RequestParam String teachDate,
                                 @RequestParam String teachTime){
        if (StringUtils.isEmpty(teachDate)||StringUtils.isEmpty(teachTime)){
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        return teachTimeService.saveTeachTime(taskId,teachDate,teachTime,tnId);
    }


    @ResponseBody
    @RequestMapping(value = "/queryTeachTime",method = RequestMethod.GET)
    public Map<String, Object> queryTeachTime(@RequestParam Integer taskId){
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        return teachTimeService.queryTeachTime(taskId,tnId);
    }

    /**
     * 判断部分(涉及到学时信息)排课规则信息是否设置过
     * @param taskId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTeachTimeStatus",method = RequestMethod.GET)
    public boolean queryTeachTimeStatus(@RequestParam Integer taskId){
        return teachTimeService.queryTeachTimeStatus(taskId);
    }

}
