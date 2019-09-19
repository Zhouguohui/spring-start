package com.spring.start.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.DeleteBuilder;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Created by 50935 on 2019/9/18.
 */
@Slf4j
@Component
public class ZkUtils {

    @Autowired
    private CuratorFramework client;

    /**
     * zk关闭
     */
    public void close(){
        client.close();
    }

    /**
     * 校验节点是否存在
     * @param path
     * @return
     * @throws Exception
     */
    public boolean existsState (String path) throws Exception {
        Stat stat= getData(path);
        if(Objects.isNull(stat)){
            return false;
        }
        return true;
    }


    /**
     * 获取数据
     * @param path
     * @return
     * @throws Exception
     */
    public Stat getData(String path) throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        return stat;
    }

    /**
     * 删除节点
     * @param path  路径
     * @param version  数据版本
     * @param isNeeded  是否递归删除
     * @throws Exception
     */
    public void deleteData(String path,int version,boolean isNeeded) throws Exception {
        DeleteBuilder deleteBuilder = client.delete();
            deleteBuilder
                    .guaranteed()
                    .withVersion(version)
                    .forPath(path);
            if(isNeeded){
                deleteBuilder.deletingChildrenIfNeeded();
            }
    }

    /**
     * 创建节点
     * @param path  节点路径
     * @param data  节点数据  如果只创建节点该字段为null
     * @param createMode  节点类型  如果使用默认类型 该字段为null
     * @param acl   节点权限  如果使用默认权限类型  该字段为null
     * @param isNeeded 是否递归创建  true代表是   false代表否
     * @return
     * @throws Exception
     */
    public Stat creatData(String path, String data, CreateMode createMode, List<ACL> acl,boolean isNeeded) throws Exception {
        Stat stat = new Stat();
        CreateBuilder createBuilder = client.create();
                createBuilder.storingStatIn(stat);
              if(isNeeded){
                  createBuilder.creatingParentsIfNeeded();
              }
              if(Objects.nonNull(createMode)){
                  createBuilder.withMode(createMode);
              }
              if(Objects.isNull(acl)){
                  createBuilder.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE);
              }else{
                  createBuilder.withACL(acl);
              }
              if(Objects.isNull(data)){
                  createBuilder.forPath(path);
              }else{
                  createBuilder.forPath(path,data.getBytes());
              }
        return stat;
    }

    /**
     * 修改节点
     * @param path
     * @param data
     * @param version
     * @return
     */
    public Stat setData(String path, String data,int version) throws Exception {
        Stat stat =  client.setData()
                .withVersion(version)
                .forPath(path,data.getBytes());
        return stat;
    }


    /**
     * 创建修改节点
     * @param path  节点路径
     * @param data  节点数据  如果只创建节点该字段为null
     * @param createMode  节点类型  如果使用默认类型 该字段为null
     * @param acl   节点权限  如果使用默认权限类型  该字段为null
     * @param isNeeded 是否递归创建  true代表是   false代表否
     * @param iscreate 节点不存在是否创建  true代表是   false代表否
     * @param version 修改数据版本 如果isCreate为false 改字段为null
     * @return
     * @throws Exception
     */
    public Stat setCreateData(String path, String data, CreateMode createMode, List<ACL> acl,boolean isNeeded,int version,boolean iscreate) throws Exception {

        if(iscreate && !existsState(path)){
           return  creatData(path,data,createMode,acl,isNeeded);
        }

        Stat stat =  client.setData()
                .withVersion(version)
                .forPath(path,data.getBytes());

        return stat;

    }

    /**
     * 节点事件添加
     * @param path
     * @throws Exception
     */
    public void watcherAddNode(String path) throws Exception {
        final NodeCache nodeCache = new NodeCache(client,path,true);
        NodeCacheListener listener = ()->{
            ChildData childData = nodeCache.getCurrentData();
        };

        nodeCache.getListenable().addListener(listener);
        nodeCache.start();
    }


    /**
     * 节点事件删除
     * @param path
     */
    public void watcherRemove(String path){
        final NodeCache nodeCache = new NodeCache(client,path);
        NodeCacheListener listener = ()->{
            ChildData childData = nodeCache.getCurrentData();
        };
        nodeCache.getListenable().removeListener(listener);
    }


    /**
     * 子节点事件监听
     * @param path
     * @throws Exception
     */
    public void watcherAddchildNode(String path) throws Exception {
        final PathChildrenCache childrenCache = new PathChildrenCache(client,path,true);

        PathChildrenCacheListener listener = (client1,event)->{
            if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                String oldPath = event.getData().getPath();
                log.info("上一个节点 "+ oldPath + " 已经被断开");
            }
        };

        childrenCache.getListenable().addListener(listener);
        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
    }

    /**
     * 分布式锁
     * @param path
     */
    public void  interProcessMutex(String path){
        InterProcessMutex lock = new InterProcessMutex(client, path);

    }




}
