package cn.thinkjoy.saas.common;

import java.io.Serializable;

/**
 * Created by liusven on 2016/11/1.
 */
public class BizData implements Serializable
{
    private String result;
    private String des;
    private ResponseFile file;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public ResponseFile getFile() {
        return file;
    }

    public void setFile(ResponseFile file) {
        this.file = file;
    }
}
