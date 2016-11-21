package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/11/16.
 */
public class TrineDto implements Serializable {
    private String batchName;
    private int majorNumber;
    private int planEnrollingNumber;
    private String percent;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getMajorNumber() {
        return majorNumber;
    }

    public void setMajorNumber(int majorNumber) {
        this.majorNumber = majorNumber;
    }

    public int getPlanEnrollingNumber() {
        return planEnrollingNumber;
    }

    public void setPlanEnrollingNumber(int planEnrollingNumber) {
        this.planEnrollingNumber = planEnrollingNumber;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
