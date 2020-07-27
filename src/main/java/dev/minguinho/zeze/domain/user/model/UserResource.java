package dev.minguinho.zeze.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import dev.minguinho.zeze.domain.common.model.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserResource extends BaseEntity {
    @Column(unique = true)
    private Long userId;
    private String email;
    private String name;
    private String profileImage;
}
