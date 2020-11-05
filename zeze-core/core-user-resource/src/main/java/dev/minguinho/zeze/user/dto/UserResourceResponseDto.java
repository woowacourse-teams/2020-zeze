package dev.minguinho.zeze.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserResourceResponseDto {
    private final String name;
    private final String email;
    private final String profileImage;

    @Builder
    private UserResourceResponseDto(String name, String email, String profileImage) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }
}
