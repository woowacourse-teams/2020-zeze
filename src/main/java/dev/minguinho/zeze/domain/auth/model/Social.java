package dev.minguinho.zeze.domain.auth.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"provider", "socialId"}
        )
    }
)
@Embeddable
public class Social {
    @Column(name = "social_provider", length = 20)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "social_id", length = 255)
    private String socialId;

    @Builder
    private Social(Provider provider, String socialId) {
        this.provider = provider;
        this.socialId = socialId;
    }

    public enum Provider {
        GITHUB,
        NONE;
    }
}
