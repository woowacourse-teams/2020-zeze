CREATE TABLE authority (
   id BIGINT NOT NULL AUTO_INCREMENT,
   role VARCHAR(20),
   PRIMARY KEY (id)
);

INSERT INTO authority (role) values ('ROLE_USER'), ('ROLE_ADMIN');

