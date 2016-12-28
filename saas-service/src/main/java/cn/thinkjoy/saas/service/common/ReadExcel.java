package cn.thinkjoy.saas.service.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by douzy on 16/10/17.
 */
public class ReadExcel {
    private static final Logger LOGGER= LoggerFactory.getLogger(ReadExcel.class);


    List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
    HSSFWorkbook workbook;

    /**
     * poi 解析excel
     * @param filePath
     * @return
     */
    public List<LinkedHashMap<String,String>> readExcelFile(String filePath,Integer columnLen) {
        LOGGER.info("=============poi 解析excel S=============");
        LOGGER.info("excel file path:"+filePath);
        try {
            FileInputStream excelFile = new FileInputStream(filePath);
            workbook = new HSSFWorkbook(excelFile);
            //读入Excel文件的第一个表
            HSSFSheet sheet = workbook.getSheetAt(0);
            //从文件第二行开始读取，第一行为标识行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                LOGGER.info("行数：" + sheet.getPhysicalNumberOfRows());
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//                row.getPhysicalNumberOfCells()-1
                for (int j = 0; j <= (columnLen-1); j++) {
                    if (row.getCell(j) != null) {
                        String str = getCellValue(row.getCell(j));
                        LOGGER.info(i + "行-" + j + "列:" + str);
                        map.put(j + "", str);
                    } else
                        map.put(j + "", EnumUtil.EXCEL_VALUE_NOTHING);
                }
                if (map.size() > 0)
                    list.add(map);
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.info("【Excel路径有误，请重新确认Excel路径...】");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("【文件输入有误，请重新确定您要加入的文件...】");
            return null;
        } finally {
            LOGGER.info("=============poi 解析excel E=============");
        }
    }
    //传入cell的值，进行cell值类型的判断，并返回String类型
    private static String getCellValue(HSSFCell cell) {
        String value = null;
        //简单的查检列类型
        System.out.println("cell.getCellType():" + cell.getCellType());
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING://字符串
                System.out.println("HSSFCell.CELL_TYPE_STRING:" + HSSFCell.CELL_TYPE_STRING);
                value = cell.getRichStringCellValue().toString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC://数字
                System.out.println("HSSFCell.CELL_TYPE_NUMERIC:" + HSSFCell.CELL_TYPE_NUMERIC);
                long dd = (long) cell.getNumericCellValue();
                value = dd + "";
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                System.out.println("HSSFCell.CELL_TYPE_BLANK:" + HSSFCell.CELL_TYPE_BLANK);
                value = "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                System.out.println("HSSFCell.CELL_TYPE_FORMULA:" + HSSFCell.CELL_TYPE_FORMULA);
                value = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值
                System.out.println("HSSFCell.CELL_TYPE_BOOLEAN:" + HSSFCell.CELL_TYPE_BOOLEAN);
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                System.out.println("HSSFCell.CELL_TYPE_ERROR:" + HSSFCell.CELL_TYPE_ERROR);
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:

                System.out.println("default");
                break;
        }
        return value;
    }
}
