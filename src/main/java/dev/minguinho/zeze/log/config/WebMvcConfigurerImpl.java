package dev.minguinho.zeze.log.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.log.interceptor.LogInterceptor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {
    private final LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
    }
}
