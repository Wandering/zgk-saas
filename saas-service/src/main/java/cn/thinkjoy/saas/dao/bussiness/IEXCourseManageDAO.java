package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.saas.domain.bussiness.CourseBaseInfo;
import cn.thinkjoy.saas.domain.bussiness.CourseManage;
import cn.thinkjoy.saas.dto.BaseDto;
import cn.thinkjoy.saas.dto.CourseManageDto;
import cn.thinkjoy.saas.dto.SelectCourseBaseDto;
import org.apache.ibatis.annotations.Param;

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

    List<CourseBaseInfo> queryListByCondition(Map<String,Object> map);

    /**
     * 根据租户ID和类型查询课程信息
     *
     * @param tnId
     * @param type
     * @return
     */
    List<SelectCourseBaseDto> getSelectCourses(
            @Param("tnId") Integer tnId,
            @Param("grade") Integer grade,
            @Param("type") Integer type
    );
}
