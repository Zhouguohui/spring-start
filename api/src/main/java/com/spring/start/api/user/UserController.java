package com.spring.start.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 50935 on 2019/8/23.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    /**
     * 首页，并将登录用户的全名返回前台
     * @param model
     * @return
     */
    @RequestMapping(value = {"/detail", "/userInfo"})
    public String userInfo(Model model) {
        log.info("我详情页面");
        return "userinfo";
    }
}
