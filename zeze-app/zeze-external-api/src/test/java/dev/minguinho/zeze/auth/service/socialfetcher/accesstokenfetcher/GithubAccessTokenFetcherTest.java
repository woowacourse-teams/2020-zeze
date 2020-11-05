package dev.minguinho.zeze.auth.service.socialfetcher.accesstokenfetcher;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import dev.minguinho.zeze.auth.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.auth.dto.response.SocialAccessTokenResponse;
import dev.minguinho.zeze.auth.model.Social;

@ExtendWith(MockitoExtension.class)
class GithubAccessTokenFetcherTest {
    private static MockWebServer server;
    private GithubAccessTokenFetcher githubAccessTokenFetcher;
    @Mock
    private SocialAccessTokenRequestDto socialAccessTokenRequestDto;

    @BeforeAll
    static void beforeAll() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @BeforeEach
    void setUp() {
        WebClient.Builder builder = WebClient.builder();
        githubAccessTokenFetcher = new GithubAccessTokenFetcher(builder, "clientId", "clientSecret");

        String baseUrl = String.format("http://localhost:%s",
            server.getPort());
        ReflectionTestUtils.setField(githubAccessTokenFetcher, "webClient", builder
            .baseUrl(baseUrl)
            .build());
    }

    @Test
    void fetchAccessToken_ValidInput_ValidOutput() {
        String jsonResponse = "{\n"
            + "  \"access_token\": \"accessToken\"\n"
            + "}";
        server.enqueue(new MockResponse()
            .setBody(jsonResponse)
            .addHeader("Content-Type", APPLICATION_JSON.toString()));
        given(socialAccessTokenRequestDto.getProvider()).willReturn(Social.Provider.GITHUB);
        given(socialAccessTokenRequestDto.getCode()).willReturn("code");

        SocialAccessTokenResponse accessTokenResponseDto =
            githubAccessTokenFetcher.fetch(socialAccessTokenRequestDto).block();
        assertThat(accessTokenResponseDto.getAccessToken()).isEqualTo("accessToken");
    }

    @Test
    void fetchAccessToken_InvalidSocial_Exception() {
        given(socialAccessTokenRequestDto.getProvider()).willReturn(Social.Provider.NONE);
        assertThatThrownBy(() -> githubAccessTokenFetcher.fetch(socialAccessTokenRequestDto))
            .isInstanceOf(IllegalStateException.class);
    }
}
