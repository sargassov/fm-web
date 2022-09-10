CREATE TABLE IF NOT EXISTS city (
                                    id bigserial PRIMARY KEY NOT NULL,
                                    id_user BIGINT NOT NULL,
                                    name VARCHAR(255) NOT NULL,
                                    id_league BIGINT NOT NULL,
);