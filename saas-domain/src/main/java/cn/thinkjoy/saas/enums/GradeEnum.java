package cn.thinkjoy.saas.enums;

/**
 * Created by yangyongping on 2016/12/6.
 */
public enum GradeEnum {
    GAOYI(1,"高一"),
    GAOER(2,"高二"),
    GAOSAN(3,"高三"),
    ;
    private final Integer code;
    private final String gradeName;


    GradeEnum(Integer code, String gradeName) {
        this.code = code;
        this.gradeName = gradeName;
    }

    // 普通方法
    public static String getName(int index) {
        for (GradeEnum c : GradeEnum .values()) {
            if (c.getCode() == index) {
                return c.gradeName;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getGradeName() {
        return gradeName;
    }
}
