package dev.minguinho.zeze.auth.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.minguinho.zeze.auth.model.Social;
import dev.minguinho.zeze.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocial(Social social);
}
