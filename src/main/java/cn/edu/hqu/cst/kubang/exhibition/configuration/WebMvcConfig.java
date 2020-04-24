package cn.edu.hqu.cst.kubang.exhibition.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author SunChonggao
 * @Date 2020/4/24 11:34
 * @Version 1.0
 * @Description:
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${exhibition.path.upload}")
    private String uploadPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/goods/**").addResourceLocations("file:"+ uploadPath);
    }
}