CREATE TABLE IF NOT EXISTS user_table (
                                     id bigserial PRIMARY KEY NOT NULL,
                                     name VARCHAR(64) NOT NULL,
                                     password TEXT NOT NULL,
                                     about VARCHAR(64) NOT NULL,
                                     user_team_description VARCHAR(64),
                                     youth_academy_visited BOOLEAN,
                                     id_team BIGINT
);