package cn.net.wujun.springboot.demo.controller;

import cn.net.wujun.springboot.demo.entity.User;
import cn.net.wujun.springboot.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jasonwu on 2017/02/17.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        logger.debug("path id:{}", id);
        User user = userService.getById(id);
        return user;
    }
}
