package com.cgg.redisstudy.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author cgg
 */

@Slf4j
@Component
public class RsCache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储
     */
    public <T> void store(String key, List<T> value) {
        Random random = new Random();
        int seconds = random.nextInt(10) * 1000 + 120;
        Map<String, T> map = Maps.newHashMap();
        value.forEach(v -> map.put(key + value.indexOf(v) + System.currentTimeMillis(), v));
        redisTemplate.opsForHash().putAll(genKey(key, 1L), map);
        redisTemplate.expire(genKey(key, 1L), seconds, TimeUnit.SECONDS);
        log.info("redis key:" + genKey(key, 1L) + " expire time seconds:" + seconds);
    }

    /**
     * 查询
     */
    public <T> List<T> getByKey(String key, Class<T> t) {
        List<Object> list = redisTemplate.opsForHash().values(genKey(key, 1L));
        return JSONObject.parseArray(JSONArray.toJSONString(list), t);
    }

    /**
     * 删除
     */
    public void delete(String bussinessKey) {
        redisTemplate.delete(genKey(bussinessKey, 1L));
    }

    /**
     * 删除
     */
    public void deleteHKey(String serviceKey) {
        redisTemplate.opsForHash().delete(serviceKey);
    }

    /**
     * 存储zset
     */
    public <T> void storeSet(String businessKey, T t, double score) {
        redisTemplate.opsForZSet().add(genKey(businessKey, 2L), t, score);
    }

    /**
     * 查询set
     */
    public <T> T getSet(String businessKey) {
        redisTemplate.opsForZSet();
        return null;
    }

    /**
     * 生成key
     */
    private String genKey(String businessKey, Long serviceKey) {
        return String.format("id:%d:%d:rs:%s", serviceKey, 1, businessKey);
    }

}
