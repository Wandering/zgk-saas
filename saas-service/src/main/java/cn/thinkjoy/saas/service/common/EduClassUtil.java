package cn.thinkjoy.saas.service.common;

import cn.thinkjoy.saas.enums.SubjectEnum;

/**
 * Created by yangyongping on 2017/2/17.
 */
public class EduClassUtil {
    public static boolean isEduSubject(String subject) {
        return SubjectEnum.dl.getSub().equals(subject) ||
                SubjectEnum.hx.getSub().equals(subject) ||
                SubjectEnum.sw.getSub().equals(subject) ||
                SubjectEnum.wl.getSub().equals(subject) ||
                SubjectEnum.zz.getSub().equals(subject) ||
                SubjectEnum.ty.getSub().equals(subject) ||
                SubjectEnum.ls.getSub().equals(subject);
    }
}
