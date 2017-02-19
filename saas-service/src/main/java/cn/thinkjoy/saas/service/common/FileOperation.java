package cn.thinkjoy.saas.service.common;

import java.io.*;
import java.util.Calendar;

/**
 * Created by douzy on 17/2/15.
 */
public class FileOperation {

    //行政班不排课 --班级粒度
    public static final String ADMIN_CLASS_NON_DISPACHING="admin_class_non_dispaching";
    //班级不排课 --课程粒度
    public static final String CLASS_NON_DISPACHING="class_non_dispaching";
    //课程不排课
    public static final String COURSE_NON_DISPACHING="course_non_dispaching";
    //班级信息
    public static final String CLASS_INFO="class_info";
    //课程信息
    public static final String COURSE_INFORMATION="course_information";
    //课程时间轴
    public static final String COURSE_TIMESLOTS="course_timeslots";
    //年级不排课
    public static final String GRAD_NON_DISPACHING="grad_non_dispaching";
    //教师设置
    public static final String TEACHERS_SETTING="teachers_setting";

    public static final String SCHEDULE_RESULT="result";

    public static final String LINE_SPLIT="\r\n";
    public static final String STR_SPLIT="\t";
    public static final String CHAR_SPLIT=" ";



//    private static String path = "C:\\timetable\\schedule\\task\\"; ///Users/douzy/ 文件保存路径设置
    private static String path = "/Users/dengshaofei/schedule/task/"; // 文件保存路径设置

    private static String filenameTemp;

    /**
     *
     * @param tnId
     * @param taskId
     * @return
     */
    public static String getParamsPath(Integer tnId,Integer taskId) {
        Calendar now = Calendar.getInstance();
        return path + tnId + "/" + taskId + "/" + now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/";
//        return path + tnId + "\\" + taskId + "\\" + now.get(Calendar.YEAR) + "\\" + (now.get(Calendar.MONTH) + 1) + "\\" + now.get(Calendar.DAY_OF_MONTH) + "\\";
    }

    public static boolean creatTxtFile(Integer tnId,Integer taskId,String name) throws IOException {

        boolean flag = false;

        filenameTemp = getParamsPath(tnId,taskId) + name + ".txt";

        File filename = new File(filenameTemp);

        if (!filename.exists()) {
            filename.getParentFile().mkdirs();
            filename.createNewFile();
            flag = true;

        } else {
            try {
                FileWriter fw5 = new FileWriter(filename);
                BufferedWriter bw1 = new BufferedWriter(fw5);
                bw1.write("");
                flag = true;
            } catch (Exception e) {
                flag = false;
            }
        }

        return flag;

    }
    public static String readerTxtString(Integer taskId,Integer tnId,String name) {

        String filenPath = getParamsPath(tnId, taskId) + name + ".txt";

        File file = new File(filenPath);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    public static boolean writeTxtFile(String newStr) throws IOException {

// 先读取原有文件内容，然后进行写入操作

        boolean flag = false;

        String filein = newStr ;//+ "\r\n";

        String temp = "";

        FileInputStream fis = null;

//        InputStreamReader isr = null;

//        BufferedReader br = null;

        FileOutputStream fos = null;

        PrintWriter pw = null;

        try {

// 文件路径

            File file = new File(filenameTemp);

// 将文件读入输入流

            fis = new FileInputStream(file);

//            isr = new InputStreamReader(fis);

//            br = new BufferedReader(isr);

            StringBuffer buf = new StringBuffer();

// 保存该文件原有的内容
//
//            for (int j = 1; (temp = br.readLine()) != null; j++) {
//
//                buf = buf.append(temp);
//
//// System.getProperty("line.separator")
//
//// 行与行之间的分隔符 相当于“\n”
//
//                buf = buf.append(System.getProperty("line.separator"));
//
//            }

            buf.append(filein);

            fos = new FileOutputStream(file);

            pw = new PrintWriter(fos);

            pw.write(buf.toString().toCharArray());

            pw.flush();

            flag = true;

        } catch (IOException e1) {

// TODO 自动生成 catch 块

            throw e1;

        } finally {

            if (pw != null) {

                pw.close();

            }

            if (fos != null) {

                fos.close();

            }

//            if (br != null) {
//
//                br.close();
//
//            }
//
//            if (isr != null) {
//
//                isr.close();
//
//            }

            if (fis != null) {

                fis.close();

            }

        }

        return flag;

    }

    public static void main(String[] args) throws IOException {

        creatTxtFile(13,28,"20160725测试文件");

        writeTxtFile("0,0,0,0,0,0\r\n0,2,1,3,4,2\r1,2,3,1,1,2\n1,2,3,4 1,2,3,22");
    }
}
