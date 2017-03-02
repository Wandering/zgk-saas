package cn.thinkjoy.saas.enums;

/**
 * Created by yangguorong on 2017/03/01.
 */
public enum CourseStateEnum {
    WSZ(0,"选课未设置"),
    WKS(1,"选课未开始"),
    XKZ(2,"学生选课中"),
    DSY(3,"选课结果待使用"),
    YSY(4,"选课结果已使用");

    private final Integer code;
    private final String name;


    CourseStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
