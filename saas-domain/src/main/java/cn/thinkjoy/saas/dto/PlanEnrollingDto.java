package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/11/1.
 */
public class PlanEnrollingDto implements Serializable {

    private String property;
    private String planEnrolling;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPlanEnrolling() {
        return planEnrolling;
    }

    public void setPlanEnrolling(String planEnrolling) {
        this.planEnrolling = planEnrolling;
    }
}
