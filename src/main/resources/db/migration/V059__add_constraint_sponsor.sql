ALTER TABLE sponsor
    ADD CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user_table(id) ON UPDATE CASCADE ON DELETE RESTRICT;