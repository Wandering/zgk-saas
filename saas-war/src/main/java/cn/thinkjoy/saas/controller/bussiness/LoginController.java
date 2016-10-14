package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.saas.dto.UserInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yangguorong on 16/10/13.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @ResponseBody
    @ApiDesc(value = "用户登陆",owner = "杨国荣")
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public UserInfoDto login(@RequestBody Request request){
        return null;
    }
}
