package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.dto.CourseManageDto;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 17/2/13.
 */
public interface IEXCourseManageDAO extends IBaseDAO<CourseManage> {
    /**
     * 根据TnId查询课程
     *
     * @param params
     * @return
     */
    List<CourseManageDto> getCourseByParams(Map<String,Object> params);

}
