package cn.thinkjoy.saas.service.common;

import cn.thinkjoy.saas.enums.SubjectEnum;

/**
 * Created by yangyongping on 2017/2/17.
 */
public class EduClassUtil {
    public static boolean isEduSubject(String subject) {
        return SubjectEnum.dl.equals(subject) ||
                SubjectEnum.hx.equals(subject) ||
                SubjectEnum.sw.equals(subject) ||
                SubjectEnum.wl.equals(subject) ||
                SubjectEnum.zz.equals(subject) ||
                SubjectEnum.ty.equals(subject) ||
                SubjectEnum.ls.equals(subject);
    }
}
