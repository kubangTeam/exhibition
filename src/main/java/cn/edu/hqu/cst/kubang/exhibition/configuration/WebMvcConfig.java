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
 * @Description:服务器本地目录和Web地址的映射
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${exhibition.path.upload.goods}")
    private String goodsPath;
    @Value("${exhibition.path.upload.user}")
    private String userPath;
    @Value("${exhibition.path.upload.company}")
    private String companyPath;
    @Value("${exhibition.path.upload.organizer}")
    private String organizerPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/goods/**").addResourceLocations("file:"+ goodsPath);
        registry.addResourceHandler("/images/user/**").addResourceLocations("file:"+ userPath);
        registry.addResourceHandler("/images/company/**").addResourceLocations("file:"+ companyPath);
        registry.addResourceHandler("/images/organizer/**").addResourceLocations("file:"+ organizerPath);
    }
}