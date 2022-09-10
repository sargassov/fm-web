CREATE TABLE IF NOT EXISTS user (
                                     id bigserial PRIMARY KEY NOT NULL,
                                     name VARCHAR(64) NOT NULL,
                                     password TEXT NOT NULL,
                                     about VARCHAR(64) NOT NULL,
                                     user_team_descroption VARCHAR(64) NOT_NULL,
                                     id_team BIGINT NOT NULL
);