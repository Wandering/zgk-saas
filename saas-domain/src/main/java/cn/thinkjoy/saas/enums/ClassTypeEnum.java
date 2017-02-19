package cn.thinkjoy.saas.enums;

/**
 * chao.yu@dianping.com
 * Created by pdeng on 2017/02/19 10:21.
 */
public enum ClassTypeEnum {

    jx_class(0,"教学班"),
    xz_class(1,"行政班"),
    wk_class(2,"文科班"),
    lk_class(3,"理科班");
    private final Integer code;
    private final String typeName;

    ClassTypeEnum(Integer code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    // 普通方法
    public static int getCode(String typeName) {
        for (ClassTypeEnum c : ClassTypeEnum.values()) {
            if (c.typeName == typeName) {
                return c.code;
            }
        }
       return 1;
    }

    public Integer getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }
}
