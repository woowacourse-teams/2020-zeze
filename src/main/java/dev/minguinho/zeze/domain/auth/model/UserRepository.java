package dev.minguinho.zeze.domain.auth.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsBySocial(Social social);

    Optional<User> findBySocial(Social social);
}
