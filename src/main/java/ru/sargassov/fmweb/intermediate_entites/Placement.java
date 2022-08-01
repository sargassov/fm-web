package ru.sargassov.fmweb.intermediate_entites;


import lombok.Data;

import java.util.List;

@Data
public class Placement {
    private Long id;
    private String name;
    private List<Role> roles;
}

