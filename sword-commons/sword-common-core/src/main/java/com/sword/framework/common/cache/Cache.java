package com.sword.framework.common.cache;


import java.time.Duration;

/**
 * 缓存接口
 *
 * @author shujian.ou
 * @since 2023/7/30 22:46
 */
public interface Cache {

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @return 缓存值
     */
    <V> V get(String key);

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    <V> void set(String key, V value);

    /**
     * 设置缓存过期时间
     *
     * @param key        缓存key
     * @param expireTime 过期时间
     */
    void setExpire(String key, Duration expireTime);

    /**
     * 获取缓存过期时间
     *
     * @param key 缓存key
     * @return 过期时间
     */
    long getExpire(String key);

    /**
     * 删除缓存
     *
     * @param key 缓存key
     */
    void delete(String key);

    /**
     * 是否存在缓存
     *
     * @param key 缓存key
     * @return 是否存在
     */
    boolean exist(String key);


}
