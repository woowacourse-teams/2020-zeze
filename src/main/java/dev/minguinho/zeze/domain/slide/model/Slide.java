package dev.minguinho.zeze.domain.slide.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.domain.common.model.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Slide extends BaseEntity {
    private String title;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    public void update(String title, String content, String accessLevel) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
        if (accessLevel != null) {
            this.accessLevel = AccessLevel.valueOf(accessLevel);
        }
    }

    public enum AccessLevel {
        PRIVATE,
        PUBLIC
    }
}
