package dev.minguinho.zeze.domain.slide.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlideRepository extends JpaRepository<Slide, Long> {
    Page<Slide> findAllByUserIdAndIdGreaterThan(Long userId, Long id, Pageable pageable);
}
