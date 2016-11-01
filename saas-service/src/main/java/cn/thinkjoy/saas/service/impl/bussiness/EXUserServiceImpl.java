package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IRelationUserRoleDAO;
import cn.thinkjoy.saas.dao.IResourcesDAO;
import cn.thinkjoy.saas.dao.IUserInstanceDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXUserDAO;
import cn.thinkjoy.saas.domain.RelationUserRole;
import cn.thinkjoy.saas.domain.Resources;
import cn.thinkjoy.saas.domain.UserInstance;
import cn.thinkjoy.saas.dto.UserBaseDto;
import cn.thinkjoy.saas.dto.UserInfoDto;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.bussiness.IEXUserService;
import cn.thinkjoy.saas.service.common.ConvertUtil;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/10/17.
 */
@Service("EXUserService")
public class EXUserServiceImpl implements IEXUserService {

    @Autowired
    private IEXUserDAO iexUserDAO;

    @Autowired
    private IResourcesDAO iResourcesDAO;

    @Autowired
    private IUserInstanceDAO iUserInstanceDAO;

    @Autowired
    private IRelationUserRoleDAO iRelationUserRoleDAO;

    @Autowired
    private RedisRepository<String, Object> redis;

    @Override
    public UserInfoDto login(String account, String pwd) {

        // 查找用户基本信息
        UserInfoDto userInfoDto = iexUserDAO.queryUserInfoByAccount(account,pwd);

        if(userInfoDto == null){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_OR_PWD_ERROR);
        }

        if(userInfoDto.getStatus() == 1){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_HAS_DISABLE);
        }

        // 根据用户ID查找用户所有菜单权限
        List<Resources> resources = Lists.newArrayList();

        if("1".equals(userInfoDto.getIsSuperManager())){

            resources = iResourcesDAO.findAll(
                    Constant.ID,
                    Constant.DESC
            );

        }else {

            resources = iexUserDAO.queryResourcesByUserId(
                    userInfoDto.getUserId()
            );

        }

        userInfoDto.setMeuns(ConvertUtil.convertMeuns(resources));

        // 根据用户ID查找用户角色
        List<String> roles = iexUserDAO.queryRolesByUserId(userInfoDto.getUserId());
        userInfoDto.setRoles(roles);

        return userInfoDto;
    }

    @Override
    public void forgetPwd(String account, String phone, String smsCode, String newPwd) {

        // 再次验证账号与手机号是否匹配
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("userAccount",account);
        paramMap.put("telephone",phone);
        UserInstance userInstance = iUserInstanceDAO.queryOne(
                paramMap,
                Constant.ID,
                Constant.DESC
        );

        if(userInstance == null){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_OR_PHONE_ERROR);
        }

        // 验证验证码
        checkSmsCode(phone,smsCode);

        userInstance.setUserPass(newPwd);
        iUserInstanceDAO.update(userInstance);
    }

    /**
     * 验证验证码
     *
     * @param phone
     * @param smsCode
     * @return
     */
    private void checkSmsCode(String phone, String smsCode) {

        // 验证码已失效
        String key = Constant.USER_CAPTCHA_KEY + phone;
        if (redis.get(key) == null) {
            ExceptionUtil.throwException(ErrorCode.SMS_CODE_INVALID);
        }

        // 验证码错误
        String code = redis.get(key).toString();
        boolean result = smsCode.equals(code);
        if (!result) {
            ExceptionUtil.throwException(ErrorCode.SMS_CODE_ERROR);
        }

        redis.del(key);
    }

    @Override
    public void updatePwd(int userId, String newPwd) {
        UserInstance userInstance = new UserInstance();
        userInstance.setId(userId);
        userInstance.setUserPass(newPwd);
        iUserInstanceDAO.update(userInstance);
    }

    @Override
    public void createUser(UserInstance instance, int roleId) {

        UserInstance tempInstance = iUserInstanceDAO.findOne(
                "user_account",
                instance.getUserAccount(),
                Constant.ID,
                Constant.DESC
        );

        if(tempInstance != null){
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_HAS_EXIST);
        }

        instance.setUserPass(Constant.DEFULT_PWD);
        instance.setCreateDate(new Date());
        instance.setIsSuperManager("0");
        instance.setStatus(0);
        iUserInstanceDAO.insert(instance);

        RelationUserRole userRole = new RelationUserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(Integer.valueOf(instance.getId().toString()));
        iRelationUserRoleDAO.insert(userRole);
    }

    @Override
    public void updateUser(UserInstance instance, int roleId) {
        iUserInstanceDAO.update(instance);
        if(roleId > 0){

            iRelationUserRoleDAO.deleteByProperty(
                    "user_id",
                    instance.getId()
            );

            RelationUserRole userRole = new RelationUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(Integer.valueOf(instance.getId().toString()));
            iRelationUserRoleDAO.insert(userRole);
        }
    }

    @Override
    public void disableUser(int userId, int state) {
        UserInstance instance = new UserInstance();
        instance.setStatus(state);
        instance.setId(userId);
        iUserInstanceDAO.update(instance);
    }

    @Override
    public void deleteUser(int userId) {

        // 删除用户和角色的关系
        iRelationUserRoleDAO.deleteByProperty(
                "user_id",
                userId
        );

        // 修改用户状态
        disableUser(userId,-1);
    }

    @Override
    public List<UserBaseDto> queryUserBaseInfoByKeyword(String keyword, int tnId) {

        List<UserBaseDto> dtos = iexUserDAO.queryUserBaseInfoByKeyword(
                keyword,
                tnId
        );

        return dtos;
    }
}
