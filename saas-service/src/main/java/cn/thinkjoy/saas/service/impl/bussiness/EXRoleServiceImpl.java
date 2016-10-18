package cn.thinkjoy.saas.service.impl.bussiness;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IRelationRoleResourceDAO;
import cn.thinkjoy.saas.dao.IRelationUserRoleDAO;
import cn.thinkjoy.saas.dao.IResourcesDAO;
import cn.thinkjoy.saas.dao.IRolesDAO;
import cn.thinkjoy.saas.dao.bussiness.IEXRoleDAO;
import cn.thinkjoy.saas.domain.RelationRoleResource;
import cn.thinkjoy.saas.domain.Resources;
import cn.thinkjoy.saas.domain.Roles;
import cn.thinkjoy.saas.dto.MeunDto;
import cn.thinkjoy.saas.service.bussiness.IEXRoleService;
import cn.thinkjoy.saas.service.common.ConvertUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangguorong on 16/10/18.
 */
@Service("EXRoleService")
public class EXRoleServiceImpl implements IEXRoleService {

    @Autowired
    private IRolesDAO iRolesDAO;

    @Autowired
    private IEXRoleDAO iexRoleDAO;

    @Autowired
    private IResourcesDAO iResourcesDAO;

    @Autowired
    private IRelationUserRoleDAO iRelationUserRoleDAO;

    @Autowired
    private IRelationRoleResourceDAO iRelationRoleResourceDAO;

    @Override
    public void createRole(Roles roles, String meunIds) {

        iRolesDAO.insert(roles);

        batchInsertRoleRes(roles,meunIds);
    }

    @Override
    public void updateRole(Roles roles, String meunIds) {

        // 删除原有角色对应菜单关系
        iRelationRoleResourceDAO.deleteByProperty("roleId",roles.getId());
        // 批量插入角色与菜单关系
        batchInsertRoleRes(roles,meunIds);
        // 修改角色信息
        iRolesDAO.update(roles);
    }

    @Override
    public void deleteRole(int roleId) {
        // 删除用户与角色关系
        iRelationUserRoleDAO.deleteByProperty("roleId",roleId);
        // 删除角色与菜单关系
        iRelationRoleResourceDAO.deleteByProperty("roleId",roleId);
        // 删除角色
        iRolesDAO.deleteByProperty("id",roleId);
    }

    /**
     * 批量插入角色与菜单关系数据
     *
     * @param roles
     * @param meunIds
     */
    private void batchInsertRoleRes(Roles roles, String meunIds){

        String [] meunIdArr = meunIds.split(",");

        List<RelationRoleResource> resources = Lists.newArrayList();
        for(int i=0;i<meunIdArr.length;i++){
            RelationRoleResource resource = new RelationRoleResource();
            resource.setRoleId((int)roles.getId());
            resource.setResId(Integer.valueOf(meunIdArr[i]));
            resources.add(resource);
        }

        iexRoleDAO.batchInsertRoleRes(resources);
    }

    @Override
    public List<MeunDto> getResourcesByRoleId(int roleId) {

        List<Resources> resourcesList = Lists.newArrayList();

        if(roleId == -1){
            resourcesList = iResourcesDAO.findAll(
                    Constant.ID,
                    Constant.DESC
            );
        }else {
            resourcesList = iexRoleDAO.getResourcesByRoleId(roleId);
        }

        return ConvertUtil.convertMeuns(resourcesList);
    }
}
