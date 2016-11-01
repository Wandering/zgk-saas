package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.common.*;
import cn.thinkjoy.saas.domain.Exam;
import cn.thinkjoy.saas.service.IExamDetailService;
import cn.thinkjoy.saas.service.IExamService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liusven on 2016/10/31.
 */
@Controller
@RequestMapping("/scoreAnalyse")
public class ScoreAnalyseController
{
    @Autowired
    Env env;
    @Autowired
    IExamService examService;
    @Autowired
    IExamDetailService examDetailService;
    private static Map<Integer, String> headerMap = new HashMap<>();

    static {
        headerMap.put(1, "studentName");
        headerMap.put(2, "className");
        headerMap.put(3, "yuWenScore");
        headerMap.put(4, "shuXueScore");
        headerMap.put(5, "yingYuScore");
        headerMap.put(6, "wuLiScore");
        headerMap.put(7, "huaXueScore");
        headerMap.put(8, "shengWuScore");
        headerMap.put(9, "zhengZhiScore");
        headerMap.put(10, "diLiScore");
        headerMap.put(11, "liShiScore");
        headerMap.put(12, "commonScore");
    }

    @RequestMapping(value = "/downloadModel", method = RequestMethod.GET)
    @ResponseBody
    public void downloadModel(HttpServletResponse response)
        throws IOException
    {
        Workbook wb = createWorkBook();
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(new Date());
        response.setHeader("Content-Disposition",
            "attachment;filename=" + new String(("student-scores" + time + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
    }

    private Workbook createWorkBook()
    {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("班级成绩");
        CellRangeAddress nameRange = new CellRangeAddress(0, 1, 0, 0);
        CellRangeAddress classRange = new CellRangeAddress(0, 1, 1, 1);
        CellRangeAddress mainCourseRange = new CellRangeAddress(0, 0, 2, 4);
        CellRangeAddress selectCourseRange = new CellRangeAddress(0, 0, 5, 11);
        for (int i = 0; i < 14; i++)
        {
            sheet.setColumnWidth((short)i, (short)(35.7 * 80));
        }
        initHeader(wb, sheet);
        sheet.addMergedRegion(nameRange);
        sheet.addMergedRegion(classRange);
        sheet.addMergedRegion(mainCourseRange);
        sheet.addMergedRegion(selectCourseRange);
        setRegionBorder(1, nameRange, sheet, wb);
        setRegionBorder(1, classRange, sheet, wb);
        setRegionBorder(1, mainCourseRange, sheet, wb);
        setRegionBorder(1, selectCourseRange, sheet, wb);
        initData(wb, sheet, 70, "三年二班");
        return wb;
    }

    private CellStyle getCellStyle(Workbook wb, boolean isHeader)
    {
        CellStyle style = wb.createCellStyle();
        Font f = wb.createFont();
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setFontName("微软雅黑");
        if (isHeader)
        {
            f.setFontHeightInPoints((short)14);
            f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        }
        else
        {
            f.setFontHeightInPoints((short)11);
            f.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        }
        style.setFont(f);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        if (isHeader)
        {
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }
        return style;
    }

    private void initHeader(Workbook wb, Sheet sheet)
    {
        CellStyle style = getCellStyle(wb, true);
        Row row1 = sheet.createRow((short)0);
        Row row2 = sheet.createRow((short)1);
        Map<Integer, String> columnOneMap = new HashMap<>();
        columnOneMap.put(0, "姓名");
        columnOneMap.put(1, "班级");
        columnOneMap.put(2, "主课");
        columnOneMap.put(5, "选课");
        Map<Integer, String> columnTwoMap = new HashMap<>();
        columnTwoMap.put(2, "语文");
        columnTwoMap.put(3, "数学");
        columnTwoMap.put(4, "英语");
        columnTwoMap.put(5, "物理");
        columnTwoMap.put(6, "化学");
        columnTwoMap.put(7, "生物");
        columnTwoMap.put(8, "政治");
        columnTwoMap.put(9, "地理");
        columnTwoMap.put(10, "历史");
        columnTwoMap.put(11, "通用技术");
        setColumnValues(row1, style, columnOneMap);
        setColumnValues(row2, style, columnTwoMap);
    }

    private void initData(Workbook wb, Sheet sheet, int dataLength, String className)
    {
        CellStyle style = getCellStyle(wb, false);
        for (int i = 1; i <= dataLength; i++)
        {
            Row row = sheet.createRow((short)(i + 1));
            Map<Integer, String> columnMap = new HashMap<>();
            columnMap.put(0, RandomValue.getChineseName());
            columnMap.put(1, className);
            columnMap.put(2, RandomValue.getScore(60, 150));
            columnMap.put(3, RandomValue.getScore(60, 150));
            columnMap.put(4, RandomValue.getScore(60, 150));
            for (int j = 5; j < 12; j++)
            {
                columnMap.put(j, "");
            }
            Set<Integer> randomIndexs = RandomValue.getIndex();
            for (Integer index : randomIndexs)
            {
                columnMap.put(index, RandomValue.getScore(60, 120));
            }
            setColumnValues(row, style, columnMap);
        }
    }

    private void setColumnValues(Row row, CellStyle style, Map<Integer, String> columnOneMap)
    {
        for (Map.Entry<Integer, String> entry : columnOneMap.entrySet())
        {
            Cell cell = row.createCell(entry.getKey());
            cell.setCellValue(entry.getValue());
            cell.setCellStyle(style);
        }
    }

    private void setRegionBorder(int border, CellRangeAddress region, Sheet sheet, Workbook wb)
    {
        RegionUtil.setBorderBottom(border, region, sheet, wb);
        RegionUtil.setBorderLeft(border, region, sheet, wb);
        RegionUtil.setBorderRight(border, region, sheet, wb);
        RegionUtil.setBorderTop(border, region, sheet, wb);
    }

    @RequestMapping("/uploadData")
    @ResponseBody
    public Map uploadExcel(HttpServletRequest request)
    {
        Map<String, String> resultMap = new HashMap<>();
        try
        {
            resultMap.put("filePath", UploadUtil.uploadFile(request));
        }
        catch (IOException e)
        {
            throw new BizException("0000111", "上传错误，请重新上传！");
        }
        return resultMap;
    }

    @RequestMapping("/addExam")
    @ResponseBody
    public Exam addExam(Exam exam)
    {
        exam.setCreateDate(TimeUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss sss"));
        examService.add(exam);
        String excelPath = exam.getUploadFilePath();
        String fileType = excelPath.substring(excelPath.lastIndexOf(".") + 1, excelPath.length());
        InputStream is = null;
        Workbook wb = null;
        try {
            URL url = new URL(excelPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new DataInputStream(conn.getInputStream());

            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
                examService.delete(exam.getId());
            }
            int sheetSize = wb.getNumberOfSheets();
            if(sheetSize>0)
            {
                Sheet sheet = wb.getSheetAt(0);
                List<Map<String, String>> sheetList = new ArrayList<>();//对应sheet页
                int rowSize = sheet.getLastRowNum() + 1;
                if(rowSize>=3)
                {
                    for (int j = 1; j < rowSize; j++) {//遍历行
                        Row row = sheet.getRow(j);
                        if (row == null) {//略过空行
                            continue;
                        }
                        int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                        if(cellSize == headerMap.size())
                        {
                            Map<String, String> rowMap = new HashMap<>();//对应一个数据行
                            for (int k = 1; k <= cellSize; k++) {
                                Cell cell = row.getCell(k-1);
                                String key = headerMap.get(k);
                                String value = null;
                                if (cell != null) {
                                    value = cell.toString();
                                }
                                rowMap.put(key, value);
                            }
                            sheetList.add(rowMap);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (wb != null) {
                try
                {
                    wb.close();
                }
                catch (IOException e)
                {
                }
            }
            if (is != null) {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        return exam;
    }
}
