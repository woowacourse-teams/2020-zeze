package dev.minguinho.zeze.domain.auth.config;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.auth.exception.NotAuthorizedException;
import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.auth.model.Authority;

@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationTokenExtractor authorizationTokenExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Secured secured = getAnnotation((HandlerMethod)handler, Secured.class);
        if (Objects.isNull(secured)) {
            return true;
        }
        String token = authorizationTokenExtractor.extract(request, "bearer");
        Set<Authority.Role> roles = jwtTokenProvider.getAuthorities(token);
        if(roles.contains(secured.authority())) {
            return true;
        }
        throw new NotAuthorizedException();
    }

    private <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationType) {
        return Optional.ofNullable(handlerMethod.getMethodAnnotation(annotationType))
            .orElse(handlerMethod.getBeanType().getAnnotation(annotationType));
    }
}
