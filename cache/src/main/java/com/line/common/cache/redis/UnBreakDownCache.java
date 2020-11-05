package com.line.common.cache.redis;

import com.line.common.cache.interf.CacheManager;
import com.line.common.cache.interf.ITTLCache;
import com.line.common.cache.interf.ITTLCacheProvider;
import com.line.common.cache.redis.constant.NullObject;
import com.line.common.cache.redis.exception.KeyIsNotFoundException;
import com.line.common.cache.redis.exception.ValueIsBlankException;
import com.line.common.cache.redis.exception.ValueIsNullException;
import com.line.common.cache.redis.storage.RedisCacheStorage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author: yangcs
 * @Date: 2020/11/4 15:39
 * @Description: 本缓存接口使用布隆过滤器处理发生击穿的情况
 */
public abstract class UnBreakDownCache<V> implements ITTLCache<String, V>, InitializingBean, DisposableBean {

    /**
     * 日志类
     */
    private static final Log LOG = LogFactory.getLog(UnBreakDownCache.class);

    //过期时间
    private int timeOut = 10 * 60;

    /**
     * 数据提供者
     */
    protected ITTLCacheProvider<V> cacheProvider;

    /**
     * 数据存储器
     */
    protected RedisCacheStorage<String, V> cacheStorage;


    /**
     * 获取数据
     * 如果返回null就是真的没有数据，无需再去数据库再取
     *
     * @param key
     * @return
     */
    public V get(String key) {
        if (StringUtils.isEmpty(key)) {
            LOG.warn("缓存[" + getUUID() + "]，key为空串，返回结果[null]");
            //key存在，value为空串
            return null;
        }

        V value = null;
        try {
            value = cacheStorage.get(getKey(key));
            //如果key不存在，或者key对应的value不存在的情况，说明是正好过期的时候导致的，再查一下
            if (value == null) {
                value = cacheProvider.get(key);
                LOG.warn("缓存[" + getUUID() + "]，key[" + key + "]过期，重新走数据库查询，返回结果[" + value + "]");
                if (value == null) {
                    this.setNullObject(key);
                } else {
                    cacheStorage.set(getKey(key), value, timeOut);
                }
            }
        } catch (ValueIsBlankException e) {
            LOG.warn("缓存[" + getUUID() + "]，key[" + key + "]存在，value为空串，返回结果[null]");
            //key存在，value为空串
            return null;
        } catch (ValueIsNullException ex) {
            //key存在，value为null
            LOG.warn("缓存[" + getUUID() + "]，key[" + key + "]存在，value为null，返回结果[null]");
            return null;
        } catch (KeyIsNotFoundException ex1) {
            //key不存在
            value = cacheProvider.get(key);
            LOG.warn("缓存[" + getUUID() + "]，key[" + key + "]不存在，走数据库查询，返回结果[" + value + "]");
            if (value == null) {
                this.setNullObject(key);
            } else {
                cacheStorage.set(getKey(key), value, timeOut);
            }
        } catch (RedisConnectionFailureException exx) {
            //redis 连接出现异常
            value = cacheProvider.get(key);
            LOG.warn("redis连接出现异常，走数据库查询!");
            LOG.error(exx);
            return value;
        } catch (Exception e) {
            //其他异常
            LOG.error(e);
            value = cacheProvider.get(key);
            return value;
        }
        return value;
    }


    /**
     * 数据层没有查询到数据,被击穿了,这里使用一个表示空的对象放在缓存中抵挡击穿;
     * 但这里有两个问题;
     * 1: 无端消耗了redis的内存空间,
     * 解决方案:这里可以修改这个对象的生存时间,稍微短一点,不要像真实有效的数据一样时长
     * 2: 如果是被攻击,使用大量的不存在的key,依然会大量击穿;
     * 解决方案: 布隆过滤器拦截;
     */
    private void setNullObject(String key) {
        cacheStorage.set(getKey(key), new NullObject(), timeOut);
    }

    ;


    //对象销毁, 删除cacheManage 中注册的缓存类信息
    public void destroy() throws Exception {
        CacheManager.getInstance().unRegisterCache(getUUID());
    }

    //对象创建, cacheManage中注册本缓存类信息
    public void afterPropertiesSet() throws Exception {
        CacheManager.getInstance().registerCache(this);
    }


    /**
     * 失效数据
     */
    public void invalid(String key) {
        cacheStorage.remove(getKey(key));
    }

    /**
     * 失效一组数据
     */
    public void invalidMulti(String... keys) {
        if (keys == null) return;
        String[] skeys = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            skeys[i] = getKey(keys[i]);
        }
        cacheStorage.removeMulti(skeys);
    }


    /**
     * 设置超时时间
     *
     * @param seconds
     * @see
     */
    @Override
    public void setTimeOut(int seconds) {
        timeOut = seconds;
    }


    /**
     * 根据uuid和key生成key
     *
     * @param key
     * @return
     * @see
     */
    protected String getKey(String key) {
        return getUUID() + "_" + key;
    }


    /**
     * 设置数据提供者
     *
     * @param cacheProvider
     * @see
     */
    public void setCacheProvider(ITTLCacheProvider<V> cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    /**
     * 设置数据存储者
     *
     * @param cacheStorage
     * @see
     */
    public void setCacheStorage(RedisCacheStorage<String, V> cacheStorage) {
        this.cacheStorage = cacheStorage;
    }


    /**
     * TTL缓存不支持取出全部
     */
    public Map<String, V> get() {
        throw new UnsupportedOperationException(getUUID() + ":TTLCache cannot get all!");
    }

    /**
     * TTL缓存不支持失效全部
     */
    public void invalid() {
        throw new UnsupportedOperationException(getUUID() + ":TTLCache cannot invalid all!");
    }

}











