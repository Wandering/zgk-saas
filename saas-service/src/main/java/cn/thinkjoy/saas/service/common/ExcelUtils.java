package cn.thinkjoy.saas.service.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/14.
 */
public class ExcelUtils {
    private static final Logger LOGGER= LoggerFactory.getLogger(ExcelUtils.class);
    /**
     * 生成FileName
     * @param type
     * @return
     */
    public static String getFileName(String type,Integer tnId) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(type);
        stringBuffer.append("_");
        stringBuffer.append(tnId);
        stringBuffer.append("_");
        stringBuffer.append(System.currentTimeMillis());
        return stringBuffer.toString();
    }

    /**
     * 创建excel文档，
     *
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(String columnNames[]) {
        LOGGER.info("===============创建excel文档 S===============");
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet1");
        LOGGER.info("sheet创建");
        LOGGER.info("column总数:"+columnNames.length);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < columnNames.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);
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
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);

        cs.setAlignment(CellStyle.ALIGN_CENTER);

        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        LOGGER.info("===============创建excel文档 E===============");
        return wb;
    }

    /**
     * 创建excel文档，
     *
     * @param columnNames excel的列名
     * @param data excel 值
     */
    public static Workbook createWorkBook(String columnNames[],List<LinkedHashMap<String,Object>> data) {
        LOGGER.info("===============创建excel文档 S===============");
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet1");
        LOGGER.info("sheet创建");
        LOGGER.info("column总数:"+columnNames.length);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < columnNames.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);
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
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);

        cs.setAlignment(CellStyle.ALIGN_CENTER);

        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }

        for(int j=0;j<data.size();j++) {
            Row rowData = sheet.createRow((short) (j+1));

            LinkedHashMap<String,Object> dataLinkedMap=data.get(j);
            int l=0;
            for(Iterator iter = dataLinkedMap.entrySet().iterator();iter.hasNext();) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                System.out.println("myMap.get(\"" + strKey + "\")=" + strObj);
                Cell cell = rowData.createCell(l);
                cell.setCellValue(strObj.toString());
                l++;
            }

        }
        LOGGER.info("===============创建excel文档 E===============");
        return wb;
    }

}
