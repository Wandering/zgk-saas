package cn.thinkjoy.saas.service.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by douzy on 17/2/21.
 */
public class ReadCmdLine {
    public static void run(String path) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("/opt/lib/timetable.sh " + path + " " + path + "");
            //读取标准输出流
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line=bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            //读取标准错误流
            BufferedReader brError = new BufferedReader(new InputStreamReader(proc.getErrorStream(), "gb2312"));
            String errline = null;
            while ((errline = brError.readLine()) != null) {
                System.out.println(errline);
            }
            //waitFor()判断Process进程是否终止，通过返回值判断是否正常终止。0代表正常终止
            int c=proc.waitFor();
            if(c!=0)
                System.out.println("Process进程异常终止");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
