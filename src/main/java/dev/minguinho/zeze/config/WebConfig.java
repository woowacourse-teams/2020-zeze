package dev.minguinho.zeze.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.auth.config.AuthorizationInterceptor;
import dev.minguinho.zeze.domain.user.config.LoginUserIdMethodArgumentResolver;
import dev.minguinho.zeze.domain.user.config.LoginUserInterceptor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;
    private final LoginUserInterceptor loginUserInterceptor;
    private final LoginUserIdMethodArgumentResolver loginUserIdMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserIdMethodArgumentResolver);
    }
}
