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

    //学生信息
    public static final String STUDENT_SELECTION="student_selection";

    //教室信息
    public static final String CLASS_ROOM="room";
    //教师设置
    public static final String TEACHERS_SETTING="teachers_setting_preference";


    public static final String PARMETERS="parameters";

    public static final String SCHEDULE_RESULT="result"; //排课结果
    public static final String AD_RESULT_TXT="ad_result";//调课结果

    public static final String LINE_SPLIT="\r\n";
    public static final String STR_SPLIT="\t";
    public static final String CHAR_SPLIT=" ";
    public static final String ERROR_TXT="error.txt";
    public static final String FAIL_TXT="FAIL.txt";
    public static final String AD_ERROR_TXT="ad_error.txt";


    public static final String ADJUSTMENT_TXT="adjustment.txt";//调课颜色信息
    public static final String EXCHANGE_TXT="exchanges.txt";//原因信息


    //硬性规则
    public static final String  NON_ADM_ERROR_MSG="不排课时间过多,行政班课时不足。班级:%s,课时:%s";
    public static final String  NON_TEACHER_ERROR_MSG="不排课时间过多,老师课时不足。老师:%s,课时:%s";
    public static final String  NON_COURSE_ERROR_MSG="不排课时间过多,【%s-%s】课程课时不足。";

    //软性规则
    public static final String MERGE_CLASS_FAIL_MSG="合班失败!时间:周%s,班级:%s,课程:%s";
    public static final String CON_NUMBER_FAIL_MSG="连堂数目违反规则。 班级:%s,课程:%s,预设规则连堂数目%s,课表连堂课程数目%s";
    public static final String NON_CON_NUMBER_FAIL_MSG="不连堂违反规则。课程:%s";
    public static final String CON_TEACHER_FAIL_MSG="老师连上违反规则。时间:周%s,老师:%s,课程:%s";
    public static final String NO_JAPQ_FAIL_MSG="教案平齐违反规则。时间:周%s,老师:%s,课程:%s";




    //    private static String path = "C:\\timetable\\schedule\\task\\"; //windows server 文件保存路径设置
    private static String path = "/Users/douzy/schedule/task/"; //本地 文件保存路径设置
//    private static String path = "/home/ubuntu/tm/schedule/task/"; //线上 文件保存路径设置

    private static String filenameTemp;

    /**
     *
     * @param tnId
     * @param taskId
     * @return
     */
    public static String getParamsPath(Integer tnId,Integer taskId,String type) {
        Calendar now = Calendar.getInstance();
        return path + tnId + "/" + taskId + "/" + now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/" + type + "/";
//        return path + tnId + "\\" + taskId + "\\" + now.get(Calendar.YEAR) + "\\" + (now.get(Calendar.MONTH) + 1) + "\\" + now.get(Calendar.DAY_OF_MONTH) + "\\";
    }

    public static boolean creatTxtFile(Integer tnId,Integer taskId,String name,String type) throws IOException {

        boolean flag = false;

        filenameTemp = getParamsPath(tnId,taskId,type) + name + ".txt";

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
    public static boolean removeAllFile(File file) {
        boolean flag = false;
        try {
            if (file.isFile() || file.list().length == 0) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    removeAllFile(files[i]);
                    files[i].delete();
                }
            }
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    public static String readerTxtString(String filenPath,String name) {

        String f =filenPath + name;

        File file = new File(f);
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

        String s="3\n" +
                "4\n" +
                "1 0 0 0 0 0 1 1 1 \t1 1 1 0 1 1 1 1 1 \t1 1 1 1 1 0 1 1 1 \t1 1 1 0 1 1 1 1 1 \t1 1 1 1 1 0 1 1 1 \t1 1 1 1 1 1 1 1 1 \t1 1 1 1 1 1 1 1 1 \t\n" +
                "8\n" +
                "1 1 1 1 1 1 1 0 0 \t1 1 1 1 0 0 1 1 1 \t1 1 1 1 1 0 1 1 1 \t1 1 1 1 0 0 1 1 1 \t1 1 1 1 1 1 1 1 1 \t1 1 1 1 1 1 1 1 1 \t1 1 1 1 1 1 1 1 1 \t\n" +
                "19\n" +
                "0 1 1 1 1 0 1 1 0 \t0 0 1 1 1 1 1 1 1 \t1 0 1 1 1 1 1 1 1 \t0 0 1 1 1 1 1 1 1 \t1 1 0 1 1 1 1 1 1 \t1 1 1 1 1 1 1 1 1 \t1 1 1 1 1 1 1 1 1 \t\n";

//        System.out.print(4093);
//        System.out.print();
        String d=s.substring(0,s.lastIndexOf("\t"));
        System.out.print(d.length());
        System.out.print(d);

        creatTxtFile(13,28,"20160725测试文件","");

        writeTxtFile("0,0,0,0,0,0\r\n0,2,1,3,4,2\r1,2,3,1,1,2\n1,2,3,4 1,2,3,22");
    }
}
