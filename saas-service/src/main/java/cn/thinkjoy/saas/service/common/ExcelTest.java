package cn.thinkjoy.saas.service.common;


import cn.thinkjoy.saas.domain.bussiness.ExcelLinkage;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by douzy on 17/2/9.
 */
public class ExcelTest {
    public static String outputFile = "/Users/douzy/Downloads/shengcheng.xls "; // 生成的文件
    public static String fileToBeRead = "/Users/douzy/demo.xls";// 模板


    private static String EXCEL_HIDE_SHEET_NAME = "poihide";
    private static String HIDE_SHEET_NAME_YN = "yesOrNOList";
    private static String HIDE_SHEET_NAME_PROVINCE = "provinceList";
    //设置下拉列表的内容
    private static String[] yesOrNOList = {"是", "否"};
    private static String[] provinceList = {"广东省", "河南省"};
    private static String[] gzProvinceList = {"广州", "深圳", "珠海"};
    private static String[] hnProvinceList = {"郑州", "洛阳", "济源"};

    static ArrayList arrayList=new ArrayList();

    public static void main(String[] args) {

        Workbook wb = new HSSFWorkbook();
        createExcel(wb);
        creatExcelHidePage(wb);
        setDataValidation(wb);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(fileToBeRead);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createExcel(Workbook wb) {
        Sheet sheet = wb.createSheet("测试");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("是否转售");
        cell = row.createCell(1);
        cell.setCellValue("省份");
        cell = row.createCell(2);
        cell.setCellValue("本地网");
        cell = row.createCell(3);
    }

    public static void creatExcelHidePage(Workbook workbook) {

        ExcelLinkage excelLinkage=new ExcelLinkage();
        excelLinkage.setChildren(false);
        excelLinkage.setArr(yesOrNOList);


        ExcelLinkage excelLinkage1=new ExcelLinkage();
        excelLinkage1.setChildren(false);
        excelLinkage1.setArr(provinceList);


        ExcelLinkage excelLinkage2=new ExcelLinkage();
        excelLinkage2.setChildren(true);
        excelLinkage2.setParentName("广东省");
        excelLinkage2.setArr(gzProvinceList);


        ExcelLinkage excelLinkage3=new ExcelLinkage();
        excelLinkage3.setChildren(true);
        excelLinkage3.setParentName("河南省");
        excelLinkage3.setArr(hnProvinceList);

        arrayList.add(excelLinkage);
        arrayList.add(excelLinkage1);
        arrayList.add(excelLinkage2);
        arrayList.add(excelLinkage3);

        Sheet hideInfoSheet = workbook.createSheet(EXCEL_HIDE_SHEET_NAME);//隐藏一些信息

        for(int i=0;i<arrayList.size();i++){
            ExcelLinkage excelLinkageModel=(ExcelLinkage)arrayList.get(i);
            String[] strAr=excelLinkageModel.getArr();
            Row sexRow = hideInfoSheet.createRow(i);
            creatRow(sexRow, strAr);
            creatExcelNameList(workbook, excelLinkageModel.isChildren()?excelLinkageModel.getParentName():"List"+i,(i+1),strAr.length,excelLinkageModel.isChildren());
        }

        //设置隐藏页标志
        workbook.setSheetHidden(workbook.getSheetIndex(EXCEL_HIDE_SHEET_NAME), true);
    }

    private static void creatExcelNameList(Workbook workbook, String nameCode, int order, int size, boolean cascadeFlag) {
        Name name;
        name = workbook.createName();
        name.setNameName(nameCode);
        String formula = EXCEL_HIDE_SHEET_NAME + "!" + creatExcelNameList(order, size, cascadeFlag);
        System.out.println(nameCode + " ==  " + formula);
        name.setRefersToFormula(formula);
    }

    private static String creatExcelNameList(int order, int size, boolean cascadeFlag) {
        char start = 'A';
        if (cascadeFlag) {
            if (size <= 25) {
                char end = (char) (start + size - 1);
                return "$" + start + "$" + order + ":$" + end + "$" + order;
            } else {
                char endPrefix = 'A';
                char endSuffix = 'A';
                if ((size - 25) / 26 == 0 || size == 51) {//26-51之间，包括边界（仅两次字母表计算）
                    if ((size - 25) % 26 == 0) {//边界值
                        endSuffix = (char) ('A' + 25);
                    } else {
                        endSuffix = (char) ('A' + (size - 25) % 26 - 1);
                    }
                } else {//51以上
                    if ((size - 25) % 26 == 0) {
                        endSuffix = (char) ('A' + 25);
                        endPrefix = (char) (endPrefix + (size - 25) / 26 - 1);
                    } else {
                        endSuffix = (char) ('A' + (size - 25) % 26 - 1);
                        endPrefix = (char) (endPrefix + (size - 25) / 26);
                    }
                }
                return "$" + start + "$" + order + ":$" + endPrefix + endSuffix + "$" + order;
            }
        } else {
            if (size <= 26) {
                char end = (char) (start + size - 1);
                return "$" + start + "$" + order + ":$" + end + "$" + order;
            } else {
                char endPrefix = 'A';
                char endSuffix = 'A';
                if (size % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    if (size > 52 && size / 26 > 0) {
                        endPrefix = (char) (endPrefix + size / 26 - 2);
                    }
                } else {
                    endSuffix = (char) ('A' + size % 26 - 1);
                    if (size > 52 && size / 26 > 0) {
                        endPrefix = (char) (endPrefix + size / 26 - 1);
                    }
                }
                return "$" + start + "$" + order + ":$" + endPrefix + endSuffix + "$" + order;
            }
        }
    }

    private static void creatRow(Row currentRow, String[] textList) {
        if (textList != null && textList.length > 0) {
            int i = 0;
            for (String cellValue : textList) {
                Cell userNameLableCell = currentRow.createCell(i++);
                userNameLableCell.setCellValue(cellValue);
            }
        }
    }

    public static void setDataValidation(Workbook wb) {
        int sheetIndex = wb.getNumberOfSheets();
        if (sheetIndex > 0) {
            for (int i = 0; i < sheetIndex; i++) {
                Sheet sheet = wb.getSheetAt(i);
                if (!EXCEL_HIDE_SHEET_NAME.equals(sheet.getSheetName())) {
//省份选项添加验证数据
                    for (int a = 2; a < 5; a++) {
//性别添加验证数据
                        DataValidation data_validation_list = getDataValidationByFormula("List0", a, 1);
                        sheet.addValidationData(data_validation_list);
                        DataValidation data_validation_list2 = getDataValidationByFormula("List1", a,
                                2);
                        sheet.addValidationData(data_validation_list2);
//城市选项添加验证数据
                        DataValidation data_validation_list3 = getDataValidationByFormula("INDIRECT($B$" + a + ")", a,
                                3);
                        sheet.addValidationData(data_validation_list3);
                    }
                }
            }
        }
    }

    private static DataValidation getDataValidationByFormula(String formulaString, int naturalRowIndex,
                                                             int naturalColumnIndex) {
        System.out.println("formulaString  " + formulaString);
//加载下拉列表内容
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(formulaString);
//设置数据有效性加载在哪个单元格上。
//四个参数分别是：起始行、终止行、起始列、终止列
        int firstRow = naturalRowIndex - 1;
        int lastRow = naturalRowIndex - 1;
        int firstCol = naturalColumnIndex - 1;
        int lastCol = naturalColumnIndex - 1;
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
//数据有效性对象
        DataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        data_validation_list.setEmptyCellAllowed(false);
        if (data_validation_list instanceof XSSFDataValidation) {
            data_validation_list.setSuppressDropDownArrow(true);
            data_validation_list.setShowErrorBox(true);
        } else {
            data_validation_list.setSuppressDropDownArrow(false);
        }
//设置输入信息提示信息
        data_validation_list.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
//设置输入错误提示信息
        data_validation_list.createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
        return data_validation_list;
    }
}
