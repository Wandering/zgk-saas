package cn.thinkjoy.saas.service.common;

import cn.thinkjoy.saas.domain.Resources;
import cn.thinkjoy.saas.dto.MeunDto;
import com.google.common.collect.Lists;

import java.util.List;

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
        meunDto.setIconUrl(resources.getIconUrl());
        return meunDto;
    }

    /**
     * 年级转换
     * @return
     */
    public static Integer converGrade(String grade) {
        Integer g = 0;
        switch (grade) {
            case "高一年级":
                g = 1;
                break;
            case "高二年级":
                g = 2;
                break;
            case "高三年级":
                g = 3;
                break;
        }
        return g;
    }

    public static String converGradeByTag(String grade) {
        String g = "";
        switch (grade) {
            case "1":
                g = "高一年级";
                break;
            case "2":
                g = "高二年级";
                break;
            case "3":
                g = "高三年级";
                break;
        }
        return g;
    }
    public static Integer converWeek(String week) {
        Integer w = 0;
        switch (week) {
            case "星期一":
                w = 1;
                break;
            case "星期二":
                w = 2;
                break;
            case "星期三":
                w = 3;
                break;
            case "星期四":
                w = 4;
                break;
            case "星期五":
                w = 5;
                break;
            case "星期六":
                w = 6;
                break;
            case "星期天":
                w = 7;
                break;
        }
        return w;
    }

    public static Integer converClassTypeByTag(String classType) {
        Integer t = 0;
        switch (classType) {
            case "行政班":
                t = 0;
                break;
            case "文科班":
                t = 2;
                break;
            case "理科班":
                t = 1;
                break;
        }
        return t;
    }
    public static Integer converCourseType(String t){
        Integer m=0;
        switch (t) {
            case "1":
                m = 2;
                break;
            case "2":
                m = 1;
                break;
        }
        return m;
    }
    /**
     * 班级类型转换
     * @return
     */
    public static Integer converClassType(String course) {
        Integer g = 2;
        switch (course) {
            case "语文":
                g = 1;
                break;
            case "数学":
                g = 1;
                break;
            case "英语":
                g = 1;
                break;
            case "外语":
                g = 1;
                break;
        }
        return g;
    }
    /**
     * 重新组装资源菜单
     *
     * @param resources
     * @return
     */
    public static List<MeunDto> convertMeuns(List<Resources> resources){

        List<MeunDto> parentMeuns = Lists.newArrayList();

        // 组装一级菜单
        for(Resources resource : resources){
            if(resource.getParentId() == 0){
                List<MeunDto> sonMeuns = Lists.newArrayList();
                MeunDto meunDto = convert2MeunDto(resource);
                meunDto.setSonMeuns(sonMeuns);
                parentMeuns.add(meunDto);
            }
        }

        // 组装二级菜单
        for(Resources resource : resources){
            for(MeunDto parentMeun : parentMeuns){

                if(resource.getParentId() == 0){
                    continue;
                }

                if(resource.getParentId() == parentMeun.getMeunId()){
                    parentMeun.getSonMeuns().add(convert2MeunDto(resource));
                }
            }
        }

        return parentMeuns;
    }

}
