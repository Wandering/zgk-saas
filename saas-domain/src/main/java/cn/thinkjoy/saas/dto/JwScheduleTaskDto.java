package cn.thinkjoy.saas.dto;

import cn.thinkjoy.saas.domain.JwScheduleTask;

/**
 * Created by yangyongping on 2016/12/6.
 */
public class JwScheduleTaskDto extends JwScheduleTask{
    private String gradeName;
    private String termName;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
