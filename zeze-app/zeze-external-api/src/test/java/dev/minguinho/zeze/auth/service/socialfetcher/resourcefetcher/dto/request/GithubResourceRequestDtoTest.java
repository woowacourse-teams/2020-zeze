package dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.minguinho.zeze.auth.model.Social;

class GithubResourceRequestDtoTest {
    @DisplayName("github user resource request dto 생성")
    @Test
    void createGithubResourceRequestDto() {
        GithubResourceRequestDto githubResourceRequestDto = GithubResourceRequestDto.builder()
            .providerAccessToken("accessToken")
            .build();

        assertAll(
            () -> assertThat(githubResourceRequestDto.getProvider()).isEqualTo(Social.Provider.GITHUB),
            () -> assertThat(githubResourceRequestDto.getProviderAccessToken()).isEqualTo("accessToken")
        );
    }
}
