package cn.net.wujun.springboot.demo.dao;

import cn.net.wujun.springboot.demo.cn.net.wujun.springboot.test.BaseSpringBootTest;
import cn.net.wujun.springboot.demo.entity.User;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Jason Wu on 2017/02/25.
 */
public class UserMapperTest extends BaseSpringBootTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMapper userMapper;

    @Test
    public void insert() throws Exception {
        User user = new User();
        user.setName("测试");
        user.setGmtCreate(new Date());
        user.setSex((byte) 0x01);
        int coount = userMapper.insert(user);
        logger.debug("insert {}", coount);
        Assert.assertEquals(1, coount);
    }

    @Test
    public void update() throws Exception {
        User user = userMapper.getById(1002L);
        user.setName("测试修改");
        int count = userMapper.update(user);
        logger.debug("{}", count);
        Assert.assertEquals(1, count);
    }

    @Test
    public void delete() throws Exception {
        int count = userMapper.delete(1000L);
        logger.debug("{}", count);
        Assert.assertEquals(1, count);
    }

    @Test
    public void getById() throws Exception {
        User user = userMapper.getById(1003L);
        logger.debug("getById {}", JSON.toJSONString(user));
        assertNotNull(user);
    }

    @Test
    public void selectPage() throws Exception {
        List<User> users = userMapper.selectPage();
        logger.debug("{}", JSON.toJSONString(users));
    }

    @Test
    public void count() throws Exception {
        int count = userMapper.count();
        logger.debug("count {}", count);
    }

}