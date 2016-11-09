package cn.thinkjoy.saas.common;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by liusven on 2016/11/7.
 */
public class ConditionsUtil
{
    public static void setCondition(Map<String,Object> condition,String field,String op,String data){
        Map<String,Object> map= Maps.newHashMap();
        map.put("field",field);
        map.put("op",op);
        map.put("data",data);
        condition.put(field,map);
    }
}
