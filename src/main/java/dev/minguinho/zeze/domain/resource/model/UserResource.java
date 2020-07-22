package dev.minguinho.zeze.domain.resource.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import dev.minguinho.zeze.domain.common.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserResource extends BaseEntity {
    @Column(unique = true)
    private Long userId;
    private String name;
    private String email;

    @Builder
    private UserResource(
        Long userId,
        String name,
        String email
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
