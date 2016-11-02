package cn.thinkjoy.saas.core;

/**
 * Created by yangguorong on 16/10/17.
 *
 * 常量类
 */
public class Constant {

    public static final String [] NoFilter_Pages = {"/account/login","/account/forgetPwd","/account/sendSmsCode","/account/loginOut"};

    public static final String ID = "id";

    public static final String DESC = "desc";

    public static final String ZGK = "zgk";

    public static final String USER_CAPTCHA_KEY = "gk_user_captcha_";

    public static final String CAPTCHA_AUTH_TIME_KEY = "gk_captcha_auth_time_";

    public static final String PROVINCE_KEY = "province_list_key";

    public static final String DATA_DICT_KEY = "data_dict_list_";

    public static final String CONDITION_KEY = "zgk_mpc_key_%s_%s";

    public static final int TOKEN_EXPIRE_TIME = 60*60;

    /**
     * 用户默认密码 111111
     */
    public static final String DEFULT_PWD = "96e79218965eb72c92a549dd5a330112";
}
