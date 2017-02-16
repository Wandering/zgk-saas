package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.dto.BaseDto;
import cn.thinkjoy.saas.dto.ClassBaseDto;
import cn.thinkjoy.saas.service.bussiness.IEXTenantCustomService;
import cn.thinkjoy.saas.service.common.ParamsUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yangguorong on 17/2/15.
 */
@Controller
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private IEXTenantCustomService iexTenantCustomService;

    @RequestMapping(value = "/getClassByCourse",method = RequestMethod.GET)
    @ResponseBody
    public List<BaseDto> getClassByCourse(@RequestParam Integer tnId, @RequestParam String course) {

        List<ClassBaseDto> dtos = iexTenantCustomService.getClassInfos(Constant.CLASS_EDU,tnId,course,null);

        return convert2BaseDto(dtos);
    }

    @RequestMapping(value = "/getClassByGrade",method = RequestMethod.GET)
    @ResponseBody
    public List<BaseDto> getClassByGrade(@RequestParam Integer tnId, @RequestParam String grade) {

        List<ClassBaseDto> dtos = iexTenantCustomService.getClassInfos(Constant.CLASS_ADM,tnId,null,grade);

        return convert2BaseDto(dtos);
    }

    private List<BaseDto> convert2BaseDto(List<ClassBaseDto> dtos){
        List<BaseDto> baseDtos = Lists.newArrayList();
        for(ClassBaseDto dto : dtos){
            BaseDto baseDto = new BaseDto();
            baseDto.setId(dto.getClassId());
            baseDto.setName(dto.getClassName());
            baseDtos.add(baseDto);
        }
        return baseDtos;
    }

}
