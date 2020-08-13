package dev.minguinho.zeze.domain.auth.exception;

public class InvalidTokenException extends IllegalArgumentException {

    public InvalidTokenException() {
    }

    public InvalidTokenException(String s) {
        super(s);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(Throwable cause) {
        super(cause);
    }
}
