package com.spring.start.zookeeper.zklock;

import org.apache.curator.framework.recipes.locks.InterProcessLock;

import java.util.concurrent.TimeUnit;

/**
 * Created by 50935 on 2019/9/19.
 */
public interface ZKLock {
    /**
     * 获取锁 - 根据接口子类推断出当前锁类型
     *
     * @see org.apache.curator.framework.recipes.locks.InterProcessMultiLock
     * @see org.apache.curator.framework.recipes.locks.InterProcessMutex
     * @see org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex
     * @param interProcessLock
     * @throws Exception
     */
    void lock(InterProcessLock interProcessLock) throws Exception;

    /**
     * 获取锁 - 阻止直到可用或给定的时间到期
     *
     * @param interProcessLock
     * @param milliseconds
     * @param timeUnit
     * @throws Exception
     */
    boolean lock(InterProcessLock interProcessLock, long milliseconds, TimeUnit timeUnit) throws Exception;


    /**
     * 释放锁 - 根据锁接口子类推断出当前锁类型
     *
     * @see org.apache.curator.framework.recipes.locks.InterProcessMultiLock
     * @see org.apache.curator.framework.recipes.locks.InterProcessMutex
     * @see org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex
     * @param interProcessLock
     * @throws Exception
     */
    void unlock(InterProcessLock interProcessLock) throws Exception;
}
