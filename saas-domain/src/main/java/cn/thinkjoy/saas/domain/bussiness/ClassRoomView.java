package cn.thinkjoy.saas.domain.bussiness;

/**
 * Created by douzy on 16/11/9.
 */
public class ClassRoomView {
    private Long id;
    private Integer tnId;
    private Integer gradeId;
    private String grade;
    private Integer number;
    private Long createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTnId() {
        return tnId;
    }

    public void setTnId(Integer tnId) {
        this.tnId = tnId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
}
