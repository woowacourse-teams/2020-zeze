package dev.minguinho.zeze.domain.auth.service.resourcefetcher;

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

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.api.dto.request.SocialResourceResponseDto;
import dev.minguinho.zeze.domain.auth.model.Social;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class GithubResourceFetcherTest {

    public static MockWebServer server;

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
            "  \"email\": \"foo@bar.com\"\n" +
            "}";
        server.enqueue(new MockResponse()
            .setBody(jsonResponse)
            .addHeader("Content-Type", APPLICATION_JSON.toString()));
        given(socialResourceRequestDto.getProvider()).willReturn(Social.Provider.GITHUB);
        given(socialResourceRequestDto.getProviderAccessToken()).willReturn("providerAccessToken");

        SocialResourceResponseDto socialResponse =
            githubResourceFetcher.fetchUserResource(socialResourceRequestDto).block();

        assertThat(socialResponse.getSocialId()).isEqualTo("socialId");
        assertThat(socialResponse.getEmail()).isEqualTo("foo@bar.com");
    }

    @Test
    void fetchUserResource_InvalidProvider_Exception() {
        given(socialResourceRequestDto.getProvider()).willReturn(Social.Provider.GOOGLE);
        assertThatThrownBy(() -> githubResourceFetcher.fetchUserResource(socialResourceRequestDto))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void fetchUserResource_InvalidIdField_Exception() {
        String jsonResponse = "{\n" +
            "  \"socialId\": \"socialId\",\n" +
            "  \"email\": \"foo@bar.com\"\n" +
            "}";
        server.enqueue(new MockResponse()
            .setBody(jsonResponse)
            .addHeader("Content-Type", APPLICATION_JSON.toString()));
        given(socialResourceRequestDto.getProvider()).willReturn(Social.Provider.GITHUB);
        given(socialResourceRequestDto.getProviderAccessToken()).willReturn("providerAccessToken");
        Mono<SocialResourceResponseDto> monoResponse =
            githubResourceFetcher.fetchUserResource(socialResourceRequestDto);
        assertThatThrownBy(monoResponse::block)
            .isInstanceOf(IllegalArgumentException.class);
    }
}
