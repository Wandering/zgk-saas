package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.saas.common.*;
import cn.thinkjoy.saas.domain.Exam;
import cn.thinkjoy.saas.domain.ExamDetail;
import cn.thinkjoy.saas.service.IExamDetailService;
import cn.thinkjoy.saas.service.IExamService;
import cn.thinkjoy.zgk.common.StringUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.thinkjoy.saas.common.UploadUtil.saveExcelData;

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

    private static List<String> headerList = new ArrayList<>();

    static
    {
        headerList.add("examId");
        headerList.add("studentName");
        headerList.add("className");
        headerList.add("yuWenScore");
        headerList.add("shuXueScore");
        headerList.add("yingYuScore");
        headerList.add("wuLiScore");
        headerList.add("huaXueScore");
        headerList.add("shengWuScore");
        headerList.add("zhengZhiScore");
        headerList.add("diLiScore");
        headerList.add("liShiScore");
        headerList.add("commonScore");
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
        Integer currentRow = 1;
        for (int i = 1; i <= 8; i++)
        {
            int dataLength = 65;
            initData(wb, sheet, currentRow, dataLength, "三年" + i + "班");
            currentRow += dataLength;
        }
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

    private void initData(Workbook wb, Sheet sheet, Integer currentRow, Integer dataLength, String className)
    {
        CellStyle style = getCellStyle(wb, false);
        for (Integer i = currentRow; i <= currentRow + dataLength; i++)
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
        saveExcelData(exam, examService, headerList);
        return exam;
    }

    @RequestMapping("/listExam")
    @ResponseBody
    public List<Exam> listExam(@RequestParam(value = "grade", required = true) String grade)
    {
        return examService.findList("grade", grade, "createDate", SqlOrderEnum.DESC);
    }

    @RequestMapping("/getExamDetailById")
    @ResponseBody
    public ExamDetail getExamDetailById(@RequestParam(value = "id", required = false) String id)
    {
        return (ExamDetail)examDetailService.findOne("id", id);
    }

    @RequestMapping("/modifyExamDetail")
    @ResponseBody
    public boolean modifyExamDetail(ExamDetail examDetail)
    {
        boolean flag;
        try
        {
            examDetailService.update(examDetail);
            flag = true;
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }

    @RequestMapping("/listExamDetail")
    @ResponseBody
    public List<ExamDetail> listExamDetail(@RequestParam(value = "examId", required = false) String examId,
        @RequestParam(value = "id", required = false) String id,
        @RequestParam(value = "grade", required = false) String grade,
        @RequestParam(value = "offset", required = true) int offset,
        @RequestParam(value = "rows", required = true) int rows)
    {
        if (rows > 50)
        {
            rows = 50;
        }
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("groupOp", "and");
        if (!StringUtil.isNulOrBlank(examId))
        {
            ConditionsUtil.setCondition(condition, "examId", "=", examId);
        }
        if (!StringUtil.isNulOrBlank(id))
        {
            ConditionsUtil.setCondition(condition, "id", "=", id);
        }
        if (!StringUtil.isNulOrBlank(grade))
        {
            ConditionsUtil.setCondition(condition, "grade", "=", grade);
        }
        return examDetailService.queryPage(condition, offset, rows, "CAST(gradeRank as SIGNED)", SqlOrderEnum.ASC);
    }

    @RequestMapping("/getOverLineNumberByDate")
    @ResponseBody
    public List<Map<String, Object>> getOverLineNumberByDate(
        @RequestParam(value = "grade", required = false) String grade,
        @RequestParam(value = "lineScore", required = false) String lineScore)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grade", grade);
        paramMap.put("lineScore", lineScore);
        return examDetailService.getOverLineNumberByDate(paramMap);
    }

    @RequestMapping("/getEnrollingNumberInfo")
    @ResponseBody
    public Map<String, Object> getEnrollingNumberInfo(@RequestParam(value = "tnId", required = false) String tnId)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        return examDetailService.getEnrollingNumberInfo(paramMap);
    }

    @RequestMapping("/getOverLineNumberDetail")
    @ResponseBody
    public List<Map<String, Object>> getOverLineNumberDetail(@RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "orderBy", required = true) String orderBy)
    {
        String examId = examDetailService.getLastExamIdByGrade(grade);
        if(StringUtils.isEmpty(examId))
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> numberMap = getNumberMap(tnId);
        int batchOneNumber = Integer.parseInt(numberMap.get("batchOne") + "");
        int batchTwoNumber = Integer.parseInt(numberMap.get("batchTwo") + "");
        int batchThrNumber = Integer.parseInt(numberMap.get("batchThr") + "");
        Map<String, Map<String, List<ExamDetail>>> classInfoMap = Maps.newHashMap();
        List<ExamDetail> detailList = examDetailService.findList("examId", examId);
        for (ExamDetail detail : detailList)
        {
            String className = detail.getClassName();
            Map<String, List<ExamDetail>> mp = classInfoMap.get(className);
            if (null == mp)
            {
                mp = new HashMap<>();
                mp.put("batchOne", new ArrayList<>());
                mp.put("batchTwo", new ArrayList<>());
                mp.put("batchThr", new ArrayList<>());
                classInfoMap.put(className, mp);
            }
            int gradeRank = Integer.parseInt(detail.getGradeRank());
            if (gradeRank <= batchOneNumber)
            {
                mp.get("batchOne").add(detail);
            }
            if (gradeRank <= batchTwoNumber)
            {
                mp.get("batchTwo").add(detail);
            }
            if (gradeRank <= batchThrNumber)
            {
                mp.get("batchThr").add(detail);
            }
        }
        for (Map.Entry<String, Map<String, List<ExamDetail>>> entry : classInfoMap.entrySet())
        {
            String className = entry.getKey();
            Map<String, List<ExamDetail>> examDetailListMap = entry.getValue();
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("className", className);
            resultMap.put("batchAll", examDetailListMap.get("batchThr").size());
            resultMap.put("batchOne", examDetailListMap.get("batchOne").size());
            resultMap.put("batchTwo",
                examDetailListMap.get("batchTwo").size() - examDetailListMap.get("batchOne").size());
            resultMap.put("batchThr",
                examDetailListMap.get("batchThr").size() - examDetailListMap.get("batchTwo").size());
            resultList.add(resultMap);
        }
        Collections.sort(resultList, new Comparator<Map<String, Object>>()
        {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2)
            {
                return Integer.parseInt(o2.get(orderBy) + "") -
                    Integer.parseInt(o1.get(orderBy) + "");
            }
        });
        return resultList;
    }

    private Map<String, Object> getNumberMap(@RequestParam(value = "tnId", required = true) String tnId)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        Map<String, Object> numberMap = examDetailService.getEnrollingNumberInfo(paramMap);
        if (null == numberMap || numberMap.isEmpty())
        {
            throw new BizException("1001021", "为了方便使用，请设置升学率！");
        }
        return numberMap;
    }

    @RequestMapping("/getMostAttentionNumber")
    @ResponseBody
    public List<Map<String, Object>> getMostAttentionNumber(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "examId", required = true) String examId)
    {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> numberMap = getNumberMap(tnId);
        int batchOneNumber = Integer.parseInt(numberMap.get("batchOne") + "");
        int batchTwoNumber = Integer.parseInt(numberMap.get("batchTwo") + "");
        int batchThrNumber = Integer.parseInt(numberMap.get("batchThr") + "");
        List<ExamDetail> detailList = examDetailService.findList("examId", examId);
        int batchOneLowScore = 0;
        int batchTwoLowScore = 0;
        int batchThrLowScore = 0;
        for (ExamDetail detail : detailList)
        {
            int gradeRank = Integer.parseInt(detail.getGradeRank());
            if (gradeRank == batchTwoNumber)
            {
                batchTwoLowScore = Integer.parseInt(detail.getTotleScore()) - 20;
            }
            if (gradeRank == batchOneNumber)
            {
                batchOneLowScore = Integer.parseInt(detail.getTotleScore()) - 20;
            }
            if (gradeRank == batchThrNumber)
            {
                batchThrLowScore = Integer.parseInt(detail.getTotleScore()) - 20;
            }
        }
        Map<String, List<ExamDetail>> batchMap = new LinkedHashMap<>();
        batchMap.put("batchOne", new ArrayList<>());
        batchMap.put("batchTwo", new ArrayList<>());
        batchMap.put("batchThr", new ArrayList<>());
        Map<String, Object> selectCourseMap = new HashMap<>();
        for (ExamDetail detail : detailList)
        {
            fixSelectCourse(detail);
            int totalScore = Integer.parseInt(detail.getTotleScore());
            setBatchMap(batchOneLowScore, batchMap, selectCourseMap, detail, totalScore, "batchOne");
            setBatchMap(batchTwoLowScore, batchMap, selectCourseMap, detail, totalScore, "batchTwo");
            setBatchMap(batchThrLowScore, batchMap, selectCourseMap, detail, totalScore, "batchThr");
        }
        for (Map.Entry<String, List<ExamDetail>> entry : batchMap.entrySet())
        {
            String batchName = entry.getKey();
            List<ExamDetail> batchDetailList = entry.getValue();
            Map<String, List<ExamDetail>> tempMap = new HashMap<>();
            for (ExamDetail detail : batchDetailList)
            {
                String selectCourse = detail.getSelectCourses();
                List<ExamDetail> selectCourseList = tempMap.get(selectCourse);
                if (null == selectCourseList)
                {
                    selectCourseList = new ArrayList<>();
                    tempMap.put(selectCourse, selectCourseList);
                }
                selectCourseList.add(detail);
            }
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("batchName", batchName);
            resultMap.put("batchTotal", batchDetailList.size());
            resultMap.putAll(selectCourseMap);
            for (Map.Entry<String, List<ExamDetail>> tempEntry : tempMap.entrySet())
            {
                resultMap.put(tempEntry.getKey(), tempEntry.getValue().size());
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    private void fixSelectCourse(ExamDetail detail)
    {
        String selectCourses = detail.getSelectCourses();
        selectCourses = selectCourses.replaceAll("@", "").replace("化学", "化").replace("物理", "物").replace("生物", "生")
            .replace("历史", "史").replace("地理", "地").replace("政治", "政").replace("通用技术", "通");
        detail.setSelectCourses(selectCourses);
    }

    private void setBatchMap(int batchLowScore, Map<String, List<ExamDetail>> batchMap,
        Map<String, Object> selectCourseMap, ExamDetail detail, int totalScore, String batchName)
    {
        if (totalScore <= batchLowScore)
        {
            batchMap.get(batchName).add(detail);
            selectCourseMap.put(detail.getSelectCourses(), 0);
        }
    }

    @RequestMapping("/getMostAttentionNumberDetail")
    @ResponseBody
    public Map<String, Object> getMostAttentionNumberDetail(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "examId", required = true) String examId,
        @RequestParam(value = "batchName", required = true) String batchName)
    {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> numberMap = getNumberMap(tnId);
        int batchNumber = Integer.parseInt(numberMap.get(batchName) + "");
        List<ExamDetail> detailList = examDetailService.findList("examId", examId);
        int batchLowScore = 0;
        for (ExamDetail detail : detailList)
        {
            int gradeRank = Integer.parseInt(detail.getGradeRank());

            if (gradeRank == batchNumber)
            {
                batchLowScore = Integer.parseInt(detail.getTotleScore()) - 20;
            }
        }
        List<ExamDetail> batchDetailList = new ArrayList<>();
        for (ExamDetail detail : detailList)
        {
            fixSelectCourse(detail);
            int totalScore = Integer.parseInt(detail.getTotleScore());
            if (totalScore <= batchLowScore)
            {
                batchDetailList.add(detail);
            }
        }
        return resultMap;
    }

}
