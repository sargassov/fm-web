ALTER TABLE market
    ADD CONSTRAINT fk_team FOREIGN KEY (id_team) REFERENCES team(id) ON UPDATE RESTRICT ON DELETE RESTRICT;