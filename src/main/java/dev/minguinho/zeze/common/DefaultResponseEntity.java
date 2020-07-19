package dev.minguinho.zeze.common;

import dev.minguinho.zeze.error.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DefaultResponseEntity<T> {
    private final T data;
    private final Integer code;
    private final String message;

    public static <T> DefaultResponseEntity<T> from(T data) {
        return new DefaultResponseEntity<>(data, null, null);
    }

    public static DefaultResponseEntity<Void> error(Error error) {
        return new DefaultResponseEntity<>(null, error.getCode(), error.getMessage());
    }
}
