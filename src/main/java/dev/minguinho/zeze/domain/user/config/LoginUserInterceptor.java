package dev.minguinho.zeze.domain.user.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {
    private static final String LOGIN_USER_ID = "loginUserId";
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationTokenExtractor authorizationTokenExtractor;

    public LoginUserInterceptor(JwtTokenProvider jwtTokenProvider,
        AuthorizationTokenExtractor authorizationTokenExtractor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorizationTokenExtractor = authorizationTokenExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!hasAnnotation((HandlerMethod)handler, LoginedUserId.class)) {
            return true;
        }
        String token = authorizationTokenExtractor.extract(request, "bearer");
        if (!jwtTokenProvider.validateToken(token)) {
            return false;
        }
        Long loginedUserId = jwtTokenProvider.getUserId(token);
        request.setAttribute(LOGIN_USER_ID, loginedUserId);
        return true;
    }

    private boolean hasAnnotation(HandlerMethod handlerMethod, Class annotationType) {
        return Arrays.stream(handlerMethod.getMethodParameters())
            .anyMatch(methodParameter -> methodParameter.hasParameterAnnotation(annotationType));
    }
}
