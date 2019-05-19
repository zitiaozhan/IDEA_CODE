package com.xsyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot通过一个主程序启动整个应用,使用注解:
 * @SpringBootApplication
 *
 * 1、我们并没有安装Tomcat？（自动的引入了嵌入式的Tomcat(Servlet容器)）
 * 2、应用怎么运行起来？SpringMVC、Spring一大堆配置？
 *
 * SpringBoot；
 * 0、自动配置：spring-boot-autoconfigure-1.5.10.RELEASE.jar
 * 	  所有的场景都自动配置好；XXXAutoConfiguration
 *    HttpEncodingAutoConfiguration:自动配置编码规则;
 * 	  默认的规则基本上都是在xxxProperties类中封装着；
 * 	  @ConfigurationProperties(prefix = "spring.http.encoding")
 * 	  spring.http.encoding：xxxProperties类中的所有属性都是和配置文件绑定
 *
 * 0、所有的场景SpringBoot都自动配置好了，我们只需要引入我们需要的开发场景（starter），
 *    自动配置的规则也都可以通过配置文件完全指定（xxxProperties）；
 *    默认所有的组件都应该放在主程序所在的包及他下面的子包
 *    修改Tomcat的端口号？ServerProperties:server.port=8080
 *
 * 	  1、我们需要什么开发场景就导入相应的starter（场景启动器）
 * 	  2、直接编写业务；
 */
@SpringBootApplication
public class HelloApplication {
	public static void main(String[] args) {
		//将@SpringBootApplication注解标注的类传入run方法的参数,启动应用
		SpringApplication.run(HelloApplication.class, args);
	}
}
