package cn.thinkjoy.saas.service.common;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 随机的验证码生成工具（数字或字母加数字）
 * 
 *
 */
public class RandomCodeUtil {

    private static final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 生成指定长度的数字验证码
     *
     * @param length
     * @return
     */
    public static String generateNumCode(int length) {

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(RandomUtils.nextInt(10));
        }
        return code.toString();
    }

    /**
     * 生成制定长度的字母加数字验证码
     *
     * @param length
     * @return
     */
    public static String generateCharCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(generateOneChar());
        }
        return code.toString();
    }

    private static char generateOneChar() {
        int index = RandomUtils.nextInt(36);
        return BASE.charAt(index);
    }

}