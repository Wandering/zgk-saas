package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.service.IGradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by douzy on 16/10/21.
 */
@Controller
@RequestMapping(value = "/manage")
public class ManageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageController.class);

    @Resource
    IGradeService iGradeService;



}
