package dev.minguinho.zeze.domain.slide.model;

import static dev.minguinho.zeze.domain.slide.model.Slide.AccessLevel;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlideRepository extends JpaRepository<Slide, Long> {
    Page<Slide> findAllByAccessLevelAndDeletedAtIsNullOrderByUpdatedAtDesc(AccessLevel accessLevel, Pageable pageable);

    Page<Slide> findAllByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(Long userId, Pageable pageable);

    Optional<Slide> findByIdAndDeletedAtIsNull(Long slideId);
}
