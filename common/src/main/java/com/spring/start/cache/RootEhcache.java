package com.spring.start.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by 50935 on 2019/10/30.
 */
@Component
public abstract class RootEhcache<K,V> implements IBaseCache {
    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    @PostConstruct
    private void init() {
        cache = cacheManager.getCache("topInit");
    }

    /**
     * 添加缓存
     *
     * @param k
     * @param v
     */
    public void inElement(K k, V v)
    {
        cache.put(new Element(k, v));
    }

    /**
     * 判断是否存在
     *
     * @param k
     * @return
     */
    public boolean containsKey(K k) {
        return cache.isKeyInCache(k);
    }

    /**
     * 获取所有缓存的Key
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<K> upKeys() {
        return cache.getKeys();
    }

    /**
     * 获取一个 默认返回null <br>
     * 客户端如果继承该类需要自行封装业务逻辑 该方法通用用于客户端覆盖 延迟加载判断<br>
     * 通常重写该方法以支撑返回缓存信息的情况
     *
     * @param k
     * @return
     */
    public abstract V upOne(K k);

    /**
     * 获取缓存的值
     *
     * @param k
     * @return
     */
    @SuppressWarnings("unchecked")
    public V upValue(K k) {

        if (containsKey(k)) {
            return (V) cache.get(k).getObjectValue();
        }

        return null;
    }

    @Override
    public void removeAll() {
        cache.removeAll();
    }

    public void removeByKey(K k) {
        synchronized (this) {
            cache.remove(k);
        }
    }
}
