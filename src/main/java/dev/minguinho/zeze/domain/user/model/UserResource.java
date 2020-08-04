package dev.minguinho.zeze.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.domain.common.model.BaseEntity;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserResource extends BaseEntity {
    @Column(unique = true)
    private Long userId;
    private String email;
    private String name;
    private String profileImage;

    public void update(UserResource userResource) {
        this.email = userResource.email;
        this.name = userResource.name;
        this.profileImage = userResource.profileImage;
    }
}
