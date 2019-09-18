package com.spring.start.zookeeper.test_demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 50935 on 2019/9/17.
 */
@Slf4j
public class ZkCurator implements Watcher{

    private static final String connectionInfo = "192.168.171.131:2181,192.168.171.131:2182,192.168.171.131:2183";

    public static void main(String[] args) throws Exception {

        /**
         * 重试次数和时间限制
         * baseSleepTimeMs 初始sleep的时间
         * maxRetries  最大重试次数
         * maxSleepMs  最大重试间隔时间
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5,5000);

        CuratorFramework frameworkFactory =CuratorFrameworkFactory.builder()
                                        .connectString(connectionInfo)
                                        .sessionTimeoutMs(5000)
                                        .connectionTimeoutMs(3000)
                                        .namespace("spring-start")
                                        .retryPolicy(retryPolicy).build();
        frameworkFactory.start();

        CuratorFrameworkState state = frameworkFactory.getState();
        log.info("======="+state);

        Stat statCheck = frameworkFactory.checkExists().forPath("/super/test");
        if(Objects.isNull(statCheck)){
            log.info("节点信息存在===》》"+statCheck.getAversion());
        }else{
            log.info("节点信息不存在");
        }

        List<String> listChildren  = frameworkFactory.getChildren().forPath("/super");
        listChildren.forEach(System.out::print);


        final NodeCache nodeCache = new NodeCache(frameworkFactory,"/super/test");

        NodeCacheListener listener = new NodeCacheListener(){
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = nodeCache.getCurrentData();
                if(Objects.nonNull(childData)){
                    log.info("节点信息"+ new String(childData.getData()) + "==="+ childData.getPath());
                }else{
                    log.info("节点信息不存在");
                }
            }
        };
        nodeCache.getListenable().addListener(listener);
        nodeCache.start();


        final PathChildrenCache childrenCache = new PathChildrenCache(frameworkFactory,"/test",true);
        /**
         * NORMAL,   异步初始化
         * BUILD_INITIAL_CACHE,  同步初始化
         * POST_INITIALIZED_EVENT;  异步初始化  初始化之后会触发事件
         */
        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

        List<ChildData> childData = childrenCache.getCurrentData();
        childData.forEach(s->{
            System.out.println(new String(s.getData()));
        });

       PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener(){
           @Override
           public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
               if(event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)){
                    log.info("子节点初始化");
               }else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)){
                   log.info("子节点添加");
                   log.info("子节点数据"+new String(event.getData().getData()));
               }else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)){
                   log.info("子节点删除");
               }else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
                   log.info("子节点修改");
                   log.info("子节点数据"+new String(event.getData().getData()));
               }
           }
       };

        childrenCache.getListenable().addListener(childrenCacheListener);


        Stat stat = new Stat() ;
        frameworkFactory.getData()
                .storingStatIn(stat)
                //.usingWatcher(new ZkCurator())
                .forPath("/super/test");

        frameworkFactory.delete()
                .guaranteed()
                .deletingChildrenIfNeeded()
                .withVersion(stat.getVersion())
                .forPath("/super/test");

        frameworkFactory.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/super/test","456".getBytes());

        frameworkFactory.setData()
                .withVersion(0)
                .forPath("/super/test","789".getBytes());

        List<CuratorOp> list = new ArrayList<>();
        CuratorOp create = frameworkFactory.transactionOp()
                .create().forPath("/aa","123".getBytes());

        list.add(create);
        CuratorOp check = frameworkFactory.transactionOp()
                .check().forPath("/aa");
        list.add(check);
        CuratorOp setData = frameworkFactory.transactionOp()
                .setData().forPath("/aa","456".getBytes());
        list.add(setData);
        CuratorOp delete = frameworkFactory.transactionOp()
                .delete().forPath("/aa");
        list.add(delete);
       /* CuratorOp create1 = frameworkFactory.transactionOp()
                .create().forPath("/aa");
        list.add(create1);*/
        List<CuratorTransactionResult> result = frameworkFactory.transaction().forOperations(list);
        result.forEach(s->{
            System.out.println(s.getForPath() + "==resultStat=="+s.getResultStat()+"==resultPath=="+s.getResultPath()+"==type=="+s.getType());
        });

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("---------->"+watchedEvent.getType().name());
    }
}
