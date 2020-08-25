package dev.minguinho.zeze.domain.slide.model;

import static dev.minguinho.zeze.domain.slide.model.Slide.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;

public interface SlideRepository extends JpaRepository<Slide, Long> {
    Page<Slide> findAllByAccessLevelAndDeletedAt(AccessLevel accessLevel, ZonedDateTime zonedDateTime, Pageable pageable);

    Page<Slide> findAllByUserIdAndDeletedAtOrderByUpdatedAtDesc(Long userId, ZonedDateTime zonedDateTime, Pageable pageable);
}
