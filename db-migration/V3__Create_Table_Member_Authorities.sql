CREATE TABLE member_authorities (
    user_id BIGINT NOT NULL,
    authorities_id BIGINT NOT NULL ,
    PRIMARY KEY (user_id, authorities_id)
);

ALTER TABLE member_authorities
    ADD CONSTRAINT fk_user_authorities_authorities_id
        FOREIGN KEY (authorities_id)
            REFERENCES authority (id);

ALTER TABLE member_authorities
    ADD CONSTRAINT fk_user_authorities_user_id
        FOREIGN KEY (user_id)
            REFERENCES member (id);
