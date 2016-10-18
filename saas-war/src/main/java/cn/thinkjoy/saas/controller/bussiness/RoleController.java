package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dao.IRolesDAO;
import cn.thinkjoy.saas.domain.RelationUserRole;
import cn.thinkjoy.saas.domain.Roles;
import cn.thinkjoy.saas.dto.MeunDto;
import cn.thinkjoy.saas.service.IRolesService;
import cn.thinkjoy.saas.service.bussiness.IEXRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by yangguorong on 16/10/14.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IEXRoleService iexRoleService;

    @Autowired
    private IRolesService iRolesService;

    @ResponseBody
    @ApiDesc(value = "创建角色",owner = "杨国荣")
    @RequestMapping(value = "/createRole",method = RequestMethod.POST)
    public void createRole(@RequestBody Request request) {

        String roleName = request.getDataString("roleName");
        String roleDesc = request.getDataString("roleDesc");
        int tnId = request.getDataInteger("tnId");
        String meunIds = request.getDataString("meunIds");

        Roles roles = new Roles();
        roles.setCreateDate(new Date());
        roles.setRoleDesc(roleDesc);
        roles.setRoleName(roleName);
        roles.setTnId(tnId);

        iexRoleService.createRole(
                roles,
                meunIds
        );
    }

    @ResponseBody
    @ApiDesc(value = "根据角色ID获取角色对应菜单(-1为获取所有)",owner = "杨国荣")
    @RequestMapping(value = "/getMeunsByRoleId/{roleId}",method = RequestMethod.GET)
    public List<MeunDto> getResourcesByRoleId(@PathVariable int roleId) {

        List<MeunDto> dtos = iexRoleService.getResourcesByRoleId(roleId);

        return dtos;
    }

    @ResponseBody
    @ApiDesc(value = "修改角色",owner = "杨国荣")
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    public void updateRole(@RequestBody Request request) {

        int roleId = request.getDataInteger("roleId");
        int tnId = request.getDataInteger("tnId");
        String roleName = request.getDataString("roleName");
        String roleDesc = request.getDataString("roleDesc");
        String meunIds = request.getDataString("meunIds");

        Roles roles = new Roles();
        roles.setRoleDesc(roleDesc);
        roles.setRoleName(roleName);
        roles.setTnId(tnId);
        roles.setId(roleId);

        iexRoleService.updateRole(
                roles,
                meunIds
        );
    }

    @ResponseBody
    @ApiDesc(value = "删除角色",owner = "杨国荣")
    @RequestMapping(value = "/deleteRole/{roleId}/{userId}",method = RequestMethod.GET)
    public void deleteRole(@PathVariable int roleId,@PathVariable int userId) {

        // TODO 鉴权

        iexRoleService.deleteRole(roleId);
    }

    @ResponseBody
    @ApiDesc(value = "根据租户ID查询租户角色集合",owner = "杨国荣")
    @RequestMapping(value = "/queryRolesByTnId/{tnId}",method = RequestMethod.GET)
    public List<Roles> queryRolesByTnId(@PathVariable int tnId) {

        List<Roles> rolesList = iRolesService.findList(
                "tnId",
                tnId,
                Constant.ID,
                SqlOrderEnum.DESC
        );

        return rolesList;
    }
}
