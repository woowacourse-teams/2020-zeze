package dev.minguinho.zeze.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.minguinho.zeze.user.model.UserResource;

public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
}
