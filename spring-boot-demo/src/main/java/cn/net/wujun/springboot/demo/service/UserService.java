package cn.net.wujun.springboot.demo.service;

import cn.net.wujun.springboot.demo.entity.User;
import cn.net.wujun.springboot.demo.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jasonwu on 2017/02/17.
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;

    public User getById(Long id) {
        return userMapper.getById(id);
    }

    public String hello(String test) {
        System.out.println("user service");
        String result = roleService.role(1L);
        System.out.println("result=" + result);
        return "hello " + test + ", rsult=" + result;
    }
}
