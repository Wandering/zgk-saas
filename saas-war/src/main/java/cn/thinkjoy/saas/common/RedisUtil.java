package cn.thinkjoy.saas.common;

import cn.thinkjoy.cloudstack.cache.*;

/**
 * Created by liusven on 2016/12/2.
 */
public class RedisUtil
{
    private static cn.thinkjoy.cloudstack.cache.RedisRepository redisRepository;
    public static final cn.thinkjoy.cloudstack.cache.RedisRepository getInstance(){
        return redisRepository;
    }
    public cn.thinkjoy.cloudstack.cache.RedisRepository getRedisRepository() {
        return redisRepository;
    }
    public void setRedisRepository(cn.thinkjoy.cloudstack.cache.RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}
