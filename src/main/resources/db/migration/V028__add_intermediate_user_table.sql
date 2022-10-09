CREATE TABLE IF NOT EXISTS user_table (
                                     id bigserial PRIMARY KEY NOT NULL,
                                     name VARCHAR(64), -- TODO добавить not null
                                     password TEXT, -- TODO добавить not null
                                     about VARCHAR(64), -- TODO добавить not null
                                     user_team_description VARCHAR(64),
                                     id_team BIGINT -- TODO добавить not null
);