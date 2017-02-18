package cn.net.wujun.springboot.demo;

import cn.net.wujun.springboot.demo.service.RoleService;
import cn.net.wujun.springboot.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;

/**
 * Unit test for simple App.
 *
 * Created by ${USER} on ${DATE}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MockBean
    RoleService roleService;

    @Autowired
    UserService userService;

    @Test
    public void hello() {

        given(this.roleService.role(anyLong())).willReturn("de");

        String result = userService.hello("addd");


        //user service
        //hello addd, rsult=null

        System.out.println(result);
        //Assert.assertSame("de", result);

        assertThat(result).isEqualTo("hello addd, rsult=de");
    }
}