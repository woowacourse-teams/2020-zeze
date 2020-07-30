package dev.minguinho.zeze.log.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.minguinho.zeze.log.interceptor.LogInterceptor;

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {
    private final LogInterceptor logInterceptor;

    public WebMvcConfigurerImpl(LogInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
    }
}
