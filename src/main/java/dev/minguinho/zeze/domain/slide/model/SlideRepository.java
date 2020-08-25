package dev.minguinho.zeze.domain.slide.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import static dev.minguinho.zeze.domain.slide.model.Slide.*;

public interface SlideRepository extends JpaRepository<Slide, Long> {
    Page<Slide> findAllByAccessLevelAndDeletedAtIsNull(AccessLevel accessLevel, Pageable pageable);

    Page<Slide> findAllByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(Long userId, Pageable pageable);
}
