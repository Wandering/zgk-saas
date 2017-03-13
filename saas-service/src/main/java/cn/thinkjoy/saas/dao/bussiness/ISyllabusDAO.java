package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.dto.JwCourseTableDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2017/2/27.
 */
public interface ISyllabusDAO {
    List<JwCourseTableDTO> queryList(@Param("params") Map<String,Object> map,@Param("teacherTableName")String teacherTableName,@Param("admClassTableName")String admClassTableName,@Param("eduClassTableName")String eduClassTableName,@Param("roomTableName")String roomTableName);
}
