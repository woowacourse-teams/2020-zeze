package dev.minguinho.zeze.domain.user.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
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
        String token = authorizationTokenExtractor.extract(request, "bearer");
        if (jwtTokenProvider.validateToken(token)) {
            return false;
        }
        Long loginedUserId = jwtTokenProvider.getUserId(token);
        request.setAttribute(LOGIN_USER_ID, loginedUserId);
        return true;
    }
}
