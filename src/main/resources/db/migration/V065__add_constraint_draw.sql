ALTER TABLE draw
    ADD CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES user(id) ON UPDATE RESTRICT ON DELETE RESTRICT;