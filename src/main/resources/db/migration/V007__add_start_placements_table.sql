CREATE TABLE IF NOT EXISTS placements (
                                   id BIGSERIAL NOT NULL,
                                   description VARCHAR(16) NOT NULL,
                                   roles VARCHAR(16)[] NOT NULL
);