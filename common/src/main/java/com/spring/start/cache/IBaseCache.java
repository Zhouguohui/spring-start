package com.spring.start.cache;

/**
 * Created by 50935 on 2019/10/30.
 */
public interface IBaseCache {
    /**
     * 刷新缓存 该方法一向定义为synchronized
     */
    public void refresh() throws Exception;



    /**
     * 删除所有缓存
     */
    public void removeAll();
}
