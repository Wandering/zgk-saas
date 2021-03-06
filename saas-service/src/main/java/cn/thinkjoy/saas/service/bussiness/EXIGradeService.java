package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.Grade;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by douzy on 16/10/24.
 */
public interface EXIGradeService {
    /**
     * 年级拖动排序
     * @param ids  需要调换的两个ID
     * @return
     */
     boolean gradeSortUpdate(Integer tnId,String ids);

    /**
     * 根据字段查找一个年级对象
     *
     * @param map
     * @return
     */
    List<Grade> selectGradeByTnId(Map map);

    /**
     * 设置年级
     * @param tnId 租户ID
     * @param nums 年级信息
     * @return
     */
    boolean AddGrade(Integer tnId,String nums);

    /**
     * 删除年级
     * @param map
     * @return
     */
    Integer deleteByMap(Map map);


    /**
     * 批量删除年级
     * @param tnId 租户ID
     * @param ids 年级标识
     * @return
     */
    boolean removeGrades(String ids);

    /**
     * 新增年级
     * @param tnId 租户ID
     * @param gradeName 年级名称
     * @return
     */
    boolean insertGrade(Integer tnId,String gradeName);

    /**
     * 更新年级
     * @param tnId 租户ID
     * @param gradeName 年级名称
     * @param gid 年级标识
     * @return
     */
    boolean updateGrade(Integer tnId,String gradeName,Integer gid);

    void updateGrade(List<Grade> grades);

    /**
     * 初始化年级信息
     * @param tnId
     * @return
     */
    List<Grade> init(int tnId);

    /**
     * 根据tnId和年级code查询年级
     * @param tnId
     * @param gradeCodes
     * @return
     */
    List<Grade> getGradeByTnIdAndGradeCode(int tnId, Set<Integer> gradeCodes);

    /**
     * 查询年级类型
     * @param tnId
     * @param gradeCode
     * @return
     */
    Integer getGradeType(Integer tnId,Integer gradeCode);
    Integer getGradeType(Integer tnId,String grade);

    /**
     * 判断是该课程是否是走读班课程
     * @param tnId
     * @param gradeCode 年级
     * @param subject 课程
     * @return
     */
    boolean isTeachClass(int tnId,String gradeCode,String subject);
}
