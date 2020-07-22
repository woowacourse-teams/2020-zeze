package dev.minguinho.zeze.domain.slide.model;

import javax.persistence.Entity;

import dev.minguinho.zeze.domain.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Slide extends BaseEntity {
    private String title;
    private String content;
    private String contentType;
}
