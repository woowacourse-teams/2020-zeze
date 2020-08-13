CREATE TABLE member_resource (
   id BIGINT NOT NULL AUTO_INCREMENT,
   created_at DATETIME(6),
   updated_at DATETIME(6),
   email VARCHAR(255),
   name VARCHAR(255),
   profile_image VARCHAR(255),
   user_id BIGINT,
   PRIMARY KEY (id)
);

ALTER TABLE member_resource
    ADD CONSTRAINT uk_member_resource_user_id
        UNIQUE (user_id);
