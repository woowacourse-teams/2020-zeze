package dev.minguinho.zeze.domain.auth.api.dto.request;

import org.springframework.test.util.ReflectionTestUtils;

import dev.minguinho.zeze.domain.auth.model.Social;

public class GithubSignInDtoTest {
    public static GithubSignInDto getGithubSignInDtoFixture() {
        GithubSignInDto githubSignInDto = new GithubSignInDto();

        ReflectionTestUtils.setField(githubSignInDto, "provider", Social.Provider.GITHUB);
        ReflectionTestUtils.setField(githubSignInDto, "code", "code");

        return githubSignInDto;
    }
}
