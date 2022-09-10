CREATE TABLE IF NOT EXISTS positions (
                                  id BIGSERIAL PRIMARY KEY NOT NULL,
                                  title VARCHAR(16) NOT NULL,
                                  short_title VARCHAR(8) NOT NULL
);