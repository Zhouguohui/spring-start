package com.spring.start.api.user;

import com.spring.start.entity.User;
import com.spring.start.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 50935 on 2019/8/23.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 首页，并将登录用户的全名返回前台
     * @param id
     * @return
     */
    @RequestMapping(value = {"/detail/{id}", "/userInfo/{id}"})
    public String userInfo(@PathVariable(value="id") Long id) {
        User user  = userService.getById(id);
        log.info(user.toString());
        return "userinfo";
    }

}
