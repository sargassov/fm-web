ALTER TABLE cortage
    ADD CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user_table(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE cortage
    ADD CONSTRAINT fk_team FOREIGN KEY (id_team) REFERENCES team(id) ON UPDATE CASCADE ON DELETE RESTRICT;