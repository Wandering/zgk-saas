package cn.thinkjoy.saas.service.common;

/**
 * Created by douzy on 16/10/13.
 */
public class EnumUtil {

    //excel 下拉框锁定  班级类型
    public static final String[] CLASS_TYPE_ARR={"重点班","普通班"};
    //excel 班级类型字段
    public static final String CLASS_MAJOR_TYPE="班级类型";
    //excel 所属年级字段
    public static final String CLASS_GRADE="所属年级";
    //excel入学年份
    public static final String CLASS_ENROLL_YEAR="入学年份";

    //教师管理
    //excel入校年份
    public static final String TEACHER_SCHOOL_ENROLL_YEAR="入校年份";
    public static final String TEACHER_EDUCATION_GRADE="所教年级";
    public static final String TEACHER_EDUCATION_CLASS="所教班级";
    public static final String TEACHER_EDUCATION_MAJOYTYPE="所教科目";
    public static final String[] TEACHER_EDUCATION_MAJOYTYPE_ARR={"物理","化学","生物","政治","历史","地理","通用技术"};

    /**
     * 错误编码   规则: 【业务块描述-错误描述】
     */

    //全局通用
    public static final  Integer  GLOBAL_SYSTEMERROR=-1;


    /**
     * ====================配置导入 错误编码=================
     */
    //成功
    public static final  Integer  IMPORTCONFIG_SUCCESS=10000;
    //参数错误
    public static final  Integer  IMPORTCONFIG_PARAMSERROR=10001;
    //该租户未找到
    public static final  Integer  IMPORTCONFIG_TEANTNULL=10002;
    //选项集校验失败
    public static final  Integer  IMPORTCONFIG_CONFIGSVALIDERROR=10003;
    //清空租户表头历史数据出错
    public static final  Integer  IMPORTCONFIG_REMOVEHISTORYERROR=10004;
    //当前租户已上传模板,表头无法修改!
    public static final Integer   IMPORTCONFIG_TEANTCUSTOM_EXCEL=10005;
    /**
     * ====================配置导入 错误编码=================
     */

    /**
     * 编码描述
     */
    public  enum ErrorCode {

        SYSTEMERROR("系统错误", GLOBAL_SYSTEMERROR),
        SUCCESS("SUCCESS", IMPORTCONFIG_SUCCESS),
        PARAMSERROR("参数错误", IMPORTCONFIG_PARAMSERROR),
        TEANTNULL("该租户不存在", IMPORTCONFIG_TEANTNULL),
        CONFIGSVALIDERROR("选项集校验失败", IMPORTCONFIG_CONFIGSVALIDERROR),
        REMOVEHISTORYERROR("清空租户表头历史数据错误",IMPORTCONFIG_REMOVEHISTORYERROR),
        TEANTCUSTOM_EXCEL("当前租户已上传模板,表头无法修改!",IMPORTCONFIG_TEANTCUSTOM_EXCEL);


        private int value;
        private String desc;

        public int getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private ErrorCode(String desc, Integer value) {
            this.desc = desc;
            this.value = value;
        }

        public static String getDesc(Integer value) {
            for (ErrorCode c : ErrorCode.values()) {
                if (c.getValue() == value) {
                    return c.desc;
                }
            }
            return null;
        }

    }
}
