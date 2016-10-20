package cn.thinkjoy.saas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class WebCotroller {
    /**
     * index
     * 首页
     */
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }


    /**
     * seting-process1
     * 基础信息设置1
     */
    @RequestMapping("/seting-process1")
    public ModelAndView setingProcess1() {
        return new ModelAndView("/basis-settings/seting-process1");
    }
    /**
     * seting-process2
     * 基础信息设置2
     */
    @RequestMapping("/seting-process2")
    public ModelAndView setingProcess2() {
        return new ModelAndView("/basis-settings/seting-process2");
    }
    /**
     * seting-process3
     * 基础信息设置3
     */
    @RequestMapping("/seting-process3")
    public ModelAndView setingProcess3() {
        return new ModelAndView("/basis-settings/seting-process3");
    }
    /**
     * seting-process4
     * 基础信息设置4
     */
    @RequestMapping("/seting-process4")
    public ModelAndView setingProcess4() {
        return new ModelAndView("/basis-settings/seting-process4");
    }
    /**
     * seting-process5
     * 基础信息设置5
     */
    @RequestMapping("/seting-process5")
    public ModelAndView setingProcess5() {
        return new ModelAndView("/basis-settings/seting-process5");
    }
    /**
     * account-management
     * 账号管理
     */
    @RequestMapping("/account-management")
    public ModelAndView accountManagement() {
        return new ModelAndView("/account/account-management");
    }
    /**
     * role-management
     * 角色管理
     */
    @RequestMapping("/role-management")
    public ModelAndView roleManagement() {
        return new ModelAndView("/account/role-management");
    }

}
