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

    ACCOUNT_HAS_DISABLE("0000009","账号已被禁用"),

    ROWS_TOO_LONG("0000010","查询条数过多"),

    TABLE_NOT_EXIST("0000011","数据异常,表不存在或者字段不存在"),

    PARAN_NULL("0500002","参数不完整,请检查参数完整性"),

    TASK_ERROR("0500001","参数不完整,请检查参数完整性"),

    ROLE_HAS_EXIST("0000012","同名的角色已经存在"),

    TASK_NOT_EXIST("0000013","任务不存在"),

    TEACH_DATE_ERROR("0000014","教学时间信息未填写"),

    COURSE_INFO_ERROR("0000015","课程信息未填写"),

    TEACHER_INFO_ERROR("0000016","教师信息未填写"),

    TEACHER_INFO_NOT_PERFECT("0000017","教师信息未填写完善"),

    COURSE_INFO_NOT_PERFECT("0000018","课程课时信息未填写完善"),

    DATA_FORMAT_ERROR("0000019","数据格式不正确"),

    SUBJECT_NULL("2000001","科目数据未设置"),

    GRADE_FORMAT_ERROR("2000002","年级数据不完整");

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
