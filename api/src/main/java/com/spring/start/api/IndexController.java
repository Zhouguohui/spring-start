package com.spring.start.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 50935 on 2019/8/23.
 */
@Controller
@Slf4j
public class IndexController {

    /**
     * 首页，并将登录用户的全名返回前台
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            try {
                System.out.println("1. " + token.hashCode());
                // 执行登录.
                subject.login(token);
            } catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + ae.getMessage());
                return "login";
            }

        }
        return "userinfo";
    }
    /**
     * 首页，并将登录用户的全名返回前台
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "login";
    }

    /**
     * 首页，并将登录用户的全名返回前台
     * @return
     */
    @RequestMapping("/unauth")
    public String unauth() {
        return "unauth";
    }

    /**
     * 首页，并将登录用户的全名返回前台
     * @return
     */
    @RequestMapping("/users")
    public String users() {
        return "user";
    }

    /**
     * 首页，并将登录用户的全名返回前台
     * @return
     */
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
}
