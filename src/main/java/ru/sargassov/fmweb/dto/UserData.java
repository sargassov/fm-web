package ru.sargassov.fmweb.dto;

import lombok.Data;

@Data
public class UserData {
    private String name;
    private String lastname;
    private String homeTown;
    private String teamName;
    private String password;
}
