package dev.minguinho.zeze.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResourceRequestDto {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String profileImage;
}
