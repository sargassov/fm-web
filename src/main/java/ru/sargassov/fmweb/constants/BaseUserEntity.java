package ru.sargassov.fmweb.constants;

import lombok.Getter;
import lombok.Setter;
import ru.sargassov.fmweb.intermediate_entities.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseUserEntity extends IntermediateEntity {

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
