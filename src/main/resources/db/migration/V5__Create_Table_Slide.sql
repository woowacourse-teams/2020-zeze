CREATE TABLE slide (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    access_level VARCHAR(255),
    content LONGTEXT,
    title VARCHAR(255),
    user_id BIGINT,
    PRIMARY KEY (id)
);


