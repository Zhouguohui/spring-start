package com.spring.start.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * 如果只需要认证和授权 AuthorizingRealm 重写doGetAuthenticationInfo  doGetAuthorizationInfo方法即可实现认证和授权
 * 如果只需要认证 继承AuthenticatingRealm 重写doGetAuthenticationInfo方法即可实现认证
 * Created by 50935 on 2019/8/23.
 * 实现认证
 */
@Slf4j
public class ShiroRealmOne extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken =(UsernamePasswordToken)token;
        String userName = userToken.getUsername();
        log.info("-------------ShiroRealmOne------------");
        log.info("从数据库获取userName:"+userName + "多对应的用户信息");

        if("zgh".equals(userName)){
            throw  new LockedAccountException("用户被锁定");
        }
        //principal 认证的实体信息  可以是userName 也可以是数据表对应的用户的实体类对象
        Object principal = userName;
        //密码credentials 为密码
        Object credentials = "123456";
        if("admin".equals(userName)){
            credentials = "a66abb5684c45962d887564f08346e8d";
        }else if("user".equals(userName)){
            credentials = "4da49c16db42ca04538d629ef0533fe8";
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
        SimpleHash simpleHash =  new SimpleHash("MD5","123456","user",1);
        System.out.println(simpleHash.toString());

    }


}
