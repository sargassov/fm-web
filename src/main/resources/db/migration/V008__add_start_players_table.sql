CREATE TABLE IF NOT EXISTS players (
                                id BIGSERIAL PRIMARY KEY NOT NULL,
                                name VARCHAR(32) NOT NULL,
                                natio VARCHAR(8) NOT NULL,
                                gk_able INTEGER NOT NULL,
                                def_able INTEGER NOT NULL,
                                mid_able INTEGER NOT NULL,
                                forw_able INTEGER NOT NULL,
                                price INTEGER NOT NULL,
                                captain_able INTEGER NOT NULL,
                                number INTEGER NOT NULL,
                                strategy_place INTEGER,
                                id_teams BIGINT,
                                year INTEGER NOT NULL,
                                id_positions BIGINT
);