package cn.thinkjoy.saas.dto;

import java.io.Serializable;

/**
 * Created by zuohao on 16/11/1.
 */
public class AnalysisDto implements Serializable {

    private String batchName;
    private String disciplineName;
    private String number;
    private String percent;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
