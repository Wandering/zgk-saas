package cn.thinkjoy.saas.service.common;

import cn.thinkjoy.saas.domain.Resources;
import cn.thinkjoy.saas.dto.MeunDto;

/**
 * Created by yangguorong on 16/10/17.
 *
 * 模型转换工具类
 */
public class ConvertUtil {

    /**
     * Resources --> MeunDto
     *
     * @param resources
     * @return
     */
    public static MeunDto convert2MeunDto(Resources resources){
        MeunDto meunDto = new MeunDto();
        meunDto.setMeunId(Integer.valueOf(resources.getId().toString()));
        meunDto.setMeunName(resources.getResName());
        meunDto.setMeunUrl(resources.getResUrl());
        return meunDto;
    }

}
