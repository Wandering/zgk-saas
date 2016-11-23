package cn.thinkjoy.saas.domain.bussiness;

import java.io.Serializable;

/**
 * Created by douzy on 16/11/22.
 */
public class ClassView implements Serializable {
    private String classType;
    private String className;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
