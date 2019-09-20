package com.spring.start.api.user;

import com.spring.start.entity.User;
import com.spring.start.result.DataReulst;
import com.spring.start.service.UserService;
import com.spring.start.zookeeper.zklock.enums.LockPath;
import com.spring.start.zookeeper.zklock.impl.SharedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.concurrent.TimeUnit;

/**
 * Created by 50935 on 2019/8/23.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/insertUser")
    public DataReulst  insertUser(@Validated @NotEmpty String userName, String mobile, String password, Integer delFlag){
        log.info("我要添加用户信息了");
        User user = new User();
        user.setMobile(mobile);
        user.setPassword(password);
        user.setUserName(userName);
        user.setDelFlag(delFlag);
        userService.insert(user);
        log.info(user.getId()+"新添加用户ID");
        return DataReulst.Success(user.getId());
    }


    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = {"/detail/{id}", "/userInfo/{id}"})
    public DataReulst getUser(@Valid
                              @NotBlank
                              @Min(value=0,message = "用户Id必须大于0")
                              @Max(value=1000,message = "用户Id无效")
                              @PathVariable(value="id") Long id) throws Exception {

        SharedLock sharedLock =  new SharedLock();
        try {
           boolean b =  sharedLock.acquire(LockPath.test,5000, TimeUnit.MILLISECONDS);
           if(b){
              User user =  userService.getById(id);
              return DataReulst.Success(user);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                sharedLock.release(LockPath.test);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DataReulst.Success(0);
    }


    @RequestMapping(value = "/insert")
    public DataReulst  insert(@Validated User user){
        log.info("我要添加用户信息了");
        userService.insert(user);
        log.info(user.getId()+"新添加用户ID");
        return DataReulst.Success(user.getId());
    }

}
