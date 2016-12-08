package cn.thinkjoy.saas.controller.bussiness;

import cn.thinkjoy.saas.common.UserContext;
import cn.thinkjoy.saas.core.Constant;
import cn.thinkjoy.saas.domain.JwTeachDate;
import cn.thinkjoy.saas.enums.ErrorCode;
import cn.thinkjoy.saas.service.IJwTeachDateService;
import cn.thinkjoy.saas.service.common.ExceptionUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/12/7.
 */
@Controller
@RequestMapping("/teachTime")
public class TeachTimeController {
    @Autowired
    private IJwTeachDateService jwTeachDateService;
    @ResponseBody
    @RequestMapping(value = "/saveTeachTime",method = RequestMethod.POST)
    public boolean saveTeachTime(@RequestParam Integer taskId,
                                 @RequestParam String teachDate,
                                 @RequestParam String teachTime){
        if (StringUtils.isEmpty(teachDate)||StringUtils.isEmpty(teachTime)){
            ExceptionUtil.throwException(ErrorCode.PARAN_NULL);
        }
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        String[] dates = teachDate.split("\\|");
        char[] times = teachTime.toCharArray();
        try {
            for (String date : dates) {
                JwTeachDate jwTeachDate = new JwTeachDate();
                jwTeachDate.setTaskId(taskId);
                jwTeachDate.setTnId(tnId);
                jwTeachDate.setCreateDate(System.currentTimeMillis());
                jwTeachDate.setTeachDate(date);
                jwTeachDate.setTeachDetail(getTime(times));
                jwTeachDateService.add(jwTeachDate);
            }
        }catch (DuplicateKeyException e){
            return false;
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/queryTeachTime",method = RequestMethod.GET)
    public Map<String, Object> queryTeachTime(@RequestParam Integer taskId){
        Map<String,Object> result = Maps.newHashMap();
        Map<String,Object> map = Maps.newHashMap();
        map.put("taskId",taskId);
        Integer tnId = Integer.valueOf(UserContext.getCurrentUser().getTnId());
        map.put("tnId",tnId);
        List<JwTeachDate> list = jwTeachDateService.queryList(map,"tid","asc");
        if (list.size()==0){
            return result;
        }
        StringBuffer buffer = new StringBuffer();
        for (JwTeachDate jwTeachDate:list){
            buffer.append(jwTeachDate.getTeachDate()).append("|");
        }
        if (buffer.length()>0){
            buffer.delete(buffer.length()-1,buffer.length());
        }
        String time = "";
        if (list!=null && list.size()>0){
            JwTeachDate jwTeachDate = list.get(0);
            String[] strings = jwTeachDate.getTeachDetail().split(Constant.TIME_INTERVAL);
            for (String s:strings){
                time+=s.length();
            }
            if (strings.length<3){
                time+=0;
            }
        }

        result.put("teachDate",buffer.toString());
        result.put("teachTime",time);
        return result;
    }


//    public static void main(String[] args) {
//        String s = "430";
//        System.out.println(getTime(s.toCharArray()));
//    }

    private static Integer getTimeString(char c){
        Integer ct = Integer.valueOf(String.valueOf(c));
        double rtn = 0;
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数

        for (int i =ct;i>0;i--){
            rtn +=ct * Math.pow(10,i-1);
        }
        int i = ct;
        return Integer.valueOf(df.format(rtn/i));
    }

    private static String getTime(char[] chars){
        StringBuffer s = new StringBuffer();
        for (char c : chars) {
            if (c != '0'){
                s.append(getTimeString(c));
            }
            s.append(Constant.TIME_INTERVAL);
        }
        if (s.length()>0){
            s.delete(s.length()-1,s.length());
        }
        return s.toString();
    }

}
