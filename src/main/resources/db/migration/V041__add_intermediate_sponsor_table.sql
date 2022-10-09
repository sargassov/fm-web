CREATE TABLE IF NOT EXISTS sponsor (
                                    id bigserial PRIMARY KEY NOT NULL,
                                    id_user BIGINT NOT NULL,
                                    id_sponsor_entity BIGINT NOT NULL,
                                    name VARCHAR(64) NOT NULL,
                                    day_wage NUMERIC(6, 2) NOT NULL,
                                    match_wage NUMERIC(6, 2) NOT NULL,
                                    goal_bonus_wage NUMERIC(6, 2) NOT NULL,
                                    win_wage NUMERIC(6, 2) NOT NULL,
                                    deuce_wage NUMERIC(6, 2) NOT NULL,
                                    contract_bonus_wage NUMERIC(6, 2) NOT NULL
);