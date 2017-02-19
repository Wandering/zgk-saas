package cn.thinkjoy.saas.service.common;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.saas.enums.ErrorCode;

/**
 * Created by yangguorong on 16/9/23.
 */
public class ExceptionUtil {

    /**
     * 抛出异常
     *
     * @param errorCode
     */
    public static void throwException(ErrorCode errorCode){
        throw new BizException(errorCode.getCode(),errorCode.getMessage());
    }

}
