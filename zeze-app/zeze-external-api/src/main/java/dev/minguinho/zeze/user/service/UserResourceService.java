package dev.minguinho.zeze.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.user.dto.UserResourceRequestDto;
import dev.minguinho.zeze.user.dto.UserResourceResponseDto;
import dev.minguinho.zeze.user.infra.UserResourceRepository;
import dev.minguinho.zeze.user.model.UserResource;

@RequiredArgsConstructor
@Service
public class UserResourceService {
    private final UserResourceRepository userResourceRepository;

    public UserResourceResponseDto retrieveUserResourceBy(Long id) {
        return UserResourceConvertor.toUserResourceResponseDto(findBy(id));
    }

    public UserResourceResponseDto updateUserResource(Long id, UserResourceRequestDto userResourceRequestDto) {
        UserResource userResource = findBy(id);
        UserResource userResourceToUpdate = UserResourceConvertor.toEntity(userResourceRequestDto);
        userResource.update(userResourceToUpdate);
        userResourceRepository.save(userResource);
        return UserResourceConvertor.toUserResourceResponseDto(userResource);
    }

    private UserResource findBy(Long id) {
        return userResourceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 정보입니다."));
    }
}
