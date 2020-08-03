package dev.minguinho.zeze.domain.user.config;

import static org.springframework.web.context.request.RequestAttributes.*;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginedUserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String id = (String)webRequest.getAttribute("loginUserId", SCOPE_REQUEST);
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return Long.valueOf(id);
    }
}
