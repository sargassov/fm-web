CREATE TABLE IF NOT EXISTS player (
                                         id bigserial PRIMARY KEY NOT NULL,
                                         id_user BIGINT NOT NULL,
                                         name VARCHAR(64) NOT NULL,
                                         natio VARCHAR(64) NOT NULL,
                                         gk_able INTEGER NOT NULL,
                                         def_able INTEGER NOT NULL,
                                         mid_able INTEGER NOT NULL,
                                         forw_able INTEGER NOT NULL,
                                         captain_able INTEGER NOT NULL,
                                         birth_year INTEGER NOT NULL,
                                         price NUMERIC(6, 2) NOT NULL,
                                         power INTEGER NOT NULL,
                                         id_position BIGINT NOT NULL,
                                         id_team BIGINT NOT NULL,
                                         tire INTEGER NOT NULL,
                                         time_before_treat INTEGER NOT NULL,
                                         training_able INTEGER NOT NULL,
                                         training_balance INTEGER NOT NULL,
                                         is_injury BOOLEAN,
                                         is_first_eleven BOOLEAN,
                                         is_captain BOOLEAN

);