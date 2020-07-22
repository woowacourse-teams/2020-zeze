package dev.minguinho.zeze.domain.auth.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import dev.minguinho.zeze.domain.common.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {

    @Embedded
    private Social social;

    @Builder
    private User(Social social) {
        this.social = social;
    }

}
