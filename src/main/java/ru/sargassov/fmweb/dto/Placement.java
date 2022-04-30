package ru.sargassov.fmweb.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class Placement {
    private Long id;
    private String name;
    private List<Role> roles;
}

