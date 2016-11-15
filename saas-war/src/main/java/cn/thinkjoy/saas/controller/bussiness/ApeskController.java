package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.gk.api.IApeskApi;
import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dto.UserInfoDto;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by yangguorong on 16/10/26.
 *
 * 数据查询模块
 */
@Controller
@RequestMapping("/apesk")
public class ApeskController {
    @Autowired
    IApeskApi apeskApi;

    @ResponseBody
    @RequestMapping("/queryReportUrl")
    public Map<String,Object> queryReportUrl(@RequestParam String liangbiao, @RequestParam Integer acId){
        Long userId = Long.valueOf(UserContext.getCurrentUser().getUserId());
        Map<String,Object> map = apeskApi.queryReportUrl(liangbiao,acId,userId);
        return map;
    }

    @ResponseBody
    @RequestMapping("/queryApeskUrl")
    public Map<String, Object> queryApeskUrl(Integer acId){
        UserInfoDto userInfoDto = UserContext.getCurrentUser();
        Long userId = Long.valueOf(userInfoDto.getUserId());
        String userName = userInfoDto.getUserName();
        Long areaId = Long.valueOf(userInfoDto.getCountyId()/10000+"0000");
        Map<String, Object> map = apeskApi.queryApeskUrl(userId,areaId,acId,userName);
        return map;
    }
    @ResponseBody
    @RequestMapping("/getApeskResult")
    Map<String,Object> getApeskResult(){
        UserInfoDto userInfoDto = UserContext.getCurrentUser();
        Long userId = Long.valueOf(userInfoDto.getUserId());
        Long areaId = Long.valueOf(userInfoDto.getCountyId()/10000+"0000");
        Map<String, Object> map =apeskApi.getApeskResult(userId,areaId);
        return map;
    }

}
