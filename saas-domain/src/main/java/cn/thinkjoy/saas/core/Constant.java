package cn.thinkjoy.saas.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangguorong on 16/10/17.
 *
 * 常量类
 */
public class Constant {

    public static final String [] NoFilter_Pages = {"/account/login","/account/forgetPwd","/account/sendSmsCode","/account/loginOut","/scheduleTask/trigger","/scheduleTask/state"};

    public static final String [] GRADES = {"高一","高二","高三"};

    public static final List<String> COURSEES = new ArrayList<String>(){{add("物理"); add("化学");add("生物");add("政治");add("地理");add("历史");add("通用技术");}};

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

    public static final String ClASS_INTERVAL = "、";

    public static final String STUDENT = "student";

    public static final String TABLE_TYPE_TEACHER = "teacher";


    public static final String TABLE_TYPE_CLASS = "class";

    public static final String TABLE_TYPE_ROOM = "room";

    public static final String TABLE_TYPE_ALL = "all";

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
    public static final String ZDBJ_COLUMNS_KEY = "student_check_major1,student_check_major_class1,student_check_major2,student_check_major_class2,student_check_major3,student_check_major_class3";
    public static final String ZDBJ_COLUMNS_VALUE = "选考科目一,科目一所在班级,选考科目二,科目二所在班级,选考科目三,科目三所在班级";

    public static final String DEFULT_TEACH_DATE= "星期一|星期二|星期三|星期四|星期五";
    public static final String DEFULT_TEACH_TIME= "430";
    public static final Integer DEFULT_CLASS_TYPE= 1;
    public static final Integer SUBJECT_CLASS_TYPE= 2;
    public static final Integer DEFULT_CLASS_NUM= 2;
    public static final Integer TASK_SUCCESS = 3;
    public static final String CLASS_ADM = "class_adm";
    public static final int CLASS_ADM_CODE = 0;
    public static final String CLASS_EDU = "class_edu";
    public static final int CLASS_EDU_CODE = 1;
    public static final String CREATE_TABLE_TYPE_TEACHER_IDS = "9-26-28-42-59";
    /**课程表redis相关start**/
    public static final String COURSE_TABLE_REDIS_KEY = "course_key_";
    public static final String COURSE_TABLE_REDIS_SPLIT = "_";
    /**课程表redis相关end**/
    /**课程解析相关**/
    public static final java.lang.String COURSE_LINE_TABLE_SPLIT_T = "\t";
    public static final java.lang.String COURSE_TABLE_LINE_SPLIT_CHAR = "  ";
    public static final java.lang.String COURSE_TABLE_LINE_SPLIT_CLASS = "     ";
    public static final java.lang.String PATH_SCHEDULE_ADM = "admin_course_0.txt";
    public static final java.lang.String PATH_SCHEDULE_EDU = "edu_course_0.txt";
    /**课程解析相关**/
    /**课程表生成相关**/
    public static final String GEN_COURSE_TABLE_BASE_SPLIT = " ";
    public static final String GEN_COURSE_TABLE_TEACHER_AROUND_S = "(";
    public static final String GEN_COURSE_TABLE_TEACHER_AROUND_E = ")";
    public static final String GEN_COURSE_TABLE_CLASS_SPLIT = "-";
    public static final String GEN_COURSE_TABLE_WRAP_SPLIT = "<br>";
    public static final String COURSE_TABLE_ALL = "all";
    public static final String COURSE_TABLE_DEFAULT_VALUE = "";
    /**课程表生成相关**/

    //redis缓存时间30天
    public static final long SCHEDULE_REDIS_TIME = 1 * 30;
    public static final Integer COURSE_STATUS_Y = 1;
}
