package top.aleaf.configration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.aleaf.interceptor.LoginInterceptor;
import top.aleaf.interceptor.PassportInterceptor;

import javax.annotation.Resource;

/**
 * @author 郭新晔
 */
@Component
public class BisheWebConfiguration extends WebMvcConfigurerAdapter {
    @Resource
    private PassportInterceptor passportInterceptor;
    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/user/*").addPathPatterns("/academicExchange/*")
                .addPathPatterns("/approval/*").addPathPatterns("/companyProject/*")
                .addPathPatterns("/governmentProject/*").addPathPatterns("/lecture/*")
                .addPathPatterns("/paper/*").addPathPatterns("/patent/*")
                .addPathPatterns("/prize/*").addPathPatterns("/role/*")
                .addPathPatterns("/score/*").addPathPatterns("/softwareWork/*")
                .addPathPatterns("/strings/*").addPathPatterns("/teachingMaterial/*");
        super.addInterceptors(registry);
    }
}
