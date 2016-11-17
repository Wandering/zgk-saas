package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/11/16.
 */
public class EnrollingInfoDto implements Serializable {
    private String universityName;
    private String rank;
    private String property;
    private Object propertys;
    private String universityId;
    private String planEnrollingNumber;

    public Object getPropertys() {
        return propertys;
    }

    public void setPropertys(Object propertys) {
        this.propertys = propertys;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getPlanEnrollingNumber() {
        return planEnrollingNumber;
    }

    public void setPlanEnrollingNumber(String planEnrollingNumber) {
        this.planEnrollingNumber = planEnrollingNumber;
    }
}
