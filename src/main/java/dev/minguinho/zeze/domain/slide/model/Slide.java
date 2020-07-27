package dev.minguinho.zeze.domain.slide.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.apache.logging.log4j.util.Strings;

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
    @Lob
    private String content;
    private String contentType;

    public void update(String title, String content, String contentType) {
        if (Strings.isNotBlank(title)) {
            this.title = title;
        }
        if (Strings.isNotBlank(content)) {
            this.content = content;
        }
        if (Strings.isNotBlank(contentType)) {
            this.contentType = contentType;
        }
    }
}
