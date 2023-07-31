package com.sword.framework.plugins.redis;

import com.sword.framework.common.cache.Cache;
import lombok.Getter;
import org.redisson.api.RedissonClient;

import java.time.Duration;

/**
 * @author shujian.ou
 * @since 2023/7/30 23:10
 */
@Getter
public class RedisCache implements Cache {

    private final RedissonClient redissonClient;

    public RedisCache(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public <V> V get(String key) {
        return (V) redissonClient.getBucket(key).get();
    }

    @Override
    public <V> void set(String key, V value) {
        redissonClient.getBucket(key).set(value);
    }

    @Override
    public void setExpire(String key, Duration expireTime) {
        redissonClient.getBucket(key).expire(expireTime);
    }

    @Override
    public long getExpire(String key) {
        return redissonClient.getBucket(key).remainTimeToLive();
    }

    @Override
    public void delete(String key) {
        redissonClient.getBucket(key).delete();
    }

    @Override
    public boolean exist(String key) {
        return redissonClient.getBucket(key).isExists();
    }

}

