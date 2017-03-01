package cn.thinkjoy.saas.service.common;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
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
    public static Workbook createWorkBook(String columnNames[],List<Map<Integer,Object>> selList) {
        LOGGER.info("===============创建excel文档 S===============");
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet1");


        sheet=lockSelect(sheet,selList);

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
     */
    public static Workbook createWorkBook(String columnNames[],String[] sheetNames,List<List<List<String>>> datas) {
        LOGGER.info("===============创建excel文档 S===============");
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        for (int m = 0 ;m < sheetNames.length ; m ++ ) {
            String sheetName = sheetNames[m];
            // 创建第一个sheet（页），并命名
            Sheet sheet = wb.createSheet(sheetName);
            List<List<String>> data = datas.get(m);

            LOGGER.info("sheet创建:"+sheetName);
            LOGGER.info("column总数:" + columnNames.length);
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
            LOGGER.info("data总数："+data.size());
            for(int j=0 ;j<data.get(0).size();j++) {
                Row rowData = sheet.createRow((short) (j+1));
//                List<String> lls=data.get(j);
//                for(int n = 0 ; n < lls.size() ; n ++) {
//
//                    Cell cell = rowData.createCell(0);
//                    cell.setCellValue(lls.get(n));
//                }

            }
            for(int j=0 ;j<data.size();j++) {

                List<String> lls=data.get(j);
                for(int n = 0 ; n < lls.size() ; n ++) {
                    Row rowData = sheet.getRow(n+1);
                    Cell cell = rowData.createCell(j);
                    cell.setCellValue(lls.get(n));
                }

            }
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
    public static Workbook createWorkBook(String columnNames[],List<LinkedHashMap<String,Object>> data,List<Map<Integer,Object>> selList) {
        LOGGER.info("===============创建excel文档 S===============");
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet1");

        sheet=lockSelect(sheet,selList);

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
        LOGGER.info("data总数："+data.size());
        for(int j=0;j<data.size();j++) {
            Row rowData = sheet.createRow((short) (j+1));

            LinkedHashMap<String,Object> dataLinkedMap=data.get(j);
            int l=0;
            for(Iterator iter = dataLinkedMap.entrySet().iterator();iter.hasNext();) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                LOGGER.info(l+"行key:"+strKey);
                LOGGER.info(l+"行value:"+strObj);
                if (strKey.equals("id"))
                    continue;

                Cell cell = rowData.createCell(l);
                cell.setCellValue(strObj.toString());
                l++;
            }

        }
        LOGGER.info("===============创建excel文档 E===============");
        return wb;
    }

    /**
     * 下拉框锁定
     * @param sheet
     * @param selList
     * @return
     */
    private static Sheet lockSelect(Sheet sheet,List<Map<Integer,Object>> selList) {
        if (selList != null && selList.size() > 0) {
            for (int i = 0; i < selList.size(); i++) {
                Map<Integer, Object> selMap = selList.get(i);
                for (Map.Entry<Integer, Object> entry : selMap.entrySet()) {
                    sheet = setHSSFValidation(sheet, (String[]) entry.getValue(), 1, 5000, entry.getKey(), entry.getKey());// 第一列的前50
                }
            }
        }

        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet
     *            要设置的sheet.
     * @param textlist
     *            下拉框显示的内容
     * @param firstRow
     *            开始行
     * @param endRow
     *            结束行
     * @param firstCol
     *            开始列
     * @param endCol
     *            结束列
     * @return 设置好的sheet.
     */
    public static Sheet setHSSFValidation(Sheet sheet,
                                              String[] textlist, int firstRow, int endRow, int firstCol,
                                              int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint
                .createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(
                regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

}
