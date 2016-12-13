package cn.thinkjoy.saas.enums;

/**
 * Created by yangyongping on 2016/12/7.
 */
public enum StatusEnum {
    Y(0,"正常"),
    D(-1,"删除")
    ;
    private final Integer code;
    private final String status;

    StatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
