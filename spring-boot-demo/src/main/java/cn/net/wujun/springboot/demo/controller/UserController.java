package cn.net.wujun.springboot.demo.controller;

import cn.net.wujun.springboot.demo.entity.User;
import cn.net.wujun.springboot.demo.service.UserService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jasonwu on 2017/02/17.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AtomicLong atomicId = new AtomicLong(0);

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        logger.debug("path id:{}", id);
        User user = userService.getById(id);
        return user;
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        logger.debug("save user:{}", JSON.toJSONString(user));
        user.setId(atomicId.incrementAndGet());
        return user;
    }

    @GetMapping("/list")
    public Map<String, Object> getTableData(int pageNum, int pageSize, String username) {
        return userService.getTableData(pageNum, pageSize, username);
    }

}
