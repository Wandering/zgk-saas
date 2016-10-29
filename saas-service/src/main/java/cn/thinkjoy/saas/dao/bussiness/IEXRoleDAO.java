package cn.thinkjoy.saas.dao.bussiness;

import cn.thinkjoy.saas.domain.RelationRoleResource;
import cn.thinkjoy.saas.domain.Resources;
import cn.thinkjoy.saas.dto.MeunDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangguorong on 16/10/18.
 */
public interface IEXRoleDAO {

    /**
     * 批量插入角色与资源关系
     *
     * @param resouces
     */
    void batchInsertRoleRes(
            @Param("resouces") List<RelationRoleResource> resouces
    );

    /**
     * 根据角色ID获取角色对应菜单
     *
     * @param roleId
     * @return
     */
    List<Resources> getResourcesByRoleId(
            @Param("roleId") int roleId
    );
}
