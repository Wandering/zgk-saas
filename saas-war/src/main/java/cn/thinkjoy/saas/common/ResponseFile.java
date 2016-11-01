package cn.thinkjoy.saas.common;

import java.io.Serializable;

/**
 * Created by liusven on 2016/11/1.
 */
public class ResponseFile implements Serializable
{
    private String fileUrl;

    public String getFileUrl()
    {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }
}
