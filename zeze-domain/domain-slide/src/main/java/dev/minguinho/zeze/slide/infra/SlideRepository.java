package dev.minguinho.zeze.slide.infra;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.minguinho.zeze.slide.model.Slide;

public interface SlideRepository extends JpaRepository<Slide, Long> {
    Page<Slide> findAllByAccessLevelAndDeletedAtIsNullOrderByUpdatedAtDesc(Slide.AccessLevel accessLevel, Pageable pageable);

    Page<Slide> findAllByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(Long userId, Pageable pageable);

    Optional<Slide> findByIdAndDeletedAtIsNull(Long slideId);
}
