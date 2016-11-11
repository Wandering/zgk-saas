package cn.thinkjoy.saas.domain;

import cn.thinkjoy.common.domain.BaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by liusven on 2016/11/10.
 */
public class ExamScoreRatio  extends BaseDomain<Long> implements Comparable
{
    private String courseName;
    private float ratio;

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public float getRatio()
    {
        return ratio;
    }

    public void setRatio(float ratio)
    {
        this.ratio = ratio;
    }

    @Override
    public int compareTo(Object o)
    {
        return this.getRatio() - ((ExamScoreRatio)o).getRatio()>=0? 1: -1;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ExamScoreRatio that = (ExamScoreRatio)o;

        return new EqualsBuilder()
            .append(ratio, that.ratio)
            .append(courseName, that.courseName)
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
            .append(courseName)
            .append(ratio)
            .toHashCode();
    }

    @Override
    public String toString()
    {
        return "ExamScoreRatio{" +
            "courseName='" + courseName + '\'' +
            ", ratio=" + ratio +
            '}';
    }
}
