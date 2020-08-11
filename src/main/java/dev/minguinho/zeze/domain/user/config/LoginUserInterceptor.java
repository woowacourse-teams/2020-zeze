package dev.minguinho.zeze.domain.user.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;

@RequiredArgsConstructor
@Component
public class LoginUserInterceptor implements HandlerInterceptor {
    private static final String LOGIN_USER_ID = "loginUserId";

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationTokenExtractor authorizationTokenExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional<LoginUserId> loginUserIdAnnotation = getAnnotation((HandlerMethod)handler, LoginUserId.class);
        if (!loginUserIdAnnotation.isPresent()) {
            return true;
        }
        String token = authorizationTokenExtractor.extract(request, "bearer");
        if (token.isEmpty()) {
            return true;
        }
        if (!jwtTokenProvider.validateToken(token)) {
            return false;
        }
        Long loginUserId = jwtTokenProvider.getUserId(token);
        request.setAttribute(LOGIN_USER_ID, loginUserId);
        return true;
    }

    private <A extends Annotation> Optional<A> getAnnotation(HandlerMethod handlerMethod, Class<A> annotationType) {
        return Arrays.stream(handlerMethod.getMethodParameters())
            .filter(parameter -> parameter.hasParameterAnnotation(annotationType))
            .map(parameter -> parameter.getParameterAnnotation(annotationType))
            .findFirst();
    }
}
