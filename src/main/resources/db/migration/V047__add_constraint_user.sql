ALTER TABLE user_table
    ADD CONSTRAINT fk_team FOREIGN KEY (id_team) REFERENCES team(id) ON UPDATE CASCADE ON DELETE RESTRICT;