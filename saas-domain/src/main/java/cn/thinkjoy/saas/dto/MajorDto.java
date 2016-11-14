package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/11/1.
 */
public class MajorDto implements Serializable {

    private String majorCode;
    private String majorName;
    private String universityId;
    private String universityName;
    private String rank;
    private String batchName;
    private String planNumber;
    private String property;
    private Object propertys;
    private String selSubject;

    public Object getPropertys() {
        return propertys;
    }

    public void setPropertys(Object propertys) {
        this.propertys = propertys;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getSelSubject() {
        return selSubject;
    }

    public void setSelSubject(String selSubject) {
        this.selSubject = selSubject;
    }
}
