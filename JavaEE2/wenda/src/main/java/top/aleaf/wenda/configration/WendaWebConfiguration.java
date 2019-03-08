package top.aleaf.wenda.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.aleaf.wenda.interceptor.LoginInterceptor;
import top.aleaf.wenda.interceptor.PassportInterceptor;
import top.aleaf.wenda.interceptor.TestInterceptor;

@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    TestInterceptor testInterceptor;

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
