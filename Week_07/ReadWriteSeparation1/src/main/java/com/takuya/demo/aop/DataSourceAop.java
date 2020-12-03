package com.takuya.demo.aop;

import com.takuya.demo.configuration.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Pointcut("@annotation(com.takuya.demo.configuration.UseSlaveDatabase)")
    public void readPointcut() {

    }
}
