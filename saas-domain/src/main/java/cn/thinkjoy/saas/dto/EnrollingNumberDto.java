package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/11/16.
 */
public class EnrollingNumberDto implements Serializable {
    private String batchName;
    private int universityNumber;
    private int planEnrollingNumber;
    private String year;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getUniversityNumber() {
        return universityNumber;
    }

    public void setUniversityNumber(int universityNumber) {
        this.universityNumber = universityNumber;
    }

    public int getPlanEnrollingNumber() {
        return planEnrollingNumber;
    }

    public void setPlanEnrollingNumber(int planEnrollingNumber) {
        this.planEnrollingNumber = planEnrollingNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
