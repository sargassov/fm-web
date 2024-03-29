ALTER TABLE goal
    ADD CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user_table(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE goal
    ADD CONSTRAINT fk_team FOREIGN KEY (id_team) REFERENCES team(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE goal
    ADD CONSTRAINT fk_player FOREIGN KEY (id_player) REFERENCES player(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE goal
    ADD CONSTRAINT fk_match FOREIGN KEY (id_match) REFERENCES match(id) ON UPDATE CASCADE ON DELETE RESTRICT;