package dev.minguinho.zeze.domain.slide.model;

import java.time.ZonedDateTime;

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
    private String subtitle;
    private String author;
    private String presentedAt;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    private Long userId;
    private ZonedDateTime deletedAt = null;

    public Slide(String title, String subtitle, String author, String presentedAt, String content,
        AccessLevel accessLevel) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.presentedAt = presentedAt;
        this.content = content;
        this.accessLevel = accessLevel;
    }

    public Slide(String title, String content, AccessLevel accessLevel, Long userId) {
        this.title = title;
        this.content = content;
        this.accessLevel = accessLevel;
        this.userId = userId;
    }

    public Slide(
        String title,
        String subtitle,
        String author,
        String presentedAt,
        String content,
        AccessLevel accessLevel,
        Long userId
    ) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.presentedAt = presentedAt;
        this.content = content;
        this.accessLevel = accessLevel;
        this.userId = userId;
    }

    public void update(Slide slide) {
        this.title = slide.title;
        this.subtitle = slide.subtitle;
        this.author = slide.author;
        this.presentedAt = slide.presentedAt;
        this.content = slide.content;
        this.accessLevel = slide.accessLevel;
    }

    public boolean isOwner(Long userId) {
        return this.userId.equals(userId);
    }

    public boolean isPublic() {
        return this.accessLevel.equals(AccessLevel.PUBLIC);
    }

    public void delete() {
        this.deletedAt = ZonedDateTime.now();
    }

    public Slide clone(Long userId) {
        return new Slide(this.title + " (clone)", this.subtitle, this.author, this.presentedAt, this.content, this.accessLevel,
            userId);
    }

    public enum AccessLevel {
        PRIVATE,
        PUBLIC
    }
}
