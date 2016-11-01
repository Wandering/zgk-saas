package cn.thinkjoy.saas.enums;

/**
 * Created by yangguorong on 16/10/17.
 *
 * 异常返回类
 */
public enum ErrorCode {

    SUCCESS("0000000", "success"),

    NOT_LOGIN("0000001","请先登录后在进行操作"),

    ACCOUNT_OR_PWD_ERROR("0000002","账号或密码错误"),

    SMS_CODE_FREQUENCY("0000003", "验证码获取过于频繁"),

    SMS_CODE_ERROR("0000004", "验证码不正确，请重新输入"),

    SMS_CODE_FAIL("0100005", "验证码发送失败,请重试"),

    SMS_CODE_INVALID("0100006", "验证码已失效，请重新获取"),

    ACCOUNT_OR_PHONE_ERROR("0000007","账号和手机号不匹配"),

    ACCOUNT_HAS_EXIST("0000008","账号已存在"),

    ACCOUNT_HAS_DISABLE("0000009","账号已被禁用");

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
