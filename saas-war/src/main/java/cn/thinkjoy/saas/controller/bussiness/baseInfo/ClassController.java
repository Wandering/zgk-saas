package cn.thinkjoy.saas.controller.bussiness.baseInfo;

import cn.thinkjoy.saas.dto.BaseDto;
import com.google.common.collect.Lists;
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

    @RequestMapping(value = "/getClassByCourse",method = RequestMethod.GET)
    @ResponseBody
    public List<BaseDto> getClassByCourse(@RequestParam Integer tnId, @RequestParam String course) {
        List<BaseDto> dtos = Lists.newArrayList();
        BaseDto dto1 = new BaseDto();
        dto1.setId(1);
        dto1.setName("物理1班");
        dtos.add(dto1);
        BaseDto dto2 = new BaseDto();
        dto2.setId(2);
        dto2.setName("物理2班");
        dtos.add(dto2);
        BaseDto dto3 = new BaseDto();
        dto3.setId(3);
        dto3.setName("物理3班");
        dtos.add(dto3);
        return dtos;
    }

    @RequestMapping(value = "/getClassByGrade",method = RequestMethod.GET)
    @ResponseBody
    public List<BaseDto> getClassByGrade(@RequestParam Integer tnId, @RequestParam String grade) {
        List<BaseDto> dtos = Lists.newArrayList();
        BaseDto dto1 = new BaseDto();
        dto1.setId(1);
        dto1.setName("1班");
        dtos.add(dto1);
        BaseDto dto2 = new BaseDto();
        dto2.setId(2);
        dto2.setName("2班");
        dtos.add(dto2);
        BaseDto dto3 = new BaseDto();
        dto3.setId(3);
        dto3.setName("3班");
        dtos.add(dto3);
        return dtos;
    }
}
