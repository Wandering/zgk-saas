package cn.thinkjoy.saas.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

public class ExamDetail extends BaseDomain<Long> implements Comparable{
    /**  */
    private Long examId;
    /**  */
    private String studentName;
    /**  */
    private String className;
    /**  */
    private String yuWenScore;
    /**  */
    private String shuXueScore;
    /**  */
    private String yingYuScore;
    /**  */
    private String wuLiScore;
    /**  */
    private String huaXueScore;
    /**  */
    private String shengWuScore;
    /**  */
    private String zhengZhiScore;
    /**  */
    private String diLiScore;
    /**  */
    private String liShiScore;
    /**  */
    private String commonScore;
    /**  */
    private String totleScore;
    /**  */
    private String classRank;
    /**  */
    private String gradeRank;
    /**  */
    private String selectCourses;

	public ExamDetail(){
	}
    public void setExamId(Long value) {
        this.examId = value;
    }

    public Long getExamId() {
        return this.examId;
    }
    public void setStudentName(String value) {
        this.studentName = value;
    }

    public String getStudentName() {
        return this.studentName;
    }
    public void setClassName(String value) {
        this.className = value;
    }

    public String getClassName() {
        return this.className;
    }
    public void setYuWenScore(String value) {
        this.yuWenScore = value;
    }

    public String getYuWenScore() {
        return this.yuWenScore;
    }
    public void setShuXueScore(String value) {
        this.shuXueScore = value;
    }

    public String getShuXueScore() {
        return this.shuXueScore;
    }
    public void setYingYuScore(String value) {
        this.yingYuScore = value;
    }

    public String getYingYuScore() {
        return this.yingYuScore;
    }
    public void setWuLiScore(String value) {
        this.wuLiScore = value;
    }

    public String getWuLiScore() {
        return this.wuLiScore;
    }
    public void setHuaXueScore(String value) {
        this.huaXueScore = value;
    }

    public String getHuaXueScore() {
        return this.huaXueScore;
    }
    public void setShengWuScore(String value) {
        this.shengWuScore = value;
    }

    public String getShengWuScore() {
        return this.shengWuScore;
    }
    public void setZhengZhiScore(String value) {
        this.zhengZhiScore = value;
    }

    public String getZhengZhiScore() {
        return this.zhengZhiScore;
    }
    public void setDiLiScore(String value) {
        this.diLiScore = value;
    }

    public String getDiLiScore() {
        return this.diLiScore;
    }
    public void setLiShiScore(String value) {
        this.liShiScore = value;
    }

    public String getLiShiScore() {
        return this.liShiScore;
    }
    public void setCommonScore(String value) {
        this.commonScore = value;
    }

    public String getCommonScore() {
        return this.commonScore;
    }
    public void setTotleScore(String value) {
        this.totleScore = value;
    }

    public String getTotleScore() {
        return this.totleScore;
    }
    public void setClassRank(String value) {
        this.classRank = value;
    }

    public String getClassRank() {
        return this.classRank;
    }
    public void setGradeRank(String value) {
        this.gradeRank = value;
    }

    public String getGradeRank() {
        return this.gradeRank;
    }
    public void setSelectCourses(String value) {
        this.selectCourses = value;
    }

    public String getSelectCourses() {
        return this.selectCourses;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ExamId",getExamId())
			.append("StudentName",getStudentName())
			.append("ClassName",getClassName())
			.append("YuWenScore",getYuWenScore())
			.append("ShuXueScore",getShuXueScore())
			.append("YingYuScore",getYingYuScore())
			.append("WuLiScore",getWuLiScore())
			.append("HuaXueScore",getHuaXueScore())
			.append("ShengWuScore",getShengWuScore())
			.append("ZhengZhiScore",getZhengZhiScore())
			.append("DiLiScore",getDiLiScore())
			.append("LiShiScore",getLiShiScore())
			.append("CommonScore",getCommonScore())
			.append("TotleScore",getTotleScore())
			.append("ClassRank",getClassRank())
			.append("GradeRank",getGradeRank())
			.append("SelectCourses",getSelectCourses())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
            .append(getId())
            .append(getExamId())
			.append(getStudentName())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExamDetail == false) return false;
		if(this == obj) return true;
		ExamDetail other = (ExamDetail)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.append(getExamId(),other.getExamId())
            .append(getStudentName(),other.getStudentName())
			.isEquals();
	}

    @Override
    public int compareTo(Object o)
    {
        return this.getId().intValue() - ((ExamDetail)o).getId().intValue();
    }
}

