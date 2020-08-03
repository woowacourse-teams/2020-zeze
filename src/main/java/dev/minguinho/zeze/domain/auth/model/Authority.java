package dev.minguinho.zeze.domain.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Authority {
    public static final Authority USER = new Authority(1L, Role.ROLE_USER);
    public static final Authority ADMIN = new Authority(2L, Role.ROLE_ADMIN);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;

    private Authority(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getAuthority() {
        return role.name();
    }

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
}
