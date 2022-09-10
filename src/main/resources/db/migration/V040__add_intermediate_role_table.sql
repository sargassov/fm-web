CREATE TABLE IF NOT EXISTS role (
                                        id bigserial PRIMARY KEY NOT NULL,
                                        id_user BIGINT NOT NULL,
                                        title VARCHAR(64) NOT NULL,
                                        id_team BIGINT NOT NULL,
                                        id_player BIGINT NOT NULL,
                                        pos_number INTEGER NOT NULL
);