package dev.minguinho.zeze.auth.api.dto;

import org.springframework.test.util.ReflectionTestUtils;

import dev.minguinho.zeze.auth.dto.request.GithubSignInDto;
import dev.minguinho.zeze.auth.model.Social;

public class GithubSignInDtoTest {
    public static GithubSignInDto getGithubSignInDtoFixture() {
        GithubSignInDto githubSignInDto = new GithubSignInDto();

        ReflectionTestUtils.setField(githubSignInDto, "provider", Social.Provider.GITHUB);
        ReflectionTestUtils.setField(githubSignInDto, "code", "code");

        return githubSignInDto;
    }
}
