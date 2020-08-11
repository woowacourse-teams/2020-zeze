package dev.minguinho.zeze.domain.documentation;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@TestConfiguration
@Profile("doc")
public class DocumentationConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createTestAuthorizationInterceptor());
        registry.addInterceptor(createTestLoginUserInterceptor());
    }

    @Bean
    public TestAuthorizationInterceptor createTestAuthorizationInterceptor() {
        return new TestAuthorizationInterceptor();
    }

    @Bean
    public TestLoginUserInterceptor createTestLoginUserInterceptor() {
        return new TestLoginUserInterceptor();
    }
}
