CREATE TABLE IF NOT EXISTS coach (
                                    id bigserial PRIMARY KEY NOT NULL,
                                    id_user BIGINT NOT NULL,
                                    count INTEGER NOT NULL,
                                    id_team BIGINT,
                                    id_position BIGINT NOT NULL,
                                    coach_type VARCHAR(255) NOT NULL,
                                    coach_program VARCHAR(255) NOT NULL,
                                    id_player_on_training BIGINT,
                                    price NUMERIC(7, 2),
                                    training_able INTEGER
);