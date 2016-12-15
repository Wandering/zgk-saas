package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.saas.common.*;
import cn.thinkjoy.saas.domain.*;
import cn.thinkjoy.saas.service.IExamDetailService;
import cn.thinkjoy.saas.service.IExamPropertiesService;
import cn.thinkjoy.saas.service.IExamService;
import cn.thinkjoy.saas.service.IExamStuWeakCourseService;
import cn.thinkjoy.saas.service.bussiness.EXIGradeService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.sun.tools.corba.se.idl.InterfaceGen;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
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
    IExamService examService;

    @Autowired
    IExamDetailService examDetailService;

    @Autowired
    IExamStuWeakCourseService examStuWeakCourseService;

    @Autowired
    IExamPropertiesService examPropertiesService;

    @Autowired
    EXIGradeService exiGradeService;

    private Set<Integer> advancedScoreSet;

    private int maxGradeRank = 0;

    private int maxAdvancedScore = 0;

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
        headerList.add("classRank");
        headerList.add("gradeRank");
    }

    @RequestMapping(value = "/downloadModel", method = RequestMethod.GET)
    @ResponseBody
    public void downloadModel(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "mock", required = false, defaultValue = "false") Boolean mock,
        HttpServletResponse response)
        throws IOException
    {
        List<String> classNames = getClassesNameByGrade(tnId, grade);
        if((null == classNames) || (classNames.size() == 0))
        {
            throw new BizException("1100221", "无班级信息，请设置班级");
        }
        Workbook wb = createWorkBook(classNames, mock);
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

    private Workbook createWorkBook(List<String> classNames, Boolean mock)
    {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("班级成绩");
        CellRangeAddress nameRange = new CellRangeAddress(0, 1, 0, 0);
        CellRangeAddress classRange = new CellRangeAddress(0, 1, 1, 1);
        CellRangeAddress mainCourseRange = new CellRangeAddress(0, 0, 2, 4);
        CellRangeAddress selectCourseRange = new CellRangeAddress(0, 0, 5, 11);
        CellRangeAddress gradeRankRange = new CellRangeAddress(0, 1, 12, 12);
        CellRangeAddress classRankRange = new CellRangeAddress(0, 1, 13, 13);
        for (int i = 0; i < 14; i++)
        {
            sheet.setColumnWidth((short)i, (short)(35.7 * 80));
        }
        initHeader(wb, sheet);
        sheet.addMergedRegion(nameRange);
        sheet.addMergedRegion(classRange);
        sheet.addMergedRegion(mainCourseRange);
        sheet.addMergedRegion(selectCourseRange);
        sheet.addMergedRegion(gradeRankRange);
        sheet.addMergedRegion(classRankRange);
        setRegionBorder(1, nameRange, sheet, wb);
        setRegionBorder(1, classRange, sheet, wb);
        setRegionBorder(1, mainCourseRange, sheet, wb);
        setRegionBorder(1, selectCourseRange, sheet, wb);
        setRegionBorder(1, gradeRankRange, sheet, wb);
        setRegionBorder(1, classRankRange, sheet, wb);
        CellRangeAddressList regions = new CellRangeAddressList(2,
            5000, 1, 1);
        DVConstraint constraint = DVConstraint
            .createExplicitListConstraint(classNames.toArray(new String[classNames.size()]));
        // 数据有效性对象
        HSSFDataValidation validation = new HSSFDataValidation(
            regions, constraint);
        sheet.addValidationData(validation);
        if (mock)
        {
            fillData(wb, sheet, classNames);
        }
        return wb;
    }

    private void fillData(Workbook wb, Sheet sheet, List<String> classNames)
    {
        Integer currentRow = 1;
        for (int i = 0; i < classNames.size(); i++)
        {
            int dataLength = 65;
            initData(wb, sheet, currentRow, dataLength, classNames.get(i));
            currentRow += dataLength;
        }
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
        columnOneMap.put(12, "年级排名");
        columnOneMap.put(13, "班级排名");
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
            Set<Integer> randomIndexs = RandomValue.getIndexByI(i % 10);
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

    @RequestMapping("/checkExamName")
    @ResponseBody
    public Boolean checkExamName(Exam exam)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("examName", exam.getExamName());
        paramMap.put("grade", exam.getGrade());
        Exam existExam = (Exam)examService.queryOne(paramMap);
        return existExam == null ? false : true;
    }

    @RequestMapping("/addExam")
    @ResponseBody
    public Exam addExam(Exam exam)
    {
        Exam existExam = getExsitExam(exam);
        if (null != existExam)
        {
            exam.setId(existExam.getId());
            exam.setCreateDate(existExam.getCreateDate());
        }
        exam.setOriginFileName(RedisUtil.getInstance().get(exam.getUploadFilePath())+"");
        exam.setCreateDate(TimeUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"));
        examService.add(exam);
        saveExcelData(exam, examService, headerList);
        return exam;
    }

    private Exam getExsitExam(Exam exam)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("examName", exam.getExamTime());
        paramMap.put("grade", exam.getGrade());
        return (Exam)examService.queryOne(paramMap);
    }

    @RequestMapping("/listExam")
    @ResponseBody
    public List<Exam> listExam(@RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        return examService.queryList(paramMap, "createDate", "DESC");
    }

    @RequestMapping("/deleteExam")
    @ResponseBody
    public Boolean deleteExam(@RequestParam(value = "examId", required = true) String examId)
    {
        Boolean result;
        try
        {
            examService.delete(examId);
            examDetailService.deleteByProperty("examId", examId);
            result = true;
        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
    }

    @RequestMapping("/getExamById")
    @ResponseBody
    public Exam getExamById(@RequestParam(value = "examId", required = true) String examId)
    {
        return (Exam)examService.fetch(examId);
    }

    @RequestMapping("/modifyExam")
    @ResponseBody
    public Boolean modifyExam(Exam exam)
    {
        Boolean result;
        Exam originExam = (Exam)examService.fetch(exam.getId());
        if (null != originExam)
        {
            originExam.setExamName(exam.getExamName());
            originExam.setExamTime(exam.getExamTime());
            originExam.setGrade(exam.getGrade());
            examService.update(originExam);
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }

    @RequestMapping("/deleteExamDetail")
    @ResponseBody
    public Boolean deleteExamDetail(@RequestParam(value = "examDetailId", required = false) String examDetailId)
    {

        Boolean result;
        try
        {
            examDetailService.delete(examDetailId);
            result = true;
        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
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
    public Map<String, Object> listExamDetail(@RequestParam(value = "examId", required = true) String examId,
        @RequestParam(value = "id", required = false) String id,
        @RequestParam(value = "grade", required = false) String grade,
        @RequestParam(value = "offset", required = true) int offset,
        @RequestParam(value = "rows", required = true) int rows)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (rows > 50)
        {
            rows = 50;
        }
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("groupOp", "and");
        if (StringUtils.isEmpty(examId))
        {
            throw new BizException("1110001", "examId不能为空！");
        }
        ConditionsUtil.setCondition(condition, "examId", "=", examId);
        int count = examDetailService.count(condition);
        if (StringUtils.isNotEmpty(id))
        {
            ConditionsUtil.setCondition(condition, "detail.id", "=", id);
        }
        if (StringUtils.isNotEmpty(grade))
        {
            ConditionsUtil.setCondition(condition, "grade", "=", grade);
        }
        resultMap.put("count", count);
        List<ExamDetail> resultList =
            examDetailService.queryPage(condition, offset, rows, "CAST(gradeRank as SIGNED)", SqlOrderEnum.ASC);
        resultMap.put("list", resultList);
        return resultMap;
    }

    @RequestMapping("/getOverLineNumberByDate")
    @ResponseBody
    public List<Map<String, Object>> getOverLineNumberByDate(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = false) String className,
        @RequestParam(value = "lineScore", required = false) String lineScore)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("lineScore", lineScore);
        if (StringUtils.isNotEmpty(className))
        {
            paramMap.put("className", className);
        }
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
    public List<Map<String, Object>> getOverLineNumberDetail(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "orderBy", required = true) final String orderBy)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("limitNumber", 1);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> numberMap = getNumberMap(tnId);
        int batchOneNumber = Integer.parseInt(numberMap.get("batchOne") + "");
        int batchTwoNumber = Integer.parseInt(numberMap.get("batchTwo") + "");
        int batchThrNumber = Integer.parseInt(numberMap.get("batchThr") + "");
        Map<String, Map<String, List<ExamDetail>>> classInfoMap = Maps.newHashMap();
        List<ExamDetail> detailList = examDetailService.findList("examId", examIds.get(0));
        for (ExamDetail detail : detailList)
        {
            String className = detail.getClassName();
            Map<String, List<ExamDetail>> mp = classInfoMap.get(className);
            if (null == mp)
            {
                mp = new HashMap<>();
                mp.put("batchOne", new ArrayList<ExamDetail>());
                mp.put("batchTwo", new ArrayList<ExamDetail>());
                mp.put("batchThr", new ArrayList<ExamDetail>());
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
        Map<String, String> classBossMap = getClassBossMap(tnId, grade);
        for (Map.Entry<String, Map<String, List<ExamDetail>>> entry : classInfoMap.entrySet())
        {
            String className = entry.getKey();
            Map<String, List<ExamDetail>> examDetailListMap = entry.getValue();
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("className", className);
            if(!classBossMap.isEmpty())
            {
                String classBoss = classBossMap.get(className);
                resultMap.put("counselor", classBoss);
            }
            resultMap.put("batchAll", examDetailListMap.get("batchTwo").size());
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

    private Map<String, String> getClassBossMap(String tnId, String grade)
    {
        boolean check  = checkClassBoss(tnId);
        Map<String, String> classBossMap = new HashMap<>();
        if(check)
        {
            String tableName = ParamsUtils.combinationTableName("class", Integer.parseInt(tnId));
            Map<String, Object> map = new HashMap<>();
            map.put("tableName",tableName);
            map.put("grade",grade);
            List<Map<String,Object>> list = examDetailService.getClassBossList(map);
            for (Map<String,Object> mp : list)
            {
                String className = mp.get("class_name") + "";
                String classBoss =mp.get("class_boss") + "";
                if(StringUtils.isNotEmpty(classBoss))
                {
                    classBossMap.put(className, classBoss);
                }
            }
        }
        return classBossMap;
    }

    @RequestMapping("/getSchoolBossForGrade")
    @ResponseBody
    private List<String> schoolBossForGrade(String tnId, String grade)
    {
        List<String> list = new ArrayList<>();
        Map<String, String> classBossMap = getClassBossMap(tnId, grade);
        if(!classBossMap.isEmpty())
        {
            list.addAll(classBossMap.values());
        }
        return list;
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
        @RequestParam(value = "grade", required = true) String grade)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grade", grade);
        paramMap.put("tnId", tnId);
        paramMap.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        String lastExamId = examIds.get(0);
        Map<String, Object> numberMap = getNumberMap(tnId);
        int batchOneNumber = Integer.parseInt(numberMap.get("batchOne") + "");
        int batchTwoNumber = Integer.parseInt(numberMap.get("batchTwo") + "");
        int batchThrNumber = Integer.parseInt(numberMap.get("batchThr") + "");
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Set<ExamDetail>> batchMap = new LinkedHashMap<>();
        batchMap.put("batchOne", new TreeSet<ExamDetail>());
        batchMap.put("batchTwo", new TreeSet<ExamDetail>());
        batchMap.put("batchThr", new TreeSet<ExamDetail>());
        Map<String, Object> selectCourseMap = new HashMap<>();
        Map<String, ExamDetail> lastExamDetailMap = new LinkedHashMap<>();
        Map<Long, Set<ExamScoreRatio>> examScoreRatioMap = new LinkedHashMap<>();
        for (int i = 1; i <= examIds.size(); i++)
        {
            String examId = examIds.get(i - 1);
            List<ExamDetail> detailList = examDetailService.findList("examId", examId);
            float batchOneLowScore = 0;
            float batchTwoLowScore = 0;
            float batchThrLowScore = 0;
            for (ExamDetail detail : detailList)
            {
                if (i == 1)
                {
                    lastExamDetailMap.put(detail.getClassName() + "@" + detail.getStudentName(), detail);
                    examScoreRatioMap.put(detail.getId(), new TreeSet<ExamScoreRatio>());
                }
                int gradeRank = Integer.parseInt(detail.getGradeRank());
                if (gradeRank == batchTwoNumber)
                {
                    batchTwoLowScore = Float.parseFloat(detail.getTotleScore());
                }
                if (gradeRank == batchOneNumber)
                {
                    batchOneLowScore = Float.parseFloat(detail.getTotleScore());
                }
                if (gradeRank == batchThrNumber)
                {
                    batchThrLowScore = Float.parseFloat(detail.getTotleScore());
                }
            }
            Map<String, String> params = new HashMap<>();
            params.put("examId", examId);
            Map<String, Object> avgScoreMap = examDetailService.getAvgScoresByExamId(params);
            for (ExamDetail detail : detailList)
            {
                fixSelectCourse(detail, lastExamId);
                float totalScore = Float.parseFloat(detail.getTotleScore());
                ExamDetail lastDetail = lastExamDetailMap.get(detail.getClassName() + "@" + detail.getStudentName());
                if (isBigger(batchOneLowScore, totalScore) && !isBigger((batchOneLowScore - 20),totalScore))
                {
                    batchMap.get("batchOne").add(lastDetail);
                    selectCourseMap.put(detail.getSelectCourses(), 0);
                }
                if (isBigger(batchTwoLowScore, totalScore) && !isBigger((batchTwoLowScore - 20),totalScore))
                {
                    batchMap.get("batchTwo").add(lastDetail);
                    selectCourseMap.put(detail.getSelectCourses(), 0);
                }
                if (isBigger(batchThrLowScore, totalScore) && !isBigger((batchThrLowScore - 20),totalScore))
                {
                    batchMap.get("batchThr").add(lastDetail);
                    selectCourseMap.put(detail.getSelectCourses(), 0);
                }
                setRatioMap(examScoreRatioMap, avgScoreMap, detail, lastDetail);
            }
        }
        examStuWeakCourseService.deleteByProperty("examId", lastExamId);
        for (Map.Entry<String, Set<ExamDetail>> entry : batchMap.entrySet())
        {
            String batchName = entry.getKey();
            Set<ExamDetail> batchDetailList = entry.getValue();
            Map<String, List<ExamDetail>> tempMap = new HashMap<>();
            List<Map<String, String>> dataList = new ArrayList<>();
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
                addDataList(lastExamId, examScoreRatioMap, dataList, batchName, detail);
            }
            if (dataList.size() > 0)
            {
                List<String> columnList = new ArrayList<>();
                columnList.addAll(dataList.get(0).keySet());
                examService.batchInsertData("saas_exam_stu_weak_course", columnList, dataList);
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

    private boolean isBigger(float f1, float f2)
    {
        BigDecimal ff1 = new BigDecimal(f1);
        BigDecimal ff2 = new BigDecimal(f2);
        return ff1.subtract(ff2).floatValue() > 0;
    }

    private void addDataList(String lastExamId, Map<Long, Set<ExamScoreRatio>> examScoreRatioMap,
        List<Map<String, String>> dataList, String batchName, ExamDetail detail)
    {
        Map<String, String> dataMap = new LinkedHashMap<>();
        dataMap.put("examId", lastExamId);
        dataMap.put("batchName", batchName);
        dataMap.put("examDetailId", detail.getId().toString());
        dataMap.put("gradeRank", detail.getGradeRank());
        dataMap.put("className", detail.getClassName());
        dataMap.put("studentName", detail.getStudentName());
        Set<ExamScoreRatio> ratioSet = examScoreRatioMap.get(detail.getId());
        if (null != ratioSet && ratioSet.size() >= 2)
        {
            List<ExamScoreRatio> ratioList = new ArrayList<>();
            ratioList.addAll(ratioSet);
            Map<String, Float> map = new HashMap<>();
            for (int i = 0; i < ratioList.size(); i++)
            {
                String weakTwoCourseName = ratioList.get(i).getCourseName();
                if(null == map.get(weakTwoCourseName))
                {
                    map.put(weakTwoCourseName, ratioList.get(i).getRatio());
                }else
                {
                    float f = map.get(weakTwoCourseName);
                    float v = new BigDecimal(f).add(new BigDecimal(ratioList.get(i).getRatio())).floatValue();
                    map.put(weakTwoCourseName, v);
                }
            }
            Set<ExamScoreRatio> set = new TreeSet<>();
            int in =1;
            for (Map.Entry<String, Float> en: map.entrySet())
            {
                ExamScoreRatio r = new ExamScoreRatio();
                r.setCourseName(en.getKey());
                r.setRatio(en.getValue());
                r.setId(Long.parseLong(in++ + ""));
                set.add(r);
            }
            List<ExamScoreRatio> ratioList2 = new ArrayList<>();
            ratioList2.addAll(set);
            if(ratioList2.size() >= 2)
            {
                String weakOneCourseName = ratioList2.get(0).getCourseName();
                dataMap.put("weakCourseOne", weakOneCourseName);
                String weakTwoCourseName = ratioList2.get(1).getCourseName();
                dataMap.put("weakCourseTwo", weakTwoCourseName);
            }
            dataMap.put("weakCourseDetails", ratioSet + "@@" +set.toString());
        }
        dataList.add(dataMap);
    }

    private void setRatioMap(Map<Long, Set<ExamScoreRatio>> examScoreRatioMap, Map<String, Object> avgScoreMap,
        ExamDetail detail, ExamDetail lastDetail)
    {
        for (Map.Entry<String, Object> entry : avgScoreMap.entrySet())
        {
            String courseName = entry.getKey();
            BigDecimal avgScore = new BigDecimal(entry.getValue() + "");
            ExamScoreRatio ratio = new ExamScoreRatio();
            ratio.setCourseName(courseName);
            String score = "";
            Float ratioValue;
            try
            {
                switch (courseName)
                {
                    case "语文":
                        score = detail.getYuWenScore();
                        break;
                    case "数学":
                        score = detail.getShuXueScore();
                        break;
                    case "英语":
                        score = detail.getYingYuScore();
                        break;
                    case "物理":
                        score = detail.getWuLiScore();
                        break;
                    case "化学":
                        score = detail.getHuaXueScore();
                        break;
                    case "生物":
                        score = detail.getShengWuScore();
                        break;
                    case "政治":
                        score = detail.getZhengZhiScore();
                        break;
                    case "地理":
                        score = detail.getDiLiScore();
                        break;
                    case "历史":
                        score = detail.getLiShiScore();
                        break;
                    case "通用技术":
                        score = detail.getCommonScore();
                        break;
                }
                ratioValue = new BigDecimal(score + "").divide(avgScore, 2, BigDecimal.ROUND_HALF_DOWN)
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                    .floatValue();
            }
            catch (Exception e)
            {
                continue;
            }
            ratio.setRatio(ratioValue);
            try
            {
                examScoreRatioMap.get(lastDetail.getId()).add(ratio);
            }
            catch (Exception e)
            {
            }
        }
    }

    private void fixSelectCourse(ExamDetail detail, String lastExamId)
    {
        detail.setExamId(Long.parseLong(lastExamId));
        String selectCourses = detail.getSelectCourses();
        selectCourses = selectCourses.replaceAll("@", "").replace("化学", "化").replace("物理", "物").replace("生物", "生")
            .replace("历史", "史").replace("地理", "地").replace("政治", "政").replace("通用技术", "通");
        detail.setSelectCourses(selectCourses);
    }

    @RequestMapping("/getMostAttentionNumberChart")
    @ResponseBody
    public Map<String, Object> getMostAttentionNumberChart(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "batchName", required = true) String batchName)
    {
        String lastExamId = getLastExamId(grade, tnId);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("examId", lastExamId);
        paramMap.put("batchName", batchName);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("classChartData", examDetailService.getMostAttentionNumberChart(paramMap));
        resultMap.put("courseChartData", examDetailService.getMostAttentionCourseChart(paramMap));
        return resultMap;
    }

    @RequestMapping("/getMostAttentionPage")
    @ResponseBody
    public Map<String, Object> getMostAttentionPage(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "batchName", required = true) String batchName,
        @RequestParam(value = "className", required = false) String className,
        @RequestParam(value = "courseName", required = false) String courseName,
        @RequestParam(value = "offset", required = true) int offset,
        @RequestParam(value = "rows", required = true) int rows)
    {
        getMostAttentionNumber(tnId, grade);
        String lastExamId = getLastExamId(grade, tnId);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("examId", lastExamId);
        if(StringUtils.isNotEmpty(batchName) && (batchName.equals("batchOne")||batchName.equals("batchTwo")||batchName.equals("batchThr")))
        {
            paramMap.put("batchName", batchName);
        }
        if (StringUtils.isNotEmpty(className))
        {
            paramMap.put("className", className);
        }
        if (StringUtils.isNotEmpty(courseName))
        {
            paramMap.put("courseName", courseName);
        }
        List<Map<String, Object>> allList = examDetailService.getMostAttentionPage(paramMap);
        paramMap.put("offset", offset + "");
        paramMap.put("rows", rows + "");
        paramMap.put("orderBy", "gradeRank");
        List<Map<String, Object>> pageList = examDetailService.getMostAttentionPage(paramMap);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", allList.size());
        resultMap.put("list", pageList);
        return resultMap;
    }

    private String getLastExamId(String grade, String tnId)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grade", grade);
        paramMap.put("tnId", tnId);
        paramMap.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        return examIds.get(0);
    }

    @RequestMapping("/getMostAdvancedNumbers")
    @ResponseBody
    public List<Map<String, Object>> getMostAdvancedNumbers(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "stepStart", required = true) Integer stepStart,
        @RequestParam(value = "stepEnd", required = true) Integer stepEnd,
        @RequestParam(value = "counselor", required = false) String counselor)
    {
        if(null == stepStart)
            stepStart = 1;
        if(null == stepEnd)
            stepEnd = Integer.MAX_VALUE;
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grade", grade);
        paramMap.put("tnId", tnId);
        paramMap.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        if (examIds.size() == 1)
        {
            throw new BizException("1100012", "该年级只有一次成绩录入！！");
        }
        Map<String, List<Integer>> examScoreRatioMap = new LinkedHashMap<>();
        setScoreList(examIds, examScoreRatioMap);
        advancedScoreSet = new TreeSet<>();
        Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
        Map<String, String> bossMap = getClassBossMap(tnId, grade);
        for (Map.Entry<String, List<Integer>> entry : examScoreRatioMap.entrySet())
        {
            String examDetailInfo = entry.getKey();
            List<Integer> scoreList = entry.getValue();
            String className = examDetailInfo.split("@")[0];
            String studentName = examDetailInfo.split("@")[1];
            int advancedScore = 0;
            if (scoreList.size() == 0)
            {
                continue;
            }
            if (scoreList.size() == 1)
            {
                advancedScore = scoreList.get(0);
            }
            if (scoreList.size() == 2)
            {
                advancedScore = new BigDecimal(scoreList.get(0)).subtract(new BigDecimal(scoreList.get(1))).intValue();
            }
            if (scoreList.size() == 3)
            {
                advancedScore = new BigDecimal(scoreList.get(0)).subtract(new BigDecimal(scoreList.get(2))).
                    divide(new BigDecimal(2), 0, RoundingMode.HALF_DOWN).intValue();
            }
            if (advancedScore >= stepStart && advancedScore <= stepEnd)
            {
                List<Map<String, Object>> dataList = resultMap.get(className);
                if (null == dataList)
                {
                    dataList = new ArrayList<>();
                    resultMap.put(className, dataList);
                }
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("className", className);
                params.put("studentName", studentName);
                params.put("advancedScore", advancedScore);
                params.put("historyScores", scoreList.toString());
                dataList.add(params);
                advancedScoreSet.add(advancedScore);
            }

        }
        for (Map.Entry<String, List<Map<String, Object>>> en : resultMap.entrySet())
        {
            String className = en.getKey();
            int advancedNumber = en.getValue().size();
            Map<String, Object> map = new HashMap<>();
            map.put("className", className);
            if(!bossMap.isEmpty())
            {
                String bossName = bossMap.get(className);
                map.put("counselor", bossName);
                if(StringUtils.isNotEmpty(counselor)  && !counselor.equals(bossName))
                    continue;
            }
            map.put("advancedNumber", advancedNumber);
            resultList.add(map);
        }
        Collections.sort(resultList, new Comparator<Map<String, Object>>()
        {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2)
            {
                return Integer.parseInt(o2.get("advancedNumber") + "")
                    - Integer.parseInt(o1.get("advancedNumber") + "");
            }
        });
        return resultList;
    }

    @RequestMapping("/getStepList")
    @ResponseBody
    public List<Map<String, Integer>> getStepList(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "stepStart", required = true) Integer stepStart,
        @RequestParam(value = "stepLength", required = true) Integer stepLength)
    {
        getMostAdvancedNumbers(tnId, grade, stepStart, Integer.MAX_VALUE, null);
        List<Map<String, Integer>> stepList = new ArrayList<>();
        if (advancedScoreSet.size() > 0)
        {
            int maxStep = Collections.max(advancedScoreSet);
            addStepList(stepList, maxStep, stepStart, stepLength);
        }
        return stepList;
    }

    @RequestMapping("/getClassesNameByGrade")
    @ResponseBody
    public List<String> getClassesNameByGrade(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade)
    {
        String tableName = ParamsUtils.combinationTableName("class", Integer.parseInt(tnId));
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tableName", tableName);
        paramMap.put("grade", grade);
        List<String> classNames;
        try
        {
            classNames = examDetailService.getClassesNameByGrade(paramMap);
        }
        catch (Exception e)
        {
            throw new BizException("1100221", "班级信息未设置或设置不正确,必须包含班级名称和年级！");
        }
        return classNames;
    }

    @RequestMapping("/getAvgScoresForClass")
    @ResponseBody
    public List<Map<String, Object>> getAvgScoresForClass(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        List<Map<String, Object>> resultList = examDetailService.getAvgScoresForClass(paramMap);
        if (null == resultList)
        {
            resultList = new ArrayList<>();
        }
        sortByExamTime(resultList);
        Map<String, List<Map<String, Object>>> map = new LinkedHashMap<>();
        if(resultList.size() > 0)
        {
            for (Map<String, Object> mp : resultList)
            {
                String examTime = mp.get("examTime") + "";
                List<Map<String, Object>> list = map.get(examTime);
                if (null == list)
                {
                    list = new ArrayList<>();
                    map.put(examTime, list);
                }
                list.add(mp);
            }
        }
        List<Map<String, Object>> rList = new ArrayList<>();
        setRList(className, map, rList);
        return rList;
    }

    private void setRList(String className, Map<String, List<Map<String, Object>>> map, List<Map<String, Object>> rList)
    {
        for (Map.Entry<String, List<Map<String, Object>>> en: map.entrySet())
        {
            String examTime = en.getKey();
            List<Map<String, Object>> list = en.getValue();
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("examTime" , examTime);
            m.put("className" , className);
            m.put("语文" , sortList(list, "语文", className));
            m.put("数学" , sortList(list, "数学", className));
            m.put("英语" , sortList(list, "英语", className));
            m.put("物理" , sortList(list, "物理", className));
            m.put("化学" , sortList(list, "化学", className));
            m.put("生物" , sortList(list, "生物", className));
            m.put("政治" , sortList(list, "政治", className));
            m.put("地理" , sortList(list, "地理", className));
            m.put("历史" , sortList(list, "历史", className));
            m.put("通用技术" , sortList(list, "通用技术", className));
            m.put("总分" , sortList(list, "总分", className));
            rList.add(m);
        }
    }

    private void sortByExamTime(List<Map<String, Object>> resultList)
    {
        Collections.sort(resultList, new Comparator<Map<String, Object>>()
        {
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            Calendar c1=Calendar.getInstance();
            Calendar c2=Calendar.getInstance();

            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2)
            {
                String t1 = o1.get("examTime") + "";
                String t2 = o2.get("examTime") + "";
                try
                {
                    c1.setTime(df.parse(t1));
                    c2.setTime(df.parse(t2));
                }
                catch (ParseException e)
                {
                    return -1;
                }

                return c1.compareTo(c2);
            }
        });
    }

    private int sortList(List<Map<String, Object>> list, final String orderBy, String className)
    {
        Collections.sort(list, new Comparator<Map<String, Object>>()
        {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2)
            {
                return Float.parseFloat(o2.get(orderBy)+"") >= Float.parseFloat(o1.get(orderBy)+"") ? 1 : -1;
            }
        });
        int rank = 0;
        for (int i = 0; i < list.size() ; i++)
        {
            if(className.equals(list.get(i).get("className")))
            {
                rank = i + 1;
                break;
            }
        }
        return rank;
    }

    @RequestMapping("/getStuNumberScoreChangeForClass")
    @ResponseBody
    public List<Map<String, Object>> getStuNumberScoreChangeForClass(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("grade", grade);
        param.put("tnId", tnId);
        param.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(param);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        if (examIds.size() == 1)
        {
            throw new BizException("1100012", "该年级只有一次成绩录入！！");
        }
        List<ExamDetail> detailListOne = examDetailService.findList("examId", examIds.get(0));
        List<ExamDetail> detailListTwo = examDetailService.findList("examId", examIds.get(1));
        Map<String, List<ExamDetail>> detailMap = new HashMap<>();
        int lastGradeRank = 0;
        for (ExamDetail detail : detailListOne)
        {
            if (className.equals(detail.getClassName()))
            {
                List<ExamDetail> detailList = new ArrayList<>();
                detailList.add(detail);
                detailMap.put(className + "@" + detail.getStudentName(), detailList);
            }
            int gradeRank = Integer.parseInt(detail.getGradeRank());
            if (gradeRank > lastGradeRank)
            {
                lastGradeRank = gradeRank;
            }
        }
        for (ExamDetail detail : detailListTwo)
        {
            if (className.equals(detail.getClassName()))
            {
                List<ExamDetail> detailList = detailMap.get(className + "@" + detail.getStudentName());
                detailList.add(detail);
            }
        }

        List<Map<String, Integer>> stepList = new ArrayList<>();
        int maxStep = (lastGradeRank / 100 + 1) * 100;
        int stepStart = 1;
        int stepLength = 99;
        addStepList(stepList, maxStep, stepStart, stepLength);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Integer> map : stepList)
        {
            Map<String, Object> params = new LinkedHashMap<>();
            int min = map.get("stepStart");
            int max = map.get("stepEnd");
            params.put("年级排名", min + "-" + max);
            List<ExamDetail> lastExamDetail = new ArrayList<>();
            List<ExamDetail> lastButTwoExamDetail = new ArrayList<>();
            List<String> changeNames = new ArrayList<>();
            for (Map.Entry<String, List<ExamDetail>> entry : detailMap.entrySet())
            {
                List<ExamDetail> detailList = entry.getValue();
                if (detailList.size() < 2)
                {
                    continue;
                }
                ExamDetail lastExam = detailList.get(0);
                ExamDetail lastButTwoExam = detailList.get(1);
                int lastGrandRank = Integer.parseInt(lastExam.getGradeRank());
                int lastButTwoGrandRank = Integer.parseInt(lastButTwoExam.getGradeRank());
                if (lastGrandRank >= min && lastGrandRank <= max)
                {
                    lastExamDetail.add(lastExam);
                }
                if (lastButTwoGrandRank >= min && lastButTwoGrandRank <= max)
                {
                    lastButTwoExamDetail.add(lastButTwoExam);
                }
                if(lastGrandRank >= min && lastGrandRank <= max && (lastButTwoGrandRank < min || lastButTwoGrandRank > max))
                {
                    changeNames.add(entry.getKey());
                }
                if(lastButTwoGrandRank >= min && lastButTwoGrandRank <= max && (lastGrandRank < min || lastGrandRank > max))
                {
                    changeNames.add(entry.getKey());
                }
            }
            Exam e1 = (Exam)examService.findOne("id", examIds.get(0));
            Exam e2 = (Exam)examService.findOne("id", examIds.get(1));
            params.put(e2.getExamName() , lastButTwoExamDetail.size());
            params.put(e1.getExamName() , lastExamDetail.size());
            List<Map<String, Object>> list = new ArrayList<>();
            for (String change : changeNames)
            {
                List<ExamDetail> detailList = detailMap.get(change);
                if (detailList.size() < 2)
                {
                    continue;
                }
                if(!detailList.get(1).getGradeRank().equals(detailList.get(0).getGradeRank()))
                {
                    Map<String, Object> para = new HashMap<>();
                    para.put("学生姓名", detailList.get(0).getStudentName());
                    para.put("变化趋势", detailList.get(1).getGradeRank() + "名 - " + detailList.get(0).getGradeRank() + "名");
                    para.put("gap", Integer.parseInt(detailList.get(1).getGradeRank()) - Integer.parseInt(detailList.get(0).getGradeRank()));
                    list.add(para);
                }
            }
            Collections.sort(list, new Comparator<Map<String, Object>>()
            {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2)
                {
                    return (int)o1.get("gap") >= (int)o2.get("gap") ? -1 :1;
                }
            });
            params.put("变化人数", list.size());
            params.put("data", list);
            resultList.add(params);
        }
        return resultList;
    }

    private void addStepList(List<Map<String, Integer>> stepList, int maxStep, int stepStart,
        int stepLength)
    {
        int endStep = stepStart;
        while (endStep < maxStep)
        {
            int start = stepStart;
            stepStart = stepStart + stepLength + 1;
            Map<String, Integer> paramMap = new LinkedHashMap<>();
            paramMap.put("stepStart", start);
            endStep = Math.min(stepStart - 1, maxStep);
            paramMap.put("stepEnd", endStep);
            stepList.add(paramMap);
        }
    }

    @RequestMapping("/getOverLineDetailForClass")
    @ResponseBody
    public List<Map<String, Object>> getOverLineDetailForClass(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className,
        @RequestParam(value = "batch", required = true) final String batch)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("limitNumber", 1);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> numberMap = getNumberMap(tnId);
        int batchOneNumber = Integer.parseInt(numberMap.get("batchOne") + "");
        int batchTwoNumber = Integer.parseInt(numberMap.get("batchTwo") + "");
        int batchThrNumber = Integer.parseInt(numberMap.get("batchThr") + "");
        Map<String, Map<String, List<ExamDetail>>> classInfoMap = Maps.newHashMap();
        List<ExamDetail> detailList = examDetailService.findList("examId", examIds.get(0));
        for (ExamDetail detail : detailList)
        {
            String clazzName = detail.getClassName();
            if (className.equals(clazzName))
            {
                Map<String, List<ExamDetail>> mp = classInfoMap.get(clazzName);
                if (null == mp)
                {
                    mp = new HashMap<>();
                    mp.put("batchAll", new ArrayList<ExamDetail>());
                    mp.put("batchOne", new ArrayList<ExamDetail>());
                    mp.put("batchTwo", new ArrayList<ExamDetail>());
                    mp.put("batchThr", new ArrayList<ExamDetail>());
                    classInfoMap.put(clazzName, mp);
                }
                int gradeRank = Integer.parseInt(detail.getGradeRank());
                if (gradeRank <= batchThrNumber)
                {
                    mp.get("batchAll").add(detail);
                }
                if (gradeRank <= batchOneNumber)
                {
                    mp.get("batchOne").add(detail);
                }
                if (gradeRank > batchOneNumber && gradeRank <= batchTwoNumber)
                {
                    mp.get("batchTwo").add(detail);
                }
                if (gradeRank > batchTwoNumber && gradeRank <= batchThrNumber)
                {
                    mp.get("batchThr").add(detail);
                }
            }
        }
        for (Map.Entry<String, Map<String, List<ExamDetail>>> entry : classInfoMap.entrySet())
        {
            Map<String, List<ExamDetail>> examDetailListMap = entry.getValue();
            List<ExamDetail> details = examDetailListMap.get(batch);
            for (ExamDetail d : details)
            {
                Map<String, Object> param = new HashMap<>();
                param.put("班级排名", d.getClassRank());
                param.put("学生姓名", d.getStudentName());
                param.put("成绩", d.getTotleScore());
                param.put("年级排名", d.getGradeRank());
                resultList.add(param);
            }
        }
        return resultList;
    }

    @RequestMapping("/getAvgScoresForClassStudent")
    @ResponseBody
    public List<Map<String, Object>> getAvgScoresForClassStudent(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className,
        @RequestParam(value = "studentName", required = true) String studentName)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("className", className);
        paramMap.put("studentName", studentName);
        List<Map<String, Object>> resultList;
        resultList = examDetailService.getAvgScoresForClassStudent(paramMap);
        if (null == resultList)
        {
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    @RequestMapping("/getExamProperties")
    @ResponseBody
    public List<ExamProperties> getExamProperties(@RequestParam(value = "tnId", required = true) String tnId)
    {
        List<ExamProperties> list = examPropertiesService.findList("tnId", tnId);
        if (list == null || list.size() == 0)
        {
            Map<String, String> map = new HashMap<>();
            map.put("tnId", tnId);
            List<Grade> grades = exiGradeService.selectGradeByTnId(map);
            if (grades == null || grades.size() == 0)
            {
                throw new BizException("1112011", "未设置年级信息，请先设置！");
            }
            ExamProperties p0 = new ExamProperties();
            p0.setTnId(Long.parseLong(tnId));
            p0.setName("defaultGrade");
            p0.setValue(grades.get(0).getGrade());
            examPropertiesService.add(p0);
            ExamProperties p1 = new ExamProperties();
            p1.setTnId(Long.parseLong(tnId));
            p1.setName("defaultClassGrade");
            p1.setValue(grades.get(0).getGrade());
            examPropertiesService.add(p1);
            List<String> classNames = getClassesNameByGrade(tnId, grades.get(0).getGrade());
            if (classNames == null || classNames.size() == 0)
            {
                throw new BizException("1112011", "未设置班级信息，请先设置！");
            }
            ExamProperties p2 = new ExamProperties();
            p2.setTnId(Long.parseLong(tnId));
            p2.setName("defaultClass");
            p2.setValue(classNames.get(0));
            examPropertiesService.add(p2);
            list.add(p0);
            list.add(p1);
            list.add(p2);
        }
        return list;
    }

    @RequestMapping("/saveExamLineProperties")
    @ResponseBody
    public boolean saveExamLineProperties(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "value", required = true) String value,
        @RequestParam(value = "grade", required = true) String grade)
    {
        if(!StringUtils.isNumeric(value))
        {
            throw new BizException("1100221", "请设置正确的关注位次线！");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("limitNumber", 1);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100081", "该年级没有成绩录入！！");
        }
        String lastExamId = examIds.get(0);
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("groupOp", "and");
        if (StringUtils.isEmpty(lastExamId))
        {
            throw new BizException("1110001", "examId不能为空！");
        }
        ConditionsUtil.setCondition(condition, "examId", "=", lastExamId);
        int count = examDetailService.count(condition);
        if(Integer.parseInt(value) > count)
        {
            throw new BizException("1100251", "请设置正确的关注位次线！");
        }
        boolean flag = false;
        Map<String, String> map = new HashMap<>();
        map.put("tnId", tnId);
        map.put("name", "line");
        ExamProperties e = (ExamProperties) examPropertiesService.queryOne(map);
        try
        {
            if (null != e)
            {
                e.setValue(value);
                examPropertiesService.update(e);
            }
            else
            {
                ExamProperties p = new ExamProperties();
                p.setName("line");
                p.setTnId(Long.parseLong(tnId));
                p.setValue(value);
                examPropertiesService.add(p);
            }
        }
        catch (Exception ex)
        {
            return flag;
        }
        flag = true;
        return flag;
    }

    @RequestMapping("/updateExamProperties")
    @ResponseBody
    public boolean updateExamProperties(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "value", required = true) String value)
    {
        boolean flag;
        Map<String, String> map = new HashMap<>();
        map.put("tnId", tnId);
        map.put("name", name);
        ExamProperties e = (ExamProperties)examPropertiesService.queryOne(map);
        if (null != e)
        {
            e.setValue(value);
            examPropertiesService.update(e);
        }
        else
        {
            throw new BizException("1100115", "未找到更新配!");
        }
        flag = true;
        return flag;
    }

    @RequestMapping("/getOverLineDetailForClassTwo")
    @ResponseBody
    public List<Map<String, Object>>  getOverLineDetailForClassTwo(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className,
        @RequestParam(value = "line", required = true) final String line)
    {
        if(!StringUtils.isNumeric(line))
        {
            throw new BizException("1100221", "请设置正确的关注位次线！");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100081", "该年级没有成绩录入！！");
        }
        List<Map<String, Object>> resultList1 = new ArrayList<>();
        float batchOneNumber = Float.parseFloat(line);
        List<ExamDetail> detailLists = examDetailService.findList("examId", examIds.get(0));
        for (ExamDetail detail : detailLists)
        {
            String clazzName = detail.getClassName();
            if (className.equals(clazzName))
            {
                int gradeRank = Integer.parseInt(detail.getGradeRank());
                if (gradeRank <= batchOneNumber)
                {
                    Map<String, Object> param = new HashMap<>();
                    param.put("学生姓名", detail.getStudentName());
                    param.put("班级排名", detail.getClassRank());
                    param.put("成绩", detail.getTotleScore());
                    param.put("年级排名", detail.getGradeRank());
                    resultList1.add(param);
                }
            }
        }

        return resultList1;
    }

    @RequestMapping("/getMostAttendDetailForClassTwo")
    @ResponseBody
    public List<Map<String, Object>> getMostAttendDetailForClassTwo(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className,
        @RequestParam(value = "line", required = true) final String line)
    {
        if(!StringUtils.isNumeric(line))
        {
            throw new BizException("1100221", "请设置正确的关注位次线！");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100081", "该年级没有成绩录入！！");
        }
        if (examIds.size() == 1)
        {
            throw new BizException("1100012", "该年级只有一次成绩录入！！");
        }
        float scoreLine = Float.parseFloat(line);
        float lineScore = 0;
        Map<String, ExamDetail> lastExamDetailMap = new LinkedHashMap<>();
        Set<ExamDetail> details = new TreeSet<>();
        for (int i = 1; i <= examIds.size(); i++)
        {
            String examId = examIds.get(i - 1);
            List<ExamDetail> detailList = examDetailService.findList("examId", examId);
            for (ExamDetail detail : detailList)
            {
                if (i == 1)
                {
                    lastExamDetailMap.put(detail.getClassName() + "@" + detail.getStudentName(), detail);
                }
                int gradeRank = Integer.parseInt(detail.getGradeRank());
                if (gradeRank == scoreLine)
                {
                    lineScore = Float.parseFloat(detail.getTotleScore());
                }
            }
            Map<String, String> params = new HashMap<>();
            params.put("examId", examId);
            for (ExamDetail detail : detailList)
            {
                if(className.equals(detail.getClassName()))
                {
                    float totalScore = Float.parseFloat(detail.getTotleScore());
                    ExamDetail lastDetail = lastExamDetailMap.get(detail.getClassName() + "@" + detail.getStudentName());
                    if (isBigger(lineScore, totalScore) && !isBigger((lineScore - 20),totalScore))
                    {
                        details.add(lastDetail);
                    }
                }
            }
        }
        List<Map<String, String>> allList = getMostAttentionInfo(tnId, grade);
        Map<String, Map<String, String>> weakCourseMap = new HashMap<>();
        for (Map<String, String> map : allList)
        {
            String clazzName = map.get("className") + "";
            if (className.equals(clazzName))
            {
                weakCourseMap.put(map.get("studentName") + "", map);
            }
        }
        Map<String, List<Integer>> examScoreRatioMap = new LinkedHashMap<>();
        setScoreList(examIds, examScoreRatioMap);
        advancedScoreSet = new TreeSet<>();
        Map<String, Map<String, Object>> rMap  = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : examScoreRatioMap.entrySet())
        {
            String examDetailInfo = entry.getKey();
            String[] values = examDetailInfo.split("@");
            if (values.length < 2)
            {
                continue;
            }
            String studentName = values[1];
            String clazzName = values[0];
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("className", clazzName);
            params.put("studentName", studentName);
            Map<String, String> map = weakCourseMap.get(studentName);
            if (null != map)
            {
                params.put("weakCourseOne", map.get("weakCourseOne"));
                params.put("weakCourseTwo", map.get("weakCourseTwo"));
                params.put("gradeRank", map.get("gradeRank"));
                params.put("classRank", map.get("classRank"));
                rMap.put(studentName, params);
            }
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (ExamDetail detail: details)
        {
            String studentName = detail.getStudentName();
            Map<String, Object> params = rMap.get(studentName);
            if(null != params && !params.isEmpty())
            {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("studentName", studentName);
                m.put("gradeRank", detail.getGradeRank());
                m.put("classRank", detail.getClassRank());
                m.put("weakCourseOne", params.get("weakCourseOne"));
                m.put("weakCourseTwo", params.get("weakCourseTwo"));
                resultList.add(m);
            }
        }
        return resultList;
    }


    @RequestMapping("/getMostAdvancedDetailForClass")
    @ResponseBody
    public List<Map<String, Object>> getMostAdvancedDetailForClass(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className,
        @RequestParam(value = "stepStart", required = false) Integer stepStart,
        @RequestParam(value = "stepEnd", required = false) Integer stepEnd,
        @RequestParam(value = "rankStepStart", required = false) Integer rankStepStart,
        @RequestParam(value = "rankStepEnd", required = false) Integer rankStepEnd
    )
    {
        stepStart = null == stepStart ? 1 : stepStart;
        stepEnd = null == stepEnd ? Integer.MAX_VALUE : stepEnd;
        rankStepStart = null == rankStepStart ? 1 : rankStepStart;
        rankStepEnd = null == rankStepEnd ? Integer.MAX_VALUE : rankStepEnd;
        String lastExamId = getLastExamId(grade, tnId);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("examId", lastExamId);
        List<Map<String, String>> allList = getMostAttentionInfo(tnId, grade);
        Map<String, Map<String, String>> weakCourseMap = new HashMap<>();
        for (Map<String, String> map : allList)
        {
            String clazzName = map.get("className") + "";
            if (className.equals(clazzName))
            {
                weakCourseMap.put(map.get("studentName") + "", map);
            }
        }
        paramMap.put("tnId", tnId);
        paramMap.put("grade", grade);
        paramMap.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        if (examIds.size() == 1)
        {
            throw new BizException("1100012", "该年级只有一次成绩录入！！");
        }
        Map<String, List<Integer>> examScoreRatioMap = new LinkedHashMap<>();
        setScoreList(examIds, examScoreRatioMap);
        advancedScoreSet = new TreeSet<>();
        Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : examScoreRatioMap.entrySet())
        {
            String examDetailInfo = entry.getKey();
            List<Integer> rankList = entry.getValue();
            String[] values = examDetailInfo.split("@");
            if (values.length < 2)
            {
                continue;
            }
            String clazzName = values[0];
            String studentName = values[1];
            int advancedScore = 0;
            if (rankList.size() == 0 || rankList.size() == 1)
            {
                continue;
            }
            if (rankList.size() == 2)
            {
                if (rankList.get(0) >= rankList.get(1))
                {
                    continue;
                }
                advancedScore = new BigDecimal(rankList.get(1)).subtract(new BigDecimal(rankList.get(0))).intValue();
            }
            if (rankList.size() == 3)
            {
                if (rankList.get(0) >= rankList.get(1) || rankList.get(1) >= rankList.get(2))
                {
                    continue;
                }
                advancedScore = new BigDecimal(rankList.get(2)).subtract(new BigDecimal(rankList.get(0))).
                    divide(new BigDecimal(2), 0, RoundingMode.HALF_DOWN).intValue();
            }
            List<Map<String, Object>> dataList = resultMap.get(clazzName);
            if (null == dataList)
            {
                dataList = new ArrayList<>();
                resultMap.put(clazzName, dataList);
            }
            if (advancedScore >= stepStart && advancedScore <= stepEnd)
            {
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("className", clazzName);
                params.put("studentName", studentName);
                params.put("advancedScore", advancedScore);
                Map<String, String> map = weakCourseMap.get(studentName);
                if (null != map)
                {
                    int rank = Integer.parseInt(map.get("gradeRank") + "");
                    if (rank >= rankStepStart && rank <= rankStepEnd)
                    {
                        params.put("weakCourseOne", map.get("weakCourseOne"));
                        params.put("weakCourseTwo", map.get("weakCourseTwo"));
                        params.put("gradeRank", map.get("gradeRank"));
                        params.put("classRank", map.get("classRank"));
                        dataList.add(params);
                        advancedScoreSet.add(advancedScore);
                        if (advancedScore > maxAdvancedScore)
                        {
                            maxAdvancedScore = advancedScore;
                        }
                        if (rank > maxGradeRank)
                        {
                            maxGradeRank = rank;
                        }
                    }
                }
            }
        }
        return resultMap.get(className) == null ? new ArrayList<Map<String, Object>>() : resultMap.get(className);
    }

    private void setScoreList(List<String> examIds, Map<String, List<Integer>> examScoreRatioMap)
    {
        for (int i = 1; i <= examIds.size(); i++)
        {
            String examId = examIds.get(i - 1);
            List<ExamDetail> detailList = examDetailService.findList("examId", examId);
            if (i == 1)
            {
                for (ExamDetail detail : detailList)
                {
                    List<Integer> rankList = new ArrayList<>();
                    rankList.add(Integer.parseInt(detail.getGradeRank()));
                    examScoreRatioMap.put(detail.getClassName() + "@" + detail.getStudentName(), rankList);
                }
            }
            if (i > 1)
            {
                for (ExamDetail detail : detailList)
                {
                    List<Integer> rankList =
                        examScoreRatioMap.get(detail.getClassName() + "@" + detail.getStudentName());
                    rankList.add(Integer.parseInt(detail.getGradeRank()));
                }
            }
        }
    }

    @RequestMapping("/getMostAdvancedDetailAdvancedStepList")
    @ResponseBody
    public List<Map<String, Integer>> getMostAdvancedDetailAdvancedStepList(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className,
        @RequestParam(value = "stepStart", required = true) int stepStart,
        @RequestParam(value = "stepLength", required = true) int stepLength
    )
    {
        getMostAdvancedDetailForClass(tnId, grade, className, null, null, null, null);
        int maxStep = (maxAdvancedScore / 10 + 1) * 10;
        List<Map<String, Integer>> stepList = new ArrayList<>();
        if (maxStep >= 10)
        {
            addStepList(stepList, maxStep, stepStart, stepLength);
        }else {
            Map<String, Integer> paramMap = new LinkedHashMap<>();
            paramMap.put("stepStart", 1);
            paramMap.put("stepEnd", maxStep);
            stepList.add(paramMap);
        }
        return stepList;
    }

    @RequestMapping("/getMostAdvancedDetailGradeRankStepList")
    @ResponseBody
    public List<Map<String, Integer>> getMostAdvancedDetailGradeRankStepList(
        @RequestParam(value = "tnId", required = true) String tnId,
        @RequestParam(value = "grade", required = true) String grade,
        @RequestParam(value = "className", required = true) String className,
        @RequestParam(value = "stepStart", required = true) int stepStart,
        @RequestParam(value = "stepLength", required = true) int stepLength)
    {
        getMostAdvancedDetailForClass(tnId, grade, className, null, null, null, null);
        int maxStep = maxGradeRank % stepLength == 0 ? maxGradeRank : (maxGradeRank / 10 + 1) * 10;
        List<Map<String, Integer>> stepList = new ArrayList<>();
        if (maxStep >= 10)
        {
            addStepList(stepList, maxStep, stepStart, stepLength);
        }else
        {
            Map<String, Integer> paramMap = new LinkedHashMap<>();
            paramMap.put("stepStart", 1);
            paramMap.put("stepEnd", maxStep);
            stepList.add(paramMap);
        }
        return stepList;
    }

    /**
     * 查看是否是否有班主任字段
     * @param tnId
     * @return
     */
    private boolean checkClassBoss(String tnId)
    {
        boolean flag =false;
        Map<String, String> paramMap = new HashMap<>();
        String tableName = ParamsUtils.combinationTableName("class", Integer.parseInt(tnId));
        paramMap.put("tableName", tableName);
        if(examDetailService.checkIsTableExist(paramMap))
        {
            paramMap.put("columnName", "class_boss");
            flag = examDetailService.checkIsColumnExist(paramMap);
        }
        return flag;
    }

    private List<Map<String, String>> getMostAttentionInfo(String tnId,String grade)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grade", grade);
        paramMap.put("tnId", tnId);
        paramMap.put("limitNumber", 3);
        List<String> examIds = examDetailService.getLastExamIdByGrade(paramMap);
        if (null == examIds || examIds.size() == 0)
        {
            throw new BizException("1100011", "该年级没有成绩录入！！");
        }
        if (examIds.size() == 1)
        {
            throw new BizException("1100011", "该年级只有一次成绩录入！！");
        }
        String lastExamId = examIds.get(0);
        Map<String, ExamDetail> lastExamDetailMap = new LinkedHashMap<>();
        Map<Long, Set<ExamScoreRatio>> examScoreRatioMap = new LinkedHashMap<>();
        for (int i = 1; i <= examIds.size(); i++)
        {
            String examId = examIds.get(i - 1);
            List<ExamDetail> detailList = examDetailService.findList("examId", examId);
            for (ExamDetail detail : detailList)
            {
                if (i == 1)
                {
                    lastExamDetailMap.put(detail.getClassName() + "@" + detail.getStudentName(), detail);
                    examScoreRatioMap.put(detail.getId(), new TreeSet<ExamScoreRatio>());
                }
            }
            Map<String, String> params = new HashMap<>();
            params.put("examId", examId);
            Map<String, Object> avgScoreMap = examDetailService.getAvgScoresByExamId(params);
            for (ExamDetail detail : detailList)
            {
                fixSelectCourse(detail, lastExamId);
                ExamDetail lastDetail = lastExamDetailMap.get(detail.getClassName() + "@" + detail.getStudentName());
                setRatioMap(examScoreRatioMap, avgScoreMap, detail, lastDetail);
            }
        }
        List<Map<String, String>> dataList = new ArrayList<>();
        for (Map.Entry<String, ExamDetail> entry : lastExamDetailMap.entrySet())
        {
            String batchName = entry.getKey();
            ExamDetail detail = entry.getValue();
            addDataList(lastExamId, examScoreRatioMap, dataList, batchName, detail);
        }
        return dataList;
    }
}
