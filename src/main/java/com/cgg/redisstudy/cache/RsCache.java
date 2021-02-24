package com.cgg.redisstudy.cache;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RsCache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储
     */
    public void store(String key, Object value) {
        Random random = new Random();
        int seconds = random.nextInt(10) * 100 + 120;
        redisTemplate.opsForValue().set(genKey(key, 1L), value, seconds, TimeUnit.SECONDS);
        log.info("redis key:" + genKey(key, 1L) + " expire time seconds:" + seconds);
    }


    /**
     * 查询
     */
    public Map<String, Object> getDicListByKey(String key) {
        Object obj = redisTemplate.opsForHash().values(genKey(key, 1L));
        if (obj != null) {
//            ObjectMapper mapper = new ObjectMapper();
//            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, DicDto.class);
//            try {
//                return mapper.readValue(mapper.writeValueAsString(obj), javaType);
//            } catch (JsonProcessingException e) {
//                log.error("解析失败", e);
//                return Lists.newArrayList();
//            }
        }
        return Maps.newHashMap();
    }

    /**
     * 删除
     */
    public void delete(String bussinessKey) {
        redisTemplate.opsForHash().delete(genKey(bussinessKey, 1L));
    }

    /**
     * 生成key
     */
    private String genKey(String businessKey, Long tenantId) {
        return String.format("id:%d:%d:dic:%s", tenantId, 1, businessKey);
    }

}
