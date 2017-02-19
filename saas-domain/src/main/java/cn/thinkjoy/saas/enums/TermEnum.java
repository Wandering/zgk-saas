package cn.thinkjoy.saas.enums;

/**
 * Created by yangyongping on 2016/12/6.
 */
public enum  TermEnum {
    one(1,"第一学期"),
    two(2,"第二学期");
    private final Integer code;
    private final String name;


    TermEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(int index) {
        for (TermEnum c : TermEnum .values()) {
            if (c.getCode() == index) {
                return c.name;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

}
