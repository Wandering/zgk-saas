package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.protocol.Request;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.saas.dto.MeunDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yangguorong on 16/10/14.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @ResponseBody
    @ApiDesc(value = "创建角色",owner = "杨国荣")
    @RequestMapping(value = "/createRole",method = RequestMethod.POST)
    public void createRole(HttpServletRequest request) {

    }

    @ResponseBody
    @ApiDesc(value = "根据角色ID获取角色对应菜单(-1为获取所有)",owner = "杨国荣")
    @RequestMapping(value = "/getMeunsByRoleId/{roleId}",method = RequestMethod.GET)
    public List<MeunDto> getResourcesByRoleId(@PathVariable int roleId) {

        return null;
    }

    @ResponseBody
    @ApiDesc(value = "修改角色",owner = "杨国荣")
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    public void updateRole(HttpServletRequest request) {

    }

    @ResponseBody
    @ApiDesc(value = "删除角色",owner = "杨国荣")
    @RequestMapping(value = "/deleteRole/{roleId}",method = RequestMethod.GET)
    public void deleteRole(@PathVariable int roleId) {

    }

    @ResponseBody
    @ApiDesc(value = "根据租户ID查询租户角色集合",owner = "杨国荣")
    @RequestMapping(value = "/queryRolesByTnId/{tnId}",method = RequestMethod.GET)
    public void queryRolesByTnId(@PathVariable int tnId) {

        return ;
    }
}
