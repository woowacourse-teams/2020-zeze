package dev.minguinho.zeze.domain.auth.service.socialfetcher;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.GithubResourceFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@ExtendWith(MockitoExtension.class)
class GithubResourceFetcherTest {
    private static MockWebServer server;
    private GithubResourceFetcher githubResourceFetcher;
    @Mock
    private SocialResourceRequestDto socialResourceRequestDto;

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
        githubResourceFetcher = new GithubResourceFetcher(builder);

        String baseUrl = String.format("http://localhost:%s",
            server.getPort());
        ReflectionTestUtils.setField(githubResourceFetcher, "webClient", builder
            .baseUrl(baseUrl)
            .build());
    }

    @Test
    void fetchUserResource_ValidInput_ValidOutput() {
        String jsonResponse = "{\n" +
            "  \"id\": \"socialId\",\n" +
            "  \"email\": \"foo@bar.com\",\n" +
            "  \"name\": \"foo\",\n" +
            "  \"avatar_url\": \"image\"\n" +
            "}";
        server.enqueue(new MockResponse()
            .setBody(jsonResponse)
            .addHeader("Content-Type", APPLICATION_JSON.toString()));
        given(socialResourceRequestDto.getProvider()).willReturn(Social.Provider.GITHUB);
        given(socialResourceRequestDto.getProviderAccessToken()).willReturn("providerAccessToken");

        SocialResourceResponse socialResponse =
            githubResourceFetcher.fetch(socialResourceRequestDto).block();

        assertAll(
            () -> assertThat(socialResponse.getSocialId()).isEqualTo("socialId"),
            () -> assertThat(socialResponse.getEmail()).isEqualTo("foo@bar.com"),
            () -> assertThat(socialResponse.getName()).isEqualTo("foo"),
            () -> assertThat(socialResponse.getProfileImage()).isEqualTo("image")
        );
    }

    @Test
    void fetchUserResource_InvalidProvider_Exception() {
        given(socialResourceRequestDto.getProvider()).willReturn(Social.Provider.NONE);
        assertThatThrownBy(() -> githubResourceFetcher.fetch(socialResourceRequestDto))
            .isInstanceOf(IllegalStateException.class);
    }
}
