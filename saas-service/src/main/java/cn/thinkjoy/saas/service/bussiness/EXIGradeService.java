package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.Grade;

import java.util.Map;

/**
 * Created by douzy on 16/10/24.
 */
public interface EXIGradeService {
    /**
     * 年级拖动排序
     * @param ids  需要调换的两个ID
     * @return
     */
    boolean gradeSortUpdate(String ids);

    /**
     * 根据字段查找一个年级对象
     *
     * @param map
     * @return
     */
    Grade selectGradeByTnId(Map map);

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
}
