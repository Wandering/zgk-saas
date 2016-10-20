package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.UserInstance;
import cn.thinkjoy.saas.dto.UserBaseDto;
import cn.thinkjoy.saas.dto.UserInfoDto;

import java.util.List;

/**
 * Created by yangguorong on 16/10/17.
 */
public interface IEXUserService {

    /**
     * 登陆
     *
     * @param account
     * @param pwd
     * @return
     */
    UserInfoDto login(
            String account,
            String pwd
    );

    /**
     * 忘记密码
     *
     * @param account
     * @param phone
     * @param smsCode
     * @param newPwd
     */
    void forgetPwd(String account,
                   String phone,
                   String smsCode,
                   String newPwd
    );

    /**
     * 修改用户密码
     *
     * @param userId
     * @param newPwd
     */
    void updatePwd(
            int userId,
            String newPwd
    );

    /**
     * 创建用户
     *
     * @param instance
     * @param roleId
     */
    void createUser(
            UserInstance instance,
            int roleId
    );

    /**
     * 修改用户信息
     *
     * @param instance
     * @param roleId
     */
    void updateUser(
            UserInstance instance,
            int roleId
    );

    /**
     * 禁用,启用用户账号
     *
     * @param userId
     * @param state
     */
    void disableUser(
            int userId,
            int state
    );

    /**
     * 删除用户
     *
     * @param userId
     */
    void deleteUser(
            int userId
    );

    /**
     * 根据关键词查询查询
     *
     * @param keyword
     * @param tnId
     * @return
     */
    List<UserBaseDto> queryUserBaseInfoByKeyword(
            String keyword,
            int tnId
    );
}
