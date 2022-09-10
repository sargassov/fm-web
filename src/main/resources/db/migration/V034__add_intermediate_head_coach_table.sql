CREATE TABLE IF NOT EXISTS head_coach (
                                    id bigserial PRIMARY KEY NOT NULL,
                                    id_user BIGINT NOT NULL,
                                    name VARCHAR(64) NOT NULL
);