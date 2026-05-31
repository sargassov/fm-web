CREATE TABLE IF NOT EXISTS cups (
                                       id BIGSERIAL PRIMARY KEY NOT NULL,
                                       team_value int4 NOT NULL,
                                       name VARCHAR(64) NOT NULL
);

INSERT INTO cups (name, team_value)
VALUES ('Кубок России', 16);

