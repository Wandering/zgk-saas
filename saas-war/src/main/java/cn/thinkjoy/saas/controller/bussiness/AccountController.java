package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.UserInstance;
import cn.thinkjoy.saas.dto.UserBaseDto;
import cn.thinkjoy.saas.dto.UserInfoDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.IUserInstanceService;
import cn.thinkjoy.saas.service.bussiness.IEXUserService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import cn.thinkjoy.saas.service.common.RandomCodeUtil;
import cn.thinkjoy.sms.api.SMSService;
import cn.thinkjoy.sms.domain.SMSCheckCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangguorong on 16/10/14.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IEXUserService iexUserService;

    @Autowired
    private SMSService zgkSmsService;

    @Autowired
    private IUserInstanceService userInstanceService;

    @Autowired
    private RedisRepository<String, Object> redis;

    @ResponseBody
    @ApiDesc(value = "用户登陆",owner = "杨国荣")
    @RequestMapping(value = "/login/{account}/{pwd}",method = RequestMethod.GET)
    public UserInfoDto login(@PathVariable String account, @PathVariable String pwd,HttpServletRequest request){

        UserInfoDto userInfoDto = iexUserService.login(account,pwd);

        // 登陆成功,将用户ID存入session
        HttpSession session = request.getSession();
        session.setAttribute("userId",userInfoDto.getUserId());

        return userInfoDto;
    }

    @ResponseBody
    @ApiDesc(value = "忘记密码",owner = "杨国荣")
    @RequestMapping(value = "/forgetPwd/{account}/{phone}/{smsCode}/{newPwd}",method = RequestMethod.GET)
    public Map<String,Object> forgetPwd(@PathVariable String account,
                                 @PathVariable String phone,
                                 @PathVariable String smsCode,
                                 @PathVariable String newPwd){

        iexUserService.forgetPwd(account,phone,smsCode,newPwd);

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "发送验证码",owner = "杨国荣")
    @RequestMapping(value = "/sendSmsCode/{account}/{phone}",method = RequestMethod.GET)
    public Map<String,Object> sendSmsCode(@PathVariable String account,@PathVariable String phone){

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("userAccount",account);
        paramMap.put("telephone",phone);
        UserInstance userInstance = (UserInstance) userInstanceService.queryOne(paramMap);

        if(userInstance == null){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_OR_PHONE_ERROR);
        }

        String timeKey = Constant.CAPTCHA_AUTH_TIME_KEY + phone;
        String smsCodeKey = Constant.USER_CAPTCHA_KEY + phone;
        // 发送验证码间隔时间未到(获取验证码太频繁)
        if(redis.exists(timeKey)){
            ExceptionUtil.throwException(ErrorCode.SMS_CODE_FREQUENCY);
        }

        SMSCheckCode smsCode = new SMSCheckCode();
        smsCode.setPhone(phone);
        smsCode.setCheckCode(RandomCodeUtil.generateNumCode(6));
        smsCode.setBizTarget(Constant.ZGK);

        boolean smsResult = zgkSmsService.sendSMS(smsCode,false);

        if(!smsResult) {
            // 发送失败切换短信通道
            smsResult = zgkSmsService.sendSMS(smsCode,true);
        }

        if(smsResult){
            // 验证码有效时间10分钟
            redis.set(
                    smsCodeKey,
                    smsCode.getCheckCode(),
                    600,
                    TimeUnit.SECONDS
            );
            // 发送验证码间隔时间1分钟
            redis.set(
                    timeKey,
                    System.currentTimeMillis(),
                    60,
                    TimeUnit.SECONDS
            );
        }else {
            // 再次发送失败,抛出异常
            ExceptionUtil.throwException(ErrorCode.SMS_CODE_FAIL);
        }

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "重设密码",owner = "杨国荣")
    @RequestMapping(value = "/updatePwd/{userId}/{newPwd}",method = RequestMethod.GET)
    public Map<String,Object> updatePwd(@PathVariable int userId,
                          @PathVariable String newPwd){

        iexUserService.updatePwd(userId,newPwd);

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "创建用户",owner = "杨国荣")
    @RequestMapping(value = "/createUser",method = RequestMethod.POST)
    public Map<String,Object> createUser(@RequestBody Request request){

        int roleId = request.getDataInteger("roleId");

        UserInstance instance = JSON.parseObject(
                JSON.toJSON(request.getData()).toString(),
                UserInstance.class
        );

        iexUserService.createUser(
                instance,
                roleId
        );

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "修改用户信息",owner = "杨国荣")
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public Map<String,Object> updateUser(@RequestBody Request request){

        int roleId = request.getDataInteger("roleId");
        int userId = request.getDataInteger("userId");

        UserInstance instance = JSON.parseObject(
                JSON.toJSON(request.getData()).toString(),
                UserInstance.class
        );
        instance.setId(userId);

        iexUserService.updateUser(
                instance,
                roleId
        );

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "禁用用户账号",owner = "杨国荣")
    @RequestMapping(value = "/disableUser/{targetUserId}/{currentUserId}/{state}",method = RequestMethod.GET)
    public Map<String,Object> disableUser(@PathVariable int targetUserId,@PathVariable int currentUserId,@PathVariable int state){

        // TODO 鉴权

        iexUserService.disableUser(
                targetUserId,
                state
        );

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "删除用户",owner = "杨国荣")
    @RequestMapping(value = "/deleteUser/{targetUserId}/{currentUserId}",method = RequestMethod.GET)
    public Map<String,Object> deleteUser(@PathVariable int targetUserId,@PathVariable int currentUserId){

        // TODO 鉴权

        iexUserService.deleteUser(
                targetUserId
        );

        return Maps.newHashMap();
    }

    @ResponseBody
    @ApiDesc(value = "查询用户",owner = "杨国荣")
    @RequestMapping(value = "/queryUserBaseInfo/{keyword}/{tnId}",method = RequestMethod.GET)
    public List<UserBaseDto> queryUserBaseInfo(@PathVariable String keyword, @PathVariable int tnId){

        List<UserBaseDto> dtos = iexUserService.queryUserBaseInfoByKeyword(
                keyword,
                tnId
        );

        return dtos;
    }
}
