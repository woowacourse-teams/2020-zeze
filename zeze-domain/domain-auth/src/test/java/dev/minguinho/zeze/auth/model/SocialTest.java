package dev.minguinho.zeze.auth.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SocialTest {
    @DisplayName("socialId 생성 테스트")
    @Test
    void createSocial() {
        Social social = Social.builder()
            .provider(Social.Provider.GITHUB)
            .socialId("socialId")
            .build();

        assertAll(
            () -> assertThat(social.getProvider()).isEqualTo(Social.Provider.GITHUB),
            () -> assertThat(social.getSocialId()).isEqualTo("socialId")
        );
    }
}
