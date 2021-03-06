CREATE TABLE member (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '생성일자',
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '수정일자',
    social_provider VARCHAR (20),
    social_id VARCHAR (255),
    PRIMARY KEY(id)
);
