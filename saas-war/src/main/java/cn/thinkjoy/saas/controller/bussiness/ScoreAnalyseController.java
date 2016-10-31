package cn.thinkjoy.saas.controller.bussiness;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liusven on 2016/10/31.
 */
@Controller
@RequestMapping("/scoreAnalyse")
public class ScoreAnalyseController
{

    @RequestMapping(value = "/downloadModel",method = RequestMethod.GET)
    @ResponseBody
    public void downloadModel(HttpServletResponse response)
        throws IOException
    {
        Workbook wb = createWorkBook();
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(new Date());
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("student-score-info"+ time + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
    }

    private Workbook createWorkBook()
    {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("班级成绩");
        CellRangeAddress nameRange = new CellRangeAddress(0, 1, 0, 0);
        CellRangeAddress classRange = new CellRangeAddress(0, 1, 1, 1);
        CellRangeAddress mainCourseRange = new CellRangeAddress(0, 0, 2, 4);
        CellRangeAddress selectCourseRange = new CellRangeAddress(0, 0, 5, 11);
        sheet.addMergedRegion(nameRange);
        sheet.addMergedRegion(classRange);
        sheet.addMergedRegion(mainCourseRange);
        sheet.addMergedRegion(selectCourseRange);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < 14; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        initHeader(wb, sheet);
        return wb;
    }

    private CellStyle getCellStyle(Workbook wb)
    {
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        // 创建两种字体
        Font f = wb.createFont();
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        return cs;
    }

    private void initHeader(Workbook wb, Sheet sheet)
    {
        CellStyle cs = getCellStyle(wb);
        // 创建第一行
        Row row0 = sheet.createRow((short) 0);
        Row row1 = sheet.createRow((short) 1);
        Map<String, Integer> columnOneMap = new HashMap<>();
        columnOneMap.put("姓名", 0);
        columnOneMap.put("班级", 1);
        columnOneMap.put("主课", 2);
        columnOneMap.put("选课", 5);
        Map<String, Integer> columnTwoMap = new HashMap<>();
        columnTwoMap.put("姓名", 0);
        columnTwoMap.put("班级", 1);
        columnTwoMap.put("语文", 2);
        columnTwoMap.put("数学", 3);
        columnTwoMap.put("英语", 4);
        columnTwoMap.put("物理", 5);
        columnTwoMap.put("化学", 6);
        columnTwoMap.put("生物", 7);
        columnTwoMap.put("政治", 8);
        columnTwoMap.put("地理", 9);
        columnTwoMap.put("历史", 10);
        columnTwoMap.put("通用技术", 11);
        setColumnValues(row0, cs, columnOneMap);
        setColumnValues(row1, cs, columnTwoMap);
    }

    private void setColumnValues(Row row, CellStyle cs, Map<String, Integer> columnOneMap)
    {
        for (Map.Entry<String, Integer> entry: columnOneMap.entrySet())
        {
            Cell cell = row.createCell(entry.getValue());
            cell.setCellValue(entry.getKey());
            cell.setCellStyle(cs);
        }
    }
}
