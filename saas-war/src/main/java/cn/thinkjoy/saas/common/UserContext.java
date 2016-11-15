package cn.thinkjoy.saas.common;

import cn.thinkjoy.saas.dto.UserInfoDto;

/**
 * 用户上下文
 * <p/>
 * 创建时间: 14-9-1 下午11:21<br/>
 *
 * @author qyang
 * @since v0.0.1
 * @author Michael
 * @since v0.0.10
 */
public class UserContext {
    private static ThreadLocal<UserInfoDto> context = new ThreadLocal<UserInfoDto>();

    public static UserInfoDto getCurrentUser(){
        return context.get();
    }

    public static void setCurrentUser(UserInfoDto user){
        context.set(user);
    }

    public static void removeCurrentUser()
    {
        context.remove();
    }
}
