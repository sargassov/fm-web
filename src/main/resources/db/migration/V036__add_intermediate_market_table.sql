CREATE TABLE IF NOT EXISTS market (
                                      id bigserial PRIMARY KEY NOT NULL,
                                      id_user BIGINT NOT NULL,
                                      name VARCHAR(64) NOT NULL,
                                      id_start_day BIGINT,
                                      id_finish_day BIGINT
);