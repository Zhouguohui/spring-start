package com.spring.start.zookeeper.zklock.impl;

import com.spring.start.zookeeper.zklock.AbstractZookeeperLock;
import com.spring.start.zookeeper.zklock.enums.LockPath;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * 分布式重入共享锁
 * Created by 50935 on 2019/9/19.
 */
public class SharedReentrantLock extends AbstractZookeeperLock<InterProcessMutex> {

    /**
     * Shared Reentrant Lock
     *
     * 获取可重入锁，阻塞直到可用，单实例{@code InterProcessMutex}获得几次锁需释放几次 <br>
     * interProcessMutex {@link InterProcessMutex#InterProcessMutex} : <ul>
     * <li>参数1: zk连接上下文{@link AbstractZookeeperLock#curatorFramework} </li>
     * <li>参数2: zk锁路径，路径相同锁相同 </li>
     * </ul>
     *
     * <b>注意：同一个线程可以重新调用获取。每次调用获取必须通过调用{@link SharedReentrantLock#(String)}释放</b>
     * @param lockPath
     * @throws Exception    抛出异常时，锁不在持有，获取锁失败，谨慎处理业务
     */
    public void acquire(LockPath lockPath){
        try {
            super.lock(getReentrantLock(lockPath));
        } catch (Exception e) {
            throw new IllegalStateException("获取重入共享锁失败=>" + e);
        }
    }

    /**
     * Shared Reentrant Lock
     * @param lockPath
     * @param time  获取互斥锁：阻止直到可用或给定的时间到期
     * @return  如果获得了互斥锁，则返回true，否则返回false
     * @throws Exception    抛出异常时，锁不在持有，获取锁失败，谨慎处理业务
     */
    public boolean acquire(LockPath lockPath, long time, TimeUnit timeUnit) throws Exception {
        try {
            return super.lock(getReentrantLock(lockPath),time,timeUnit);
        } catch (Exception e) {
            throw new IllegalStateException("获取重入共享锁失败=>" + e);
        }
    }

    private InterProcessMutex getReentrantLock(LockPath lockPath){
        String key =  lockPath.getValue();
        InterProcessMutex interProcessMutex = null;
        if (locks.containsKey(key)){
            interProcessMutex = locks.get(key);
        } else {
            interProcessMutex = new InterProcessMutex(curatorFramework,lockPath.getValue());
            locks.put(key,interProcessMutex);
        }
        return interProcessMutex;
    }

    public void release(LockPath lockPath) throws Exception {
        super.unlock(locks.get(lockPath.getValue()));
    }
}
