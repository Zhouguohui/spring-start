package com.spring.start.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * Created by 50935 on 2019/8/23.
 * 实现认证
 */
@Slf4j
public class ShiroRealmTwo extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken =(UsernamePasswordToken)token;
        String userName = userToken.getUsername();

        log.info("-------------ShiroRealmTwo------------");
        log.info("从数据库获取userName:"+userName + "多对应的用户信息");

        if("zgh".equals(userName)){
            throw  new LockedAccountException("用户被锁定");
        }
        //principal 认证的实体信息  可以是userName 也可以是数据表对应的用户的实体类对象
        Object principal = userName;
        //密码credentials 为密码
        Object credentials = "123456";
        if("admin".equals(userName)){
            credentials = "cd5ea73cd58f827fa78eef7197b8ee606c99b2e6";
        }else if("user".equals(userName)){
            credentials = "99e7a456385b481f25e1451868a3a584d4200d17";
        }
        //credentialsSalt 盐值  保证每个用户的密码即使一样  数据库存储的密码也是不一样
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName);
        //realmName 当前对象的realm对象的name 调用父类的getName()方法即可
        String realmName = getName();
        SimpleAuthenticationInfo info  = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
        return info;
    }


    /**
     * e10adc3949ba59abbe56e057f20f883e
     * @param args
     */
    public static  void main(String[] args){
        SimpleHash simpleHash =  new SimpleHash("SHA1","123456","admin",1);
        System.out.println(simpleHash.toString());

    }
}
