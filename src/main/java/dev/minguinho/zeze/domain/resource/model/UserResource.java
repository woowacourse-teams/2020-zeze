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
    private String email;
    private String name;

    @Builder
    private UserResource(
        Long userId,
        String email,
        String name
    ) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
