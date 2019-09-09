package com.spring.start.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 50935 on 2019/8/23.
 */
@Configuration
@Slf4j
public class ShiroConfig {

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcherOne() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcherTwo() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA1");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    /**
     * 认证器
     * @return
     */
    @Bean
    public ShiroRealmOne shiroRealmOne(){
        ShiroRealmOne shiroRealmOne = new ShiroRealmOne();
        shiroRealmOne.setCredentialsMatcher(hashedCredentialsMatcherOne());
        return shiroRealmOne;
    }


    /**
     * 认证器
     * @return
     */
    @Bean
    public ShiroRealmTwo shiroRealmTwo(){
        ShiroRealmTwo shiroRealmTwo = new ShiroRealmTwo();
        shiroRealmTwo.setCredentialsMatcher(hashedCredentialsMatcherTwo());
        return shiroRealmTwo;
    }


    /**
     * 认证和授权
     * @return
     */
    //@Bean
    public UserRealmOne userRealmOne(){
        UserRealmOne userRealmOne = new UserRealmOne();
        userRealmOne.setCredentialsMatcher(hashedCredentialsMatcherOne());
        return userRealmOne;
    }

    /**
     * 安全管理器
     * 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManager会报错，直接引用shiro-spring则不报错
     *
     * @return
     */
    //@Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(shiroRealmOne());
        //配置多个realm 方式1
        //securityManager.setAuthenticator(authenticator());
        //配置多realm 方式2
        //为什么要使用第二种  因为在系统授权的时间需要从securityManager读取数据
       /* List<Realm> l = new ArrayList<Realm>(){{
            add(shiroRealmOne()); add(shiroRealmTwo());
        }};
        securityManager.setRealms(l);*/

       securityManager.setRealm(userRealmOne());
        return securityManager;
    }


    //@Bean
    public Authenticator authenticator(){
        //使用ModularRealmAuthenticator 来实现多realm的配置
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        List<Realm> l = new ArrayList<Realm>(){{
            add(shiroRealmOne()); add(shiroRealmTwo());
        }};
        /**
         FirstSuccessfulStrategy：只要有一个Realm验证成功即可，只返回第一个Realm身份验证成功的认证信息，其他的忽略；
         AtLeastOneSuccessfulStrategy：只要有一个Realm验证成功即可，和FirstSuccessfulStrategy不同，返回所有Realm身份验证成功的认证信息；
         AllSuccessfulStrategy：所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息，如果有一个失败就失败了。
         */
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        modularRealmAuthenticator.setRealms(l);
        return modularRealmAuthenticator;
    }

    /**
     * 设置过滤规则
     *
     * @param securityManager
     * @return
     */
   // @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        log.info("shiro 过滤器开始了");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/index");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");


        /**DefaultFilter   拦截权限有一下这么多
         * anon(AnonymousFilter.class),  匿名拦截器   不需要登录比如静态资源加载
         authc(FormAuthenticationFilter.class),       基于表单的认证拦截器   如果没有登录不允许访问
         authcBasic(BasicHttpAuthenticationFilter.class),     Basic  HTTP身份验证拦截器
         logout(LogoutFilter.class),          退出拦截器
         noSessionCreation(NoSessionCreationFilter.class),
         perms(PermissionsAuthorizationFilter.class),
         port(PortFilter.class),
         rest(HttpMethodPermissionFilter.class),
         roles(RolesAuthorizationFilter.class),          角色拦截器
         ssl(SslFilter.class),
         user(UserFilter.class);       用户拦截器
         */

        //注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
        //所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了。
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/*", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/users","roles[user]");
        filterChainDefinitionMap.put("/admin","roles[admin]");
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}
