package dev.minguinho.zeze.domain.user.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.domain.user.model.UserResource;

@NoArgsConstructor
@Getter
public class UserResourceRequestDto {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String profileImage;

    public UserResource toEntity() {
        return UserResource.builder()
            .email(email)
            .name(name)
            .profileImage(profileImage)
            .build();
    }
}
