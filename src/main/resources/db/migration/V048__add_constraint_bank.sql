ALTER TABLE bank
    ADD CONSTRAINT fk_team FOREIGN KEY (id_team) REFERENCES team(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE bank
    ADD CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user_table(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE bank
    ADD CONSTRAINT fk_day_of_loan FOREIGN KEY (id_day_of_loan) REFERENCES day(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE bank
    ADD CONSTRAINT fk_remains_day FOREIGN KEY (id_remains_day) REFERENCES day(id) ON UPDATE CASCADE ON DELETE RESTRICT;
