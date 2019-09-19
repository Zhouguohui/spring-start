package com.spring.start.zookeeper.zklock.impl;

import com.spring.start.zookeeper.zklock.AbstractZookeeperLock;
import com.spring.start.zookeeper.zklock.enums.LockPath;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

import java.util.concurrent.TimeUnit;

/**分布式排它锁
 * Created by 50935 on 2019/9/19.
 */
public class SharedLock extends AbstractZookeeperLock<InterProcessSemaphoreMutex> {

    /**
     * Shared Lock
     *
     * 获取排他锁，阻塞直到可用，单实例{@code InterProcessSemaphoreMutex} <br>
     * interProcessMutex {@link InterProcessSemaphoreMutex#InterProcessSemaphoreMutex} : <ul>
     * <li>参数1: zk连接上下文{@link AbstractZookeeperLock#curatorFramework} </li>
     * <li>参数2: zk锁路径，路径相同锁相同 </li>
     * </ul>
     *
     * @param lockPath
     * @throws Exception 抛出异常时，锁不在持有，获取锁失败，谨慎处理业务
     */
    public void acquire(LockPath lockPath){
        try {
            super.lock(getSharedLock(lockPath));
        } catch (Exception e) {
            throw new IllegalStateException("获取共享锁失败=>" + e);
        }
    }

    /**
     * Shared Lock
     *
     * @param lockPath
     * @param time  获取互斥锁：阻止直到可用或给定的时间到期
     * @return  如果获得了互斥锁，则返回true，否则返回false
     * @throws Exception    抛出异常时，锁不在持有，获取锁失败，谨慎处理业务
     */
    public boolean acquire(LockPath lockPath, long time, TimeUnit timeUnit) throws Exception {
        try {
            return super.lock(getSharedLock(lockPath),time,timeUnit);
        } catch (Exception e) {
            throw new IllegalStateException("获取共享锁失败=>" + e);
        }
    }

    private InterProcessSemaphoreMutex getSharedLock(LockPath lockPath){
        String key = lockPath.getValue();
        InterProcessSemaphoreMutex interProcessSemaphoreMutex = null;
        if (locks.containsKey(key)){
            interProcessSemaphoreMutex = locks.get(key);
        }else {
            interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(curatorFramework,lockPath.getValue());
            locks.put(key,interProcessSemaphoreMutex);
        }
        return interProcessSemaphoreMutex;
    }

    public void release(LockPath lockPath) throws Exception {
        super.unlock(locks.get(lockPath.getValue()));
    }
}