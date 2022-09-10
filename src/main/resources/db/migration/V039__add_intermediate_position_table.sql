CREATE TABLE IF NOT EXISTS position (
                                         id bigserial PRIMARY KEY NOT NULL,
                                         id_user BIGINT NOT NULL,
                                         title VARCHAR(64) NOT NULL
);