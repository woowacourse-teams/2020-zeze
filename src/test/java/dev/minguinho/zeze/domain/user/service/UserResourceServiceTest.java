package dev.minguinho.zeze.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.user.api.dto.UserResourceRequestDto;
import dev.minguinho.zeze.domain.user.api.dto.UserResourceResponseDto;
import dev.minguinho.zeze.domain.user.model.UserResource;
import dev.minguinho.zeze.domain.user.model.UserResourceRepository;

@ExtendWith(MockitoExtension.class)
class UserResourceServiceTest {
    @Mock
    private UserResourceRepository userResourceRepository;

    @InjectMocks
    private UserResourceService userResourceService;

    @Test
    @DisplayName("유저 정보 업데이트")
    void updateUserResource() {
        UserResource userResource = UserResource.builder()
            .userId(1L)
            .build();
        UserResourceRequestDto userResourceRequestDto = new UserResourceRequestDto();

        given(userResourceRepository.findById(1L)).willReturn(Optional.of(userResource));

        assertThat(userResourceService.updateUserResource(1L, userResourceRequestDto)).isEqualToComparingFieldByField(
            UserResourceResponseDto.from(userResource));
    }
}
