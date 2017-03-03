package cn.thinkjoy.saas.dto;

/**
 * Created by yangguorong on 17/3/1.
 */
public class SelectCourseBaseDto extends BaseDto {

    /**
     * 是否可选 true:可选 false:不可选
     */
    private boolean isSelect = true;

    public boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean select) {
        isSelect = select;
    }
}
