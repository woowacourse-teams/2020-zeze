package dev.minguinho.zeze.domain.user.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.user.api.dto.UserResourceRequestDto;
import dev.minguinho.zeze.domain.user.api.dto.UserResourceResponseDto;
import dev.minguinho.zeze.domain.user.model.UserResource;
import dev.minguinho.zeze.domain.user.model.UserResourceRepository;

@RequiredArgsConstructor
@Service
public class UserResourceService {
    private final UserResourceRepository userResourceRepository;

    public UserResourceResponseDto retrieveUserResourceBy(Long id) {
        return UserResourceResponseDto.from(findBy(id));
    }

    public UserResourceResponseDto updateUserResource(Long id, @Valid UserResourceRequestDto userResourceRequestDto) {
        UserResource userResource = findBy(id);
        UserResource userResourceToUpdate = userResourceRequestDto.toEntity();
        userResource.update(userResourceToUpdate);
        userResourceRepository.save(userResource);
        return UserResourceResponseDto.from(userResource);
    }

    private UserResource findBy(Long id) {
        return userResourceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 정보입니다."));
    }
}
