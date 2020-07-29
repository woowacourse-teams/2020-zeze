package dev.minguinho.zeze.domain.auth.service;

import static dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.GithubResourceResponseDtoTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.auth.model.UserRepository;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.GithubResourceResponseDto;
import dev.minguinho.zeze.domain.user.model.UserResource;
import dev.minguinho.zeze.domain.user.model.UserResourceRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserResourceRepository userResourceRepository;
    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, userResourceRepository);
    }

    @DisplayName("존재하는 유저의 경우 바로 반환")
    @Test
    void saveUser_Existing_ValidOutput() {
        GithubResourceResponseDto response = getGithubResourceFixture();
        given(userRepository.findBySocial(any())).willReturn(Optional.of(user));
        User foundUser = userService.findOrElseSave(response, Social.Provider.GITHUB);
        assertThat(foundUser).isEqualTo(user);
    }

    @DisplayName("존재하지 않는 유저의 경우 save 호출")
    @Test
    void findUser_NotExisting_ValidOutput() {
        GithubResourceResponseDto response = getGithubResourceFixture();
        given(userRepository.findBySocial(any())).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(user);
        given(user.getId()).willReturn(1L);
        User user = userService.findOrElseSave(response, Social.Provider.GITHUB);
        assertThat(user.getId()).isEqualTo(1L);
        verify(userRepository).save(any(User.class));
        verify(userResourceRepository).save(any(UserResource.class));
    }
}
