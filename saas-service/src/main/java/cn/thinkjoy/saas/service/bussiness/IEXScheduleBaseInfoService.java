package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.dto.CourseBaseDto;

import java.util.List;

/**
 * Created by yangguorong on 16/12/7.
 *
 * 排课基本信息设置
 */
public interface IEXScheduleBaseInfoService {

    /**
     * 根据任务ID获取课程信息
     *
     * @param taskId
     * @return
     */
    List<CourseBaseDto> queryCourseInfoByTaskId(long taskId);
}
