package cn.net.wujun.springboot.mybatis.configure;

import cn.net.wujun.springboot.mybatis.interceptor.LogInterceptor;
import com.github.pagehelper.PageInterceptor;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wujun on 2017/02/28.
 *
 * @author wujun
 * @since 2017/02/28
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(PageHelperProperties.class)
@AutoConfigureAfter(org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration.class)
public class MybatisAutoConfiguration {

    protected static final Logger logger = LoggerFactory.getLogger(MybatisAutoConfiguration.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void addPageInterceptor() {
        logger.info("addPageInterceptor :{}", "init log interceptor");
        LogInterceptor interceptor = new LogInterceptor();
        sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
    }
}
