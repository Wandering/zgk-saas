package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.Resources;
import cn.thinkjoy.saas.dto.UserBaseDto;
import cn.thinkjoy.saas.dto.UserInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangguorong on 16/10/17.
 */
public interface IEXUserDAO {

    /**
     * 根据用户账号,密码查询用户信息
     *
     * @param account
     * @param pwd
     * @return
     */
    UserInfoDto queryUserInfoByAccount(
            @Param("account") String account,
            @Param("pwd") String pwd
    );

    /**
     * 根据用户ID查找用户角色集合
     *
     * @param userId
     * @return
     */
    List<String> queryRolesByUserId(
            @Param("userId") int userId
    );

    /**
     * 根据用户ID查询用户所对应的菜单集合
     *
     * @param userId
     * @return
     */
    List<Resources> queryResourcesByUserId(
            @Param("userId") int userId
    );

    /**
     * 根据关键词查询用户集合
     *
     * @param keyword
     * @param tnId
     * @return
     */
    List<UserBaseDto> queryUserBaseInfoByKeyword(
            @Param("keyword") String keyword,
            @Param("tnId") int tnId
    );
}
