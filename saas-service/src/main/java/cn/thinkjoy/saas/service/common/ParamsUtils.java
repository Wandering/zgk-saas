package cn.thinkjoy.saas.service.common;

import cn.thinkjoy.saas.domain.bussiness.TenantConfigInstanceView;
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
        map.put("class", "class_grade");
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
    public static boolean excelValueValid(List<LinkedHashMap<String, String>> excelValues,List<TenantConfigInstanceView> tenantConfigInstanceViews) {

        LOGGER.info("================excel数据格式校验 S================");
        boolean result = false;

        if (excelValues == null || tenantConfigInstanceViews == null)
            return result;
        Integer excelLen=excelValues.size();

        LOGGER.info("excel集大小:"+excelLen);
        LOGGER.info("表头集大小:"+tenantConfigInstanceViews.size());

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

                LOGGER.info(x + "行-" + key + "列 value:" + val + "");
                boolean valid = (StringUtils.isBlank(regularStr) ? true : isRegValid(regularStr, val));
                LOGGER.info("校验结果:" + valid);
                if (!valid)
                    return result;
                y++;
            }
        }
        result = true;
        LOGGER.info("================excel数据格式校验 E================");
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
