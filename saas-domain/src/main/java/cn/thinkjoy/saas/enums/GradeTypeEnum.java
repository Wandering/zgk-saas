package cn.thinkjoy.saas.enums;

/**
 * Created by yangyongping on 2016/12/6.
 */
public enum GradeTypeEnum {
    Executive(1,"行政班"),
    Teaching(2,"教学班+行政班"),
    Science(3,"文科班+理科班");
    private final Integer code;
    private final String typeName;


    GradeTypeEnum(Integer code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    // 普通方法
    public static String getName(int index) {
        for (GradeTypeEnum c : GradeTypeEnum.values()) {
            if (c.getCode() == index) {
                return c.typeName;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }
}
