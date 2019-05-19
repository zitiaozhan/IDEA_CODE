package top.aleaf.filter;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 从springContext中获取所有ControllerMapping 并过滤所有的url，这些url的请求会经过TransferFilter
 * @author 郭新晔
 */
@Component
public class FilterUrlMapping {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterUrlMapping.class);

    @Resource
    private ApplicationContext applicationContext;

    //@Bean
    private Set<String> allUrlMappings() {
        Set<String> result = Sets.newHashSet();
        //获得所有的URL
        RequestMappingHandlerMapping rmhp = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();

        for (RequestMappingInfo info : map.keySet()) {
            //getMatchingPatterns优化
            String urlItem = info.getPatternsCondition().toString().replace("[", "").replace("]", "").trim();
            if (urlItem.contains("||")) {
                String[] subItems = urlItem.split("\\|\\|");
                for (String item : subItems) {
                    item = item.trim();
                    result.add(item);
                    LOGGER.info(item);
                }
            } else {
                result.add(urlItem);
                LOGGER.info(urlItem);
            }
        }
        return result;
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        //添加过滤器
        filterRegistration.setFilter(new XSSFilter());
        Set<String> allSaveUrlPattern = allUrlMappings();

        if (allSaveUrlPattern.size() == 0) {
            return filterRegistration;
        }

        filterRegistration.setUrlPatterns(allSaveUrlPattern);

        filterRegistration.setName("XSSFilter");
        return filterRegistration;
    }

}
