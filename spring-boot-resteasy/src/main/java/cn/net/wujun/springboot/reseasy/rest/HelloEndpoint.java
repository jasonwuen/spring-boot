package cn.net.wujun.springboot.reseasy.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by wujun on 2017/02/21.
 *
 * @author wujun
 * @since 2017/02/21
 */
@Component
@Path("/hello")
public class HelloEndpoint {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private String msg;

    @GET
    public String message() {
        logger.debug("Hello {}", msg);
        return "Hello " + msg;
    }

}
