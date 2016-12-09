package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/12/7.
 */
public class ClassBaseDto implements Serializable {

    private int classId; // 所带班级ID
    private String className; // 所带班级名称
    private String isMerge;

    public String getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(String isMerge) {
        this.isMerge = isMerge;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ClassBaseDto{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                '}';
    }
}
