CREATE TABLE IF NOT EXISTS market (
                                      id bigserial PRIMARY KEY NOT NULL,
                                      id_user BIGINT NOT NULL,
                                      id_start_day BIGINT,
                                      id_team BIGINT,
                                      id_finish_day BIGINT,
                                      market_type varchar(64)
);