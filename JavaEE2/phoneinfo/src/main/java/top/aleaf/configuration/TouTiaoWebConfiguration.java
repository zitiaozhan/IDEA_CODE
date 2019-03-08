/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TouTiaoWebConfiguration
 * Author:   郭新晔
 * Date:     2018/12/3 0003 15:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import top.aleaf.interceptor.HelperInterceptor;

/**
 * 〈〉
 *
 * @create 2018/12/3 0003
 */
@Component
public class TouTiaoWebConfiguration extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {
    @Autowired
    private HelperInterceptor helperInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.helperInterceptor);
        super.addInterceptors(registry);
    }
}