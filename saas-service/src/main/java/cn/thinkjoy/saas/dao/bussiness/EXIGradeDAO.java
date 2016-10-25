package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/10/24.
 */
public interface EXIGradeDAO {

    /**
     * 年级拖动排序
     *
     * @param grades 需排序的对象集
     * @return
     */
    Integer gradeSortUpdate(List<Grade> grades);


    /**
     * 根据字段查找一个年级对象
     *
     * @param map
     * @return
     */
    Grade selectGradeByTnId(Map map);

    /**
     * 新增年级
     * @param grades
     * @return
     */
    Integer addGrade(@Param("grads")List<Grade> grades);

    /**
     * 删除年级
     * @param map
     * @return
     */
    Integer deleteByMap(Map map);
}
