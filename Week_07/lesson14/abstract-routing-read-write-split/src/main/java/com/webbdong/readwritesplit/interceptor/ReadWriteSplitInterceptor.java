package com.webbdong.readwritesplit.interceptor;

import com.webbdong.readwritesplit.datasource.ReadWriteSplitDataSourceContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

/**
 * 读写分离 MyBatis 拦截器，动态切换数据源
 * @author Webb Dong
 * @date 2021-03-10 2:58 PM
 */
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class ReadWriteSplitInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStmt = (MappedStatement) invocation.getArgs()[0];
        // 所有查询类型的语句全部走从库
        if (SqlCommandType.SELECT == mappedStmt.getSqlCommandType()) {
            ReadWriteSplitDataSourceContextHolder.setCurrentTargetDataSourceByAlgorithm();
            try {
                return invocation.proceed();
            } finally {
                ReadWriteSplitDataSourceContextHolder.clearCurrentTargetDataSource();
            }
        } else {
            return invocation.proceed();
        }
    }

    // plugin 用来设置 MyBatis 什么情况需要进行拦截
    @Override
    public Object plugin(Object target) {
        // 拦截 Executor 执行器
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

}
