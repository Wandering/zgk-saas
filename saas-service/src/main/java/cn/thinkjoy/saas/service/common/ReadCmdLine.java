package cn.thinkjoy.saas.service.common;

/**
 * Created by douzy on 17/2/21.
 */
public class ReadCmdLine {
    public static void run(String path) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("sh /opt/lib/timetable.sh " + path + " " + path + "");
//            InputStream stderr = proc.getErrorStream();
//            InputStreamReader isr = new InputStreamReader(stderr);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//            System.out.println("<error></error>");
//            while ((line = br.readLine()) != null)
//                System.out.println(line);
//            System.out.println("");
//            int exitVal = proc.waitFor();
//            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
