package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.dto.CourseManageDto;

import java.util.List;

/**
 * Created by douzy on 17/2/14.
 */
public interface IEXCourseManageService {
    /**
     * 根据TnId查询课程
     * @param tnId
     * @return
     */
    List<CourseManageDto> getCourseByTnId(Integer tnId);

    /**
     * 根据课程和TnId查询年级信息
     * @param tnId
     * @return
     */
    List<CourseManageDto> getCourseGradeByTnIdAndSubject(Integer tnId,String subject);

}
