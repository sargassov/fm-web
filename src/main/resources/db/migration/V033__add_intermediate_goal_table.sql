CREATE TABLE IF NOT EXISTS goal (
                                       id bigserial PRIMARY KEY NOT NULL,
                                       id_user BIGINT NOT NULL,
                                       id_team BIGINT NOT NULL,
                                       id_player BIGINT NOT NULL,
                                       minute INTEGER NOT NULL,
                                       id_match BIGINT NOT NULL
);