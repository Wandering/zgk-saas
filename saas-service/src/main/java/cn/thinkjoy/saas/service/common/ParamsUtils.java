package cn.thinkjoy.saas.service.common;

import cn.thinkjoy.saas.domain.bussiness.TeantCustom;
import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by douzy on 16/10/13.
 */
public  class ParamsUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamsUtils.class);

    /**
     * ids 分隔符
     */
    protected static final String IDS_DIVIDE_CHAR = "-";

    /**
     * 动态表名组装分隔符
     */
    public static final String TABLE_NAME_COMBIN_CHAR="_";

    /**
     * 年级:数量
     */
    public static final String CLASSROOM_GRADE_COMBIN_CHAR=":";
    public static final String CLASSROOM_NUMBER_COMBIN_CHAR="\\|";

    /**
     * ids 转换Array
     *
     * @return
     */
    public static List<String> idsSplit(String parmas) {
        if (StringUtils.isBlank(parmas))
            return null;

        String[] ids = parmas.split(IDS_DIVIDE_CHAR);

        List<String> idsList = Arrays.asList(ids);

        return idsList;
    }

    /**
     * 获取模块下对应字段
     * @param type
     * @return
     */
    public static String getGradeKey(String type) {
        Map map = new HashMap();
        map.put("class","class_grade");
        map.put("class_adm", "class_grade");
        map.put("class_edu", "class_grade");
        map.put("student", "student_grade");
        map.put("teacher", "teacher_grade");
        return map.get(type).toString();
    }

    /**
     * 获取年份
     * @return
     */
    public static Integer getYear() {
        Calendar a = Calendar.getInstance();

        return a.get(Calendar.YEAR);
    }

    /**
     *  动态表名组装     规则: saas_{type}_{tnId}_excel
     * @param type   class:班级  teacher:教师
     * @param tnId   租户ID
     * @return
     */
    public static String combinationTableName(String type,Integer tnId) {
        StringBuffer stringBuffer = new StringBuffer("saas");
        stringBuffer.append(ParamsUtils.TABLE_NAME_COMBIN_CHAR);
        stringBuffer.append(tnId);
        stringBuffer.append(ParamsUtils.TABLE_NAME_COMBIN_CHAR);
        stringBuffer.append(type);
        stringBuffer.append(ParamsUtils.TABLE_NAME_COMBIN_CHAR);
        stringBuffer.append("excel");
        return stringBuffer.toString();
    }

    /**
     * excel数据格式校验
     * @param excelValues
     * @param tenantConfigInstanceViews
     * @return
     */
    public static String excelValueValid(List<LinkedHashMap<String, String>> excelValues,List<TenantConfigInstanceView> tenantConfigInstanceViews) {

        LOGGER.info("================excel数据格式校验 S================");
        String result = "excel数据校验失败";

        if (excelValues == null || tenantConfigInstanceViews == null)
            return result;
        Integer excelLen = excelValues.size();

        LOGGER.info("excel集大小:" + excelLen);
        LOGGER.info("表头集大小:" + tenantConfigInstanceViews.size());

        for (int x = 0; x < excelLen; x++) {

            LinkedHashMap<String, String> rowsMap = excelValues.get(x);

            Iterator iter = rowsMap.entrySet().iterator();

            int y = 0;

            while (iter.hasNext()) {

                Map.Entry entry = (Map.Entry) iter.next();
                String key = entry.getKey().toString();
                String val = entry.getValue().toString();

                TenantConfigInstanceView tenantConfigInstanceView = tenantConfigInstanceViews.get(y);

                String checkRuleStr = tenantConfigInstanceView.getCheckRule();

                LOGGER.info("原始正则:" + checkRuleStr);
                String regularStr = getConverReg(checkRuleStr);
                LOGGER.info("转换正则:" + regularStr);

                LOGGER.info((x+1+1) + "行-" + (key+1) + "列 value:" + val + "");
                boolean isNoth = isNothing(val);
                if (isNoth)
                    return "输入的数据不完整，请完善数据后再上传";

                boolean valid = (StringUtils.isBlank(regularStr) ? true : isRegValid(regularStr, val));
                //select 类型校验
                if (valid) {
                    if (tenantConfigInstanceView.getDataType().equals("select")&&StringUtils.isNotBlank(tenantConfigInstanceView.getDataValue())) {
                        valid = false;
                        for (String data : tenantConfigInstanceView.getDataValue().split("-")) {
                            if (data.equals(val)) {
                                valid = true;
                                break;
                            }
                        }
                    }
                }
                LOGGER.info("校验结果:" + valid);
                if (!valid) {
                    return (x+1+1)+ "行-" + (key+1) + "列,数据校验失败,请检查!";
                }
                y++;
            }
        }
        result = "SUCCESS";
        LOGGER.info("================excel数据格式校验 E================");
        return result;
    }

    public static Map getTeantCustomDataValue(List<TeantCustom> teantCustoms,String type) {

        Map map = new HashMap();

        String key1 = null, key2 = null;

        switch (type) {
            case "class":
                key1 = "class_grade";
                key2 = "class_name";
                break;
            case "class_adm":
                key1 = "class_grade";
                key2 = "class_name";
                break;
            case "class_edu":
                key1 = "class_grade";
                key2 = "class_name";
                break;
            case "teacher":
                key1 = "teacher_name";
                key2 = "teacher_major_type";
                break;
            case "student":
                key1 = "student_no";
                key2 = "student_name";
                break;
        }

        List<TeantCustom> customs = JSONArray.parseArray(teantCustoms.toString(), TeantCustom.class);
        for (TeantCustom teantCustom:customs) {
            if (teantCustom.getKey().equals(key1)) {
                map.put("value1", teantCustom.getValue());
            }
            if (teantCustom.getKey().equals(key2)) {
                map.put("value2", teantCustom.getValue());
            }
        }
        return map;
    }

    /**
     * 空值校验
     * @return
     */
    public static Boolean isNothing(String str) {
        return str.equals(EnumUtil.EXCEL_VALUE_NOTHING);
    }


    /**
     * 查找是否存在重复学号
     * @param excelValues
     * @return
     */
    public static String repeatStudentNo(List<LinkedHashMap<String, String>> excelValues) {
        String result = "SUCCESS";

        String stuNo = "";
        for (int i = 0; i < excelValues.size() - 1; i++) {
            stuNo = excelValues.get(i).get("2");
            for (int j = i + 1; j < excelValues.size(); j++) {
                if (stuNo.equals(excelValues.get(j).get("2"))) {
                    result = "第" + (i + 1) + "行学号信息与第" + (j + 1) + "行学号信息重复";
                    return result;
                }
            }
        }

        return result;
    }
    /**
     * 正则匹配结果
     * @param reg  正则
     * @param str
     * @return
     */
    private static boolean isRegValid(String reg,String str) {
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * js正则转换java正则
     * @param regStr 正则
     * @return
     */
    private static String getConverReg(String regStr) {
        if (StringUtils.isBlank(regStr))
            return null;
        return regStr.replace("/", "").replace("\\", "\\");
    }

    public static void main(String[] arg){
        String regStr="^\\s*[\\s\\S]{1,12}\\s*$";


        System.out.println(regStr.replace("/", "").replace("\\", "\\"));

//
//        String s="^\\s*[\\\\s\\\\S]{1,10}\\\\s*$";
//        String str="1234567";
//        Pattern pattern = Pattern.compile(s,Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(str);
//        System.out.println(matcher.matches());

    }
}
