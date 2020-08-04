package dev.minguinho.zeze.domain.auth.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dev.minguinho.zeze.domain.auth.model.Authority.Role;

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
    Role authority() default Role.ROLE_USER;
}
