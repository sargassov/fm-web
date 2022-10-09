CREATE TABLE IF NOT EXISTS match (
                                    id bigserial PRIMARY KEY NOT NULL,
                                    id_user BIGINT NOT NULL,
                                    id_home_team BIGINT,
                                    id_away_team BIGINT,
                                    id_stadium BIGINT,
                                    id_tour_day BIGINT,
                                    id_cortage BIGINT,
                                    count_of_tour INTEGER,
                                    impossible_match BOOLEAN,
                                    home_score INTEGER,
                                    away_score INTEGER,
                                    match_passed BOOLEAN
);