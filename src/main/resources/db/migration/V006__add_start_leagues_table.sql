CREATE TABLE IF NOT EXISTS leagues (
                                id BIGSERIAL PRIMARY KEY NOT NULL,
                                team_value int4 NOT NULL,
                                name VARCHAR(64) NOT NULL
);

INSERT INTO leagues (name, team_value)
VALUES ('Российская Футбольная Премьер Лига', 16);