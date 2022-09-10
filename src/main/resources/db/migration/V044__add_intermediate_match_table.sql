CREATE TABLE IF NOT EXISTS match (
                                    id bigserial PRIMARY KEY NOT NULL,
                                    id_user BIGINT NOT NULL,
                                    id_home_team BIGINT NOT NULL,
                                    id_away_team BIGINT NOT NULL,
                                    id_stadium BIGINT NOT NULL,
                                    id_tour_day BIGINT NOT NULL,
                                    id_cortage BIGINT,
                                    impossible_match BOOLEAN,
                                    home_score INTEGER NOT NULL,
                                    away_score INTEGER NOT NULL,
                                    match_passed BOOLEAN
);