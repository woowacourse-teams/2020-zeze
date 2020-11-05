package dev.minguinho.zeze.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.common.model.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_resource")
public class UserResource extends BaseEntity {
    @Column(unique = true)
    private Long userId;
    private String email;
    private String name;
    private String profileImage;

    @Builder
    private UserResource(Long userId, String email, String name, String profileImage) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
    }

    public void update(UserResource userResource) {
        this.email = userResource.email;
        this.name = userResource.name;
        this.profileImage = userResource.profileImage;
    }
}

