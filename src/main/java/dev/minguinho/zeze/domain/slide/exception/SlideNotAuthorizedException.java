package dev.minguinho.zeze.domain.slide.exception;

public class SlideNotAuthorizedException extends RuntimeException {
    public SlideNotAuthorizedException() {
        super("사용자의 슬라이드가 아닙니다.");
    }
}
