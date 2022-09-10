CREATE TABLE IF NOT EXISTS sponsors (
                                 id BIGSERIAL PRIMARY KEY NOT NULL,
                                 name VARCHAR(32) NOT NULL,
                                 day_wage NUMERIC(10,2),
                                 match_wage NUMERIC(10,2),
                                 goal_bonus_wage NUMERIC(10,2),
                                 win_wage NUMERIC(10,2),
                                 deuce_wage NUMERIC(10,2),
                                 contract_bonus_wage NUMERIC(10,2)
);