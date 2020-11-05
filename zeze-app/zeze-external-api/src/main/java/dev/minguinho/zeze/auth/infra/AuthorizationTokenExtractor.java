package dev.minguinho.zeze.auth.infra;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationTokenExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCESS_TOKEN_TYPE =
        AuthorizationTokenExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public String extract(HttpServletRequest request, String type) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(type.toLowerCase())) {
                String headerValue = value.substring(type.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, type.length()).trim());
                int commaIndex = headerValue.indexOf(',');
                if (commaIndex > 0) {
                    headerValue = headerValue.substring(0, commaIndex);
                }
                return headerValue;
            }
        }
        return "";
    }
}
