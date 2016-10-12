package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.common.domain.ListWrapper;
import cn.thinkjoy.common.protocol.RequestT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 示例http api
 */
@Controller
@RequestMapping(value = "/user")
public class HttpApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpApiController.class);

    /**
     * 示例服务
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String service1() {
        System.out.println("run here");
        return "return str";
    }

    /**
     * 示例服务
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    @ResponseBody
    public ListWrapper<String> service2(@RequestBody RequestT<String> request) {
        System.out.println("run here");
        ListWrapper<String> listWrapper = new ListWrapper<String>();

        return listWrapper;
    }


    @RequestMapping(value = "/model")
    public ModelAndView model(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("module/" + "" +"mock.ftl");
        return mav;
    }
}

