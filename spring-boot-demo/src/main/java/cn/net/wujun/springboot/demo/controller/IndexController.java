package cn.net.wujun.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Jason Wu on 2017/02/25.
 */
@Controller
public class IndexController {


    @GetMapping("/")
    String index() {
        //System.out.println("Hello World!");
        return "index";
    }

    @GetMapping("/user/info")
    String userInfo() {
        return "/user/info";
    }


}
