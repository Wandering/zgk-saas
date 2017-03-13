package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.JwCourseTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangyongping on 2017/2/23.
 */
public interface IEXJwCourseTableDAO {
    int insertList(@Param("list") List<JwCourseTable> list);
}
