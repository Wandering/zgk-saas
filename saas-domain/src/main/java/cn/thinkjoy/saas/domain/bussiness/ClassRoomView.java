package cn.thinkjoy.saas.domain.bussiness;

/**
 * Created by douzy on 16/11/9.
 */
public class ClassRoomView {
    private Long id;
    private Integer tnId;
    private Integer gradeId;
    private String grade;
    private Integer executiveNumber;
    private Integer dayNumber;
    private Integer roomOrder;
    private Long createDate;
    private int scheduleNumber;

    public int getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(int scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

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

    public Integer getExecutiveNumber() {
        return executiveNumber;
    }

    public void setExecutiveNumber(Integer executiveNumber) {
        this.executiveNumber = executiveNumber;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Integer getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(Integer roomOrder) {
        this.roomOrder = roomOrder;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
}
