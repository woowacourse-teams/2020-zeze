package dev.minguinho.zeze.domain.user.documentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.auth.model.Authority;
import dev.minguinho.zeze.domain.documentation.Documentation;
import dev.minguinho.zeze.domain.user.api.LoginUserController;
import dev.minguinho.zeze.domain.user.api.dto.UserResourceRequestDto;
import dev.minguinho.zeze.domain.user.api.dto.UserResourceResponseDto;
import dev.minguinho.zeze.domain.user.config.LoginUserIdMethodArgumentResolver;
import dev.minguinho.zeze.domain.user.service.UserResourceService;

@WebMvcTest(controllers = {LoginUserController.class})
public class UserDocumentation extends Documentation {
    @MockBean
    private UserResourceService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthorizationTokenExtractor authorizationTokenExtractor;

    @MockBean
    private LoginUserIdMethodArgumentResolver loginUserIdMethodArgumentResolver;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthenticationDto authenticationDto;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        authenticationDto = AuthenticationDto.builder().accessToken("token").build();
    }

    @Test
    void getUser() throws Exception {
        UserResourceResponseDto userResourceResponseDto = UserResourceResponseDto.builder()
            .name("이름")
            .email("email@google.com")
            .profileImage("url")
            .build();
        given(userService.retrieveUserResourceBy(anyLong())).willReturn(userResourceResponseDto);
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(jwtTokenProvider.getAuthorities(anyString())).willReturn(Collections.singleton(Authority.Role.ROLE_USER));
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mockMvc.perform(get("/api/me")
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("users/get",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")
                ),
                responseFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("User 이름"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("User 이메일"),
                    fieldWithPath("profileImage").type(JsonFieldType.STRING).description("User 프로파일 이미지")
                ))
            );
    }

    @Test
    void updateUser() throws Exception {
        UserResourceResponseDto userResourceResponseDto = UserResourceResponseDto.builder()
            .name("name")
            .email("email@google.com")
            .profileImage("url")
            .build();
        given(userService.updateUserResource(anyLong(), any(UserResourceRequestDto.class))).willReturn(
            userResourceResponseDto);
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(jwtTokenProvider.getAuthorities(anyString())).willReturn(Collections.singleton(Authority.Role.ROLE_USER));
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);
        String content = objectMapper.writeValueAsString(userResourceResponseDto);

        mockMvc.perform(put("/api/me")
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(content)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("users/update",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")
                ),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("수정할 User 이름"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("수정할 User 이메일"),
                    fieldWithPath("profileImage").type(JsonFieldType.STRING).description("수정할 User 프로파일 이미지")
                ),
                responseFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("수정한 User 이름"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("수정한 User 이메일"),
                    fieldWithPath("profileImage").type(JsonFieldType.STRING).description("수정한 User 프로파일 이미지")
                ))
            );
    }
}
