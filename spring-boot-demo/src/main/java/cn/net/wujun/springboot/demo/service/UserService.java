package cn.net.wujun.springboot.demo.service;

import cn.net.wujun.springboot.demo.entity.User;
import cn.net.wujun.springboot.demo.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> getTableData(int pageNum, int pageSize, String username) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<User> userList = userMapper.selectPage();
            int count = userMapper.count();
            Map<String, Object> tableData = new HashMap<>();
            tableData.put("list", userList);
            tableData.put("count", count);
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
