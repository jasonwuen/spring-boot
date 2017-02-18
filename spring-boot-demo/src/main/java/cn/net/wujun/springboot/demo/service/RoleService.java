package cn.net.wujun.springboot.demo.service;

import org.springframework.stereotype.Service;

/**
 * Created by jasonwu on 2017/02/17.
 */
@Service
public class RoleService {

    public String role(Long id) {
        System.out.println("role");
        return "resutl id =" + id;
    }
}
