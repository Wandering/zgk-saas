package cn.thinkjoy.saas.domain.bussiness;

/**
 * Created by douzy on 17/2/9.
 */
public class ExcelLinkage {
    /**
     * 是否为子级联动项
     */
    private boolean children;

    /**
     * 父级名称
     */
    private String parentName;
    /**
     * 联动数据集
     */
    private String[] arr;

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }
}
