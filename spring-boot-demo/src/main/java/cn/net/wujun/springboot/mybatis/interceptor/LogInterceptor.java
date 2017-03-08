package cn.net.wujun.springboot.mybatis.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by wujun on 2017/02/28.
 *
 * @author wujun
 * @since 2017/02/28
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement
                        .class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement
                        .class, Object.class, RowBounds.class, ResultHandler.class, CacheKey
                        .class, BoundSql.class}),
        }
)
public class LogInterceptor implements Interceptor {

    protected static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();

        logger.debug("parameterObject :{}", boundSql.getParameterObject().getClass());

        logger.info("parameterObject :{}", JSON.toJSONString(boundSql.getParameterObject()));

        /*List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        logger.warn("showSql :{}", JSON.toJSONString(parameterMappings));

        //boundSql.setAdditionalParameter();

        for (ParameterMapping parameterMapping : parameterMappings) {
            logger.info("1211221 :{}", boundSql.hasAdditionalParameter("comId"));
            Object comId = boundSql.getAdditionalParameter("comId");
            logger.info("intercept11111 :{}", JSON.toJSONString(comId));
            if (parameterMapping.getProperty().equals("comId")) {
                logger.warn("comId parameter mapping33333 :{}", JSON.toJSONString(parameterMapping));
                boundSql.setAdditionalParameter("comId", 1000L);
                //logger.debug("getAdditionalParameter :{}", JSON.toJSONString(boundSql.getAdditionalParameter("comId")));
                //logger.warn(" :{}", parameterMapping.);
            }
        }*/

        /*ParameterMapping comIdParameterMapping = buildParameterMapping(Long.class, "comId",
                configuration);
        parameterMappings.add(comIdParameterMapping);
        boundSql.setAdditionalParameter("comId", new Long(1000L));*/

        Object returnValue = null;
        long start = System.currentTimeMillis();
        returnValue = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = (end - start);
        if (time > 1) {
            String sql = getSql(configuration, boundSql, sqlId, time);
            System.err.println(sql);
        }
        return returnValue;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId,
                                long time) {

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        logger.info("getSql :{}", JSON.toJSONString(boundSql));

        //boundSql.setAdditionalParameter("comId", 1000L);

        logger.info("getSql :{}", JSON.toJSONString(boundSql));


        if (parameterMappings != null && parameterMappings.size() > 0) {
            logger.debug("getSql :{}", JSON.toJSONString(parameterMappings));
        }

        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId);
        str.append(":");
        str.append(sql);
        str.append(":");
        str.append(time);
        str.append("ms");
        return str.toString();
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat
                    .DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //logger.warn("showSql :{}", JSON.toJSONString(parameterMappings));
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    private ParameterMapping buildParameterMapping(Class<?> propertyType, String property,
                                                   Configuration configuration) {
        ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, property,
                propertyType);
        return builder.build();
    }
}
