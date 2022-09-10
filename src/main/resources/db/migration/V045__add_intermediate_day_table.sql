CREATE TABLE IF NOT EXISTS day (
                                     id bigserial PRIMARY KEY NOT NULL,
                                     id_user BIGINT NOT NULL,
                                     'date' date NOT NULL,
                                     is_passed BOOLEAN NOT NULL,
                                     is_present BOOLEAN NOT NULL,
                                     is_match BOOLEAN NOT NULL,
                                     count_of_tour INTEGER NOT NULL,
                                     notes_of_changes VARCHAR(255)[]
);