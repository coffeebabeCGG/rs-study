package com.cgg.redisstudy.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cgg
 */

@Slf4j
@Component
public class RsCache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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

    public void setBitMaps(String dateKey, Long userId, boolean isLogin) {
        stringRedisTemplate.opsForValue().setBit(dateKey, userId, isLogin);
    }

    public boolean getBitMaps(String dateKey, Long userId, boolean isLogin) {
        return stringRedisTemplate.opsForValue().getBit(dateKey, userId);
    }

    //统计某一天日活量
    public int activeCountOfDay(String dateKey) {
        String value = stringRedisTemplate.opsForValue().get(dateKey);
        if (ObjectUtils.isEmpty(value)) {
            return 0;
        }
        return BitSet.valueOf(value.getBytes()).cardinality();
    }

    //统计某一时间段连续登录的用户量
    public int activeUserCountOfPeriodTime(int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dateStr = Calendar.getInstance();
        String[] value = new String[day];
        for (int i = 0; i < day; i++) {
            value[i] = simpleDateFormat.format(dateStr.getTime());
            dateStr.set(Calendar.DAY_OF_MONTH, dateStr.get(Calendar.DAY_OF_MONTH) -1);
        }
        return uniqueCounts(value);
    }

    //统计某一时间段日活量
    public int activeCountOfPeriodTime(int day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int totalCount = 0;
        Calendar dateStr = Calendar.getInstance();
        for (int i = 0; i <= day; i++) {
            totalCount += activeCountOfDay(simpleDateFormat.format(dateStr.getTime()));
            dateStr.set(Calendar.DAY_OF_MONTH, dateStr.get(Calendar.DAY_OF_MONTH) -1);
        }
        return totalCount;
    }



    //bitmaps求并集
    private int uniqueCounts(String... dateKey) {
        BitSet all = new BitSet();
        for (int i = 0; i < dateKey.length; i++) {
            String value = stringRedisTemplate.opsForValue().get(dateKey[i]);
            if (ObjectUtils.isEmpty(value)) {
                break;
            }
            BitSet bitSet = BitSet.valueOf(value.getBytes());
            if (i == 0) {
                all = bitSet;
            }
            all.and(bitSet);
        }
        return all.cardinality();
    }

    //bitmaps求或集
    private int orCounts(String... dateKey) {
        BitSet all = new BitSet();
        for (int i = 0; i < dateKey.length; i++) {
            String value = stringRedisTemplate.opsForValue().get(dateKey[i]);
            if (ObjectUtils.isEmpty(value)) {
                break;
            }
            BitSet bitSet = BitSet.valueOf(value.getBytes());
            if (i == 0) {
                all = bitSet;
            }
            all.or(bitSet);
        }
        return all.cardinality();
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
