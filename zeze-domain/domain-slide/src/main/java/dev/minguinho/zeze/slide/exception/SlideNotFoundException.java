package dev.minguinho.zeze.slide.exception;

public class SlideNotFoundException extends RuntimeException {
    public SlideNotFoundException(Long slideId) {
        super("Slide Id : " + slideId + " 해당 슬라이드는 존재하지 않습니다.");
    }
}
