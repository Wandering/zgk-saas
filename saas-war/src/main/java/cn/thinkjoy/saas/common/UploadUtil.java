package cn.thinkjoy.saas.common;

import cn.thinkjoy.saas.domain.Exam;
import cn.thinkjoy.saas.service.IExamService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by liusven on 2016/11/1.
 */
public class UploadUtil
{
    private static Map<Integer, String> courseMap = new HashMap<>();

    static {
        courseMap.put(1, "物理");
        courseMap.put(2, "化学");
        courseMap.put(3, "生物");
        courseMap.put(4, "政治");
        courseMap.put(5, "地理");
        courseMap.put(6, "历史");
        courseMap.put(7, "通用技术");
    }

    public static String uploadFile(HttpServletRequest request)
        throws IOException
    {
        String filePath = "";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
            request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request))
        {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext())
            {
                MultipartFile uploadFile = multiRequest.getFile(iter.next().toString());
                if (uploadFile != null)
                {
                    String path = request.getSession().getServletContext().getRealPath("upload"); ;
                    File distFile = new File(path + uploadFile.getOriginalFilename());
                    uploadFile.transferTo(distFile);
                    MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
                    FileSystemResource resource = new FileSystemResource(distFile);
                    RestTemplate template = new RestTemplate();
                    param.add("file", resource);
                    param.add("productCode", "gk360");
                    param.add("bizSystem", "gk360");
                    param.add("spaceName", "gk360");
                    param.add("userId", "gk360");
                    param.add("dirId", "0");
                    template.getMessageConverters().add(new FastJsonHttpMessageConverter());
                    String returnJson =
                        template.postForObject("http://cs-dev.thinkjoy.com.cn/rest/v1/uploadFile", param, String.class);
                    UploadFileReturn uploadFileReturn = JSON.parseObject(returnJson, UploadFileReturn.class);
                    if (uploadFileReturn != null && "0000000".equals(uploadFileReturn.getRtnCode()))
                    {
                        filePath = uploadFileReturn.getBizData().getFile().getFileUrl();
                        boolean delete = distFile.delete();
                        while (!delete)
                        {
                            delete = distFile.delete();
                        }
                    }
                }
            }
        }
        return filePath;
    }

    public static void saveExcelData(Exam exam, IExamService examService, List<String> headerList)
    {
        String excelPath = exam.getUploadFilePath();
        String fileType = excelPath.substring(excelPath.lastIndexOf(".") + 1, excelPath.length());
        InputStream is = null;
        Workbook wb = null;
        try
        {
            URL url = new URL(excelPath);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            is = new DataInputStream(conn.getInputStream());
            wb = getWorkbook(exam, examService, fileType, is);
            assert wb != null;
            int sheetSize = wb.getNumberOfSheets();
            if (sheetSize > 0)
            {
                setData(exam, examService, headerList, wb);
            }
        }
        catch (Exception e)
        {
            examService.delete(exam.getId());
        }
        finally
        {
            closeResource(is, wb);
        }
    }

    private static void closeResource(InputStream is, Workbook wb)
    {
        if (wb != null)
        {
            try
            {
                wb.close();
            }
            catch (IOException ignored)
            {
            }
        }
        if (is != null)
        {
            try
            {
                is.close();
            }
            catch (IOException ignored)
            {
            }
        }
    }

    private static Workbook getWorkbook(Exam exam, IExamService examService, String fileType, InputStream is)
        throws IOException
    {
        Workbook wb = null;
        switch (fileType)
        {
            case "xls":
                wb = new HSSFWorkbook(is);
                break;
            case "xlsx":
                wb = new XSSFWorkbook(is);
                break;
            default:
                examService.delete(exam.getId());
                break;
        }
        return wb;
    }

    private static void setData(Exam exam, IExamService examService, List<String> headerList,
        Workbook wb)
    {
        Sheet sheet = wb.getSheetAt(0);
        List<Map<String, String>> sheetList = new ArrayList<>();
        Map<String, List<Map<String, String>>> classDataMap = new HashMap<>();
        int rowSize = sheet.getLastRowNum() + 1;
        if (rowSize >= 3)
        {
            for (int j = 2; j < rowSize; j++)
            {
                Row row = sheet.getRow(j);
                if (row == null)
                {
                    continue;
                }
                int cellSize = row.getLastCellNum();
                if (cellSize == headerList.size()-1)
                {
                    Map<String, String> rowMap = new LinkedHashMap<>();
                    rowMap.put("examId", exam.getId() + "");
                    int totalScore = 0;
                    StringBuilder stringBuffer = new StringBuilder();
                    for (int k = 1; k <= cellSize; k++)
                    {
                        totalScore = getCellData(headerList, row, rowMap, totalScore, stringBuffer, k);
                    }
                    rowMap.put("totleScore", totalScore + "");
                    rowMap.put("selectCourses", stringBuffer.substring(1));
                    String className = row.getCell(1).toString();
                    List<Map<String, String>> classData = classDataMap.get(className);
                    if(null == classData)
                    {
                        classData = new ArrayList<>();
                        classDataMap.put(className, classData);
                    }
                    classData.add(rowMap);
                }
            }
            for (Map.Entry<String, List<Map<String, String>>> classDataEntry : classDataMap.entrySet())
            {
                List<Map<String, String>> classData = classDataEntry.getValue();
                sortList(classData, "totleScore", "classRank");
                sheetList.addAll(classData);
            }
            sortList(sheetList, "totleScore", "gradeRank");
            List<String> columnList = new ArrayList<>();
            columnList.addAll(headerList);
            columnList.add("totleScore");
            columnList.add("selectCourses");
            columnList.add("classRank");
            columnList.add("gradeRank");
            examService.batchInsertData("saas_exam_detail", columnList, sheetList);
        }
    }

    private static void sortList(List<Map<String, String>> sheetList, String sortBy, String sortColumn)
    {
        Collections.sort(sheetList, new Comparator<Map<String, String>>()
        {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2)
            {
                return Integer.parseInt(o2.get(sortBy))
                    - Integer.parseInt(o1.get(sortBy));
            }
        });
        for (int i=1; i<=sheetList.size(); i++)
        {
            Map<String, String> data = sheetList.get(i-1);
            data.put(sortColumn, i + "");
        }
    }

    private static int getCellData(List<String> headerList, Row row, Map<String, String> rowMap, int totalScore,
        StringBuilder stringBuffer, int k)
    {
        Cell cell = row.getCell(k - 1);
        String key = headerList.get(k);
        String value = null;
        if (cell != null)
        {
            value = cell.toString();
            if (k >= 3)
            {
                int intValue;
                try
                {
                    intValue = Integer.parseInt(value);
                    if (k >= 6)
                    {
                        stringBuffer.append("@").append(courseMap.get(k - 5));
                    }
                }
                catch (Exception e)
                {
                    intValue = 0;
                }
                totalScore += intValue;
            }
        }
        rowMap.put(key, value);
        return totalScore;
    }
}
