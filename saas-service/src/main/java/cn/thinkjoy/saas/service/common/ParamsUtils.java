package cn.thinkjoy.saas.service.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
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
}
