package com.spring.start.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 如果只需要认证 继承AuthenticatingRealm 重写doGetAuthenticationInfo方法即可
 * Created by 50935 on 2019/8/23.
 * 实现授权和认证
 */
@Slf4j
public class UserRealmTwo extends AuthorizingRealm {

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermissions(sysPermissions);
        log.info("-----------------doGetAuthorizationInfo");
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("---------------doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Object userName = "zgh";
        Object password = "";
        ByteSource byteSource = ByteSource.Util.bytes("");
        String realmName = getName();

        return new SimpleAuthenticationInfo(userName, password, byteSource, realmName);
    }
}
