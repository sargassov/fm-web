package ru.sargassov.fmweb.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserData {
    private String name;
    private String lastname;
    private String homeTown;
    private String teamName;
}
