package dev.minguinho.zeze.domain.auth.exception;

public class NotAuthorizedException extends IllegalStateException {
    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String s) {
        super(s);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
