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

    public static final String USER_SESSION_KEY = "user_info_dto";

    public static final int TOKEN_EXPIRE_TIME = 60*60;

    /**
     * 用户默认密码 111111
     */
    public static final String DEFULT_PWD = "96e79218965eb72c92a549dd5a330112";


    public static final Integer SCHEDULE_TASK_INIT_STATUS = 1;

    public static final String TIME_INTERVAL = "_";

    public static final String STUDENT = "student";

    public static final String TABLE_TYPE_TEACHER = "teacher";

    public static final String STUDENT_GRADE = "student_grade";

    public static final String[] CHECK_TABLE_STUDENT_COLUMNS = {
            "student_name",
            "student_no",
            "student_check_major1",
            "student_check_major_class1",
            "student_check_major2",
            "student_check_major_class2",
            "student_check_major3",
            "student_check_major_class3"
            };

    // 走读班级学生特有属性
    public static final String ZDBJ_COLUMNS = "student_check_major1,student_check_major_class1,student_check_major2,student_check_major_class2,student_check_major3,student_check_major_class3";
    public static final String DEFULT_TEACH_DATE= "星期一|星期二|星期三|星期四|星期五";
    public static final String DEFULT_TEACH_TIME= "430";
    public static final Integer DEFULT_CLASS_TYPE= 1;
    public static final Integer SUBJECT_CLASS_TYPE= 2;
    public static final Integer DEFULT_CLASS_NUM= 2;
    public static final Integer TASK_SUCCESS = 3;
    public static final String CLASS_ADM = "class_adm";
    public static final String CLASS_EDU = "class_edu";
}
