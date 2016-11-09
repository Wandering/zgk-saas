package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.saas.domain.Roles;
import cn.thinkjoy.saas.dto.MeunDto;

import java.util.List;

/**
 * Created by yangguorong on 16/10/18.
 */
public interface IEXRoleService {

    /**
     * 创建角色
     *
     * @param roles
     * @param meunIds
     */
    void createRole(
            Roles roles,
            String meunIds
    );

    /**
     * 创建角色
     *
     * @param roles
     * @param meunIds
     */
    void updateRole(
            Roles roles,
            String meunIds
    );

    /**
     * 删除角色
     *
     * @param roleId
     */
    void deleteRole(
            int roleId
    );

    /**
     * 根据角色ID获取角色对应菜单(-1为获取所有)
     *
     * @param roleId
     * @return
     */
    List<MeunDto> getResourcesByRoleId(
            int roleId
    );
}
