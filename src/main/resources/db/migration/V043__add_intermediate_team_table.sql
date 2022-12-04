CREATE TABLE IF NOT EXISTS team (
                                       id bigserial PRIMARY KEY NOT NULL,
                                       id_user BIGINT NOT NULL,
                                       id_league BIGINT NOT NULL,
                                       id_stadium BIGINT,
                                       id_city BIGINT,
                                       id_head_coach BIGINT NOT NULL,
                                       id_sponsor BIGINT,
                                       id_placement BIGINT,
                                       id_team_entity BIGINT NOT NULL,
                                       name VARCHAR(64) NOT NULL,
                                       wealth NUMERIC(6, 2) NOT NULL,
                                       start_wealth NUMERIC(6, 2) NOT NULL,
                                       transfer_expenses NUMERIC(6, 2) NOT NULL,
                                       personal_expenses NUMERIC(6, 2) NOT NULL,
                                       market_expenses NUMERIC(6, 2) NOT NULL,
                                       stadium_expenses NUMERIC(6, 2) NOT NULL,
                                       regular_capacity INTEGER NOT NULL,
                                       temporary_ticket_cost INTEGER NOT NULL,
                                       team_power INTEGER NOT NULL,
                                       change_sponsor BOOLEAN,
                                       games INTEGER NOT NULL,
                                       won INTEGER NOT NULL,
                                       drawn INTEGER NOT NULL,
                                       lost INTEGER NOT NULL,
                                       scored INTEGER NOT NULL,
                                       missed INTEGER NOT NULL,
                                       points INTEGER NOT NULL
);