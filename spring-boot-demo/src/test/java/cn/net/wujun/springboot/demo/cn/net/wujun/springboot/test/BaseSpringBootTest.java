package cn.net.wujun.springboot.demo.cn.net.wujun.springboot.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wujun on 2017/02/25.
 *
 * @author wujun
 * @since 2017/02/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BaseSpringBootTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
