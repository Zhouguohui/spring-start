package com.spring.start.zookeeper.test_demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 50935 on 2019/9/17.
 */
@Slf4j
public class ZKConnect implements Watcher {


    public static final String serverPath = "192.168.171.131:2181,192.168.171.131:2182,192.168.171.131:2183";

    public static final int timeout = 5000;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, NoSuchAlgorithmException {
        ZooKeeper zooKeeper = new ZooKeeper(serverPath, timeout, new ZKConnect());

        long sessonid = zooKeeper.getSessionId();
        byte[] password = zooKeeper.getSessionPasswd();

        log.info("zookeeper 连接了");
        log.info("连接状态" + zooKeeper.getState());
        Thread.sleep(2000);
        log.info("连接状态" + zooKeeper.getState());


        List<ACL> acl = new ArrayList<>();

        Id test1 = new Id("digest", DigestAuthenticationProvider.generateDigest("zgh:123"));
        Id test2 = new Id("digest", DigestAuthenticationProvider.generateDigest("test:123"));

        acl.add(new ACL(ZooDefs.Perms.ALL,test1));
        acl.add(new ACL(ZooDefs.Perms.READ,test2));
        acl.add(new ACL(ZooDefs.Perms.CREATE | ZooDefs.Perms.DELETE,test2));

        zooKeeper.create("/test1","123".getBytes(),acl, CreateMode.EPHEMERAL);

        zooKeeper.addAuthInfo("digest","test:123".getBytes());
        zooKeeper.setData("/test1","456".getBytes(),0);

        //zooKeeper.create("/test","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

       /* byte[] b1=  zooKeeper.getData("/test",false, new Stat());
        System.out.println("1======="+new String(b1));


        byte[] b2= zooKeeper.getData("/test",new ZKConnect(), new Stat());
        System.out.println("2======="+new String(b2));

        Stat s = zooKeeper.setData("/test","456".getBytes(),0);
        log.info("setData====version====" + s.getVersion());

        byte[] b =  zooKeeper.getData("/test",false, new Stat());
        System.out.println(new String(b));

        zooKeeper.delete("/test",s.getVersion());

       Stat stat =  zooKeeper.exists("/test",false);*/
       /* Thread.sleep(5000);

        log.info("==============开始会话重连===============");
        ZooKeeper zoo = new ZooKeeper(serverPath, timeout, new ZKConnect(), sessonid, password);

        log.info("=====zoo   ===" + zoo.getState());
        Thread.sleep(5000);
        log.info("=====zoo   ===" + zoo.getState());*/

    }




    @Override
    public void process(WatchedEvent watchedEvent) {

        log.info("我触发事件了=====》》"+watchedEvent);

    }
}
