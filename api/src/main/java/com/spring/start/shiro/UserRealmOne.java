package com.spring.start.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 50935 on 2019/8/23.
 * 实现授权和认证
 */
@Slf4j
@SuppressWarnings("all")
public class UserRealmOne extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        Object p =token.getPrimaryPrincipal();
        Set<String> s = new HashSet<>();
        /*s.add("user");
        if("admin".equals(p)){
            s.add("admin");
        }*/
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(s);
        return simpleAuthorizationInfo;
    }

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
}
