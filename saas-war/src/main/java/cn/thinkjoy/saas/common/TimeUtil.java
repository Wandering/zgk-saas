package cn.thinkjoy.saas.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liusven on 2016/11/1.
 */
public class TimeUtil
{
    public static String getTimeStamp(String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }
}
