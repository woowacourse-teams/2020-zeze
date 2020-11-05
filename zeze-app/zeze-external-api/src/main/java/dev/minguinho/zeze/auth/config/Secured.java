package dev.minguinho.zeze.auth.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.minguinho.zeze.auth.model.Authority;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    Authority.Role authority() default Authority.Role.ROLE_USER;
}
