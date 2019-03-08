/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LogAspect
 * Author:   郭新晔
 * Date:     2019/1/16 0016 20:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* top.aleaf.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint point) {
        LOGGER.info("方法执行之前：");
        StringBuilder sb = new StringBuilder();
        for (Object arg : point.getArgs()) {
            sb.append(arg == null ? "null" : arg.toString() + " | ");
        }
        LOGGER.info(sb.toString());
        LOGGER.info(point.toString());
    }

    @After("execution(* top.aleaf.controller.*Controller.*(..))")
    public void afterMethod() {
        LOGGER.info("方法执行完成");
    }
}