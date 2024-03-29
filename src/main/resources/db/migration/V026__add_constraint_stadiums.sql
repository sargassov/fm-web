ALTER TABLE stadiums
    ADD CONSTRAINT fk_teams FOREIGN KEY (id_teams) REFERENCES teams(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE stadiums
    ADD CONSTRAINT fk_leagues FOREIGN KEY (id_leagues) REFERENCES leagues(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE stadiums
    ADD CONSTRAINT fk_cities FOREIGN KEY (id_cities) REFERENCES cities(id) ON UPDATE RESTRICT ON DELETE RESTRICT;