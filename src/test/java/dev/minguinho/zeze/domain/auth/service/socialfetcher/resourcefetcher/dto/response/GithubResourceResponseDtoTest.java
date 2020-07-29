package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.user.model.UserResource;

public class GithubResourceResponseDtoTest {
    public static GithubResourceResponseDto getGithubResourceFixture() {
        return GithubResourceResponseDto.builder()
            .socialId("socialId")
            .email("email")
            .name("name")
            .profileImage("image")
            .build();
    }

    @DisplayName("User 생성")
    @Test
    void createUser_ValidInput_ValidOutput() {
        GithubResourceResponseDto socialResponse = getGithubResourceFixture();
        User user = socialResponse.createUser(Social.Provider.GITHUB);
        assertAll(
            () -> assertThat(user.getSocial().getProvider()).isEqualTo(Social.Provider.GITHUB),
            () -> assertThat(user.getSocial().getSocialId()).isEqualTo("socialId")
        );
    }

    @DisplayName("User Resource 생성")
    @Test
    void createUserResource_ValidInput_ValidOutput() {
        GithubResourceResponseDto socialResponse = getGithubResourceFixture();
        UserResource userResource = socialResponse.createUserResource(1L);

        assertAll(
            () -> assertThat(userResource.getUserId()).isEqualTo(1L),
            () -> assertThat(userResource.getEmail()).isEqualTo("email"),
            () -> assertThat(userResource.getName()).isEqualTo("name"),
            () -> assertThat(userResource.getProfileImage()).isEqualTo("image")
        );
    }
}
