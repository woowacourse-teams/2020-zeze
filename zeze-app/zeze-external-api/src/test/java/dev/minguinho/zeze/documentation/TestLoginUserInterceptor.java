package dev.minguinho.zeze.documentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class TestLoginUserInterceptor implements HandlerInterceptor {
    private static final String LOGIN_USER_ID = "loginUserId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        request.setAttribute(LOGIN_USER_ID, 1L);
        return false;
    }
}
