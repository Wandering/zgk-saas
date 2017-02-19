package cn.thinkjoy.saas.service.common;

/**
 * Created by douzy on 17/2/18.
 */
public class TimeTabling {
    static {
        System.load("/home/ubuntu/tm/libtableling.so");
    }
    public native int runTimetabling(String paraDir, String destDir);
}
