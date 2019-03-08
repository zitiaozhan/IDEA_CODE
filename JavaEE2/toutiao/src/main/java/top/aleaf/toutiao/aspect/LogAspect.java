/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LogAspect
 * Author:   郭新晔
 * Date:     2018/11/17 0017 22:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.*;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @create 2018/11/17 0017
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 指定方法执行之前执行该方法
     */
    @Before("execution(* top.aleaf.toutiao.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        logger.info("Before method: ");
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            sb.append(arg.toString() + " | ");
        }
        logger.info(sb.toString());
    }

    /**
     * 指定方法执行之后执行该方法
     */
    @After("execution(* top.aleaf.toutiao.controller.*Controller.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info("After method: ");
    }
}