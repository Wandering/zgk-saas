package cn.thinkjoy.saas.service.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by douzy on 16/10/13.
 */
public  class ParamsUtils {

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
}
