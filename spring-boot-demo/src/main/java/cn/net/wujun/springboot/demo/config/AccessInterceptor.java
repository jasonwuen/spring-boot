package cn.net.wujun.springboot.demo.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by wujun on 2017/02/18.
 */
@Aspect
@Component
@Order(100)
public class AccessInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    //申明一个切点 里面是 execution表达式
    @Pointcut("execution(public * cn.net.wujun.springboot.demo.controller..*.*(..))")
    private void controllerPointcut() {
        logger.info(JSON.toJSONString(request.getParameterMap()));
    }

    /**
     * 接收到请求，记录请求内容.
     *
     * @param joinPoint
     */
    @Before(value = "controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        /*StringBuilder builder = new StringBuilder();
        builder.append("\n===============请求内容===============");
        builder.append("\n请求地址:" + request.getRequestURL().toString());
        builder.append("\n请求方式:" + request.getMethod());
        builder.append("\n请求类方法:" + joinPoint.getSignature());
        builder.append("\n请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        builder.append("\n===============请求内容===============");*/

        StringBuilder builder = new StringBuilder("\ncontroller report -------- ")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .append(" ------------------------------\n");

        builder.append("Controller  : ").append(joinPoint.getTarget().getClass().getName())
                .append(".java");
        builder.append("\nMethod      : ").append(joinPoint.getSignature().getName()).append("\n");

        String uri = request.getRequestURI();
        if (uri != null) {
            builder.append("url         : ").append(uri).append("\n");
        }

        Enumeration<String> e = request.getParameterNames();
        if (e.hasMoreElements()) {
            builder.append("Parameter   : ");
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String[] values = request.getParameterValues(name);
                if (values.length == 1) {
                    builder.append(name).append("=").append(values[0]);
                } else {
                    builder.append(name).append("[]={");
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0)
                            builder.append(",");
                        builder.append(values[i]);
                    }
                    builder.append("}");
                }
                builder.append("  ");
            }
            builder.append("\n");
        }
        builder.append
                ("--------------------------------------------------------------------------------\n");

        logger.info(builder.toString());
    }


    //在方法执行完结后打印返回内容

    /**
     * 处理完请求，返回内容.
     *
     * @param returnValue
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, Object returnValue) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n--------------返回内容----------------");
        builder.append("\nreturn value is " + JSON.toJSONString(returnValue));
        builder.append("\n--------------返回内容----------------");

        logger.info(builder.toString());
    }
}
