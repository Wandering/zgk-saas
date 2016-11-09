package cn.thinkjoy.saas.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangguorong on 16/10/14.
 */
public class MeunDto implements Serializable {

    /**
     * 菜单ID
     */
    private int meunId;

    /**
     * 菜单名
     */
    private String meunName;

    /**
     * 菜单所对应链接
     */
    private String meunUrl;

    /**
     * 菜单图标集合
     */
    private String iconUrl;

    /**
     * 子菜单集合
     */
    private List<MeunDto> sonMeuns;

    public int getMeunId() {
        return meunId;
    }

    public void setMeunId(int meunId) {
        this.meunId = meunId;
    }

    public String getMeunName() {
        return meunName;
    }

    public void setMeunName(String meunName) {
        this.meunName = meunName;
    }

    public String getMeunUrl() {
        return meunUrl;
    }

    public void setMeunUrl(String meunUrl) {
        this.meunUrl = meunUrl;
    }

    public List<MeunDto> getSonMeuns() {
        return sonMeuns;
    }

    public void setSonMeuns(List<MeunDto> sonMeuns) {
        this.sonMeuns = sonMeuns;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "MeunDto{" +
                "meunId=" + meunId +
                ", meunName='" + meunName + '\'' +
                ", meunUrl='" + meunUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", sonMeuns=" + sonMeuns +
                '}';
    }
}
