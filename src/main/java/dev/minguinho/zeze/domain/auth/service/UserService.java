package dev.minguinho.zeze.domain.auth.service;

import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.model.UserRepository;
import dev.minguinho.zeze.domain.resource.model.UserResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserResourceRepository userResourceRepository;
}
