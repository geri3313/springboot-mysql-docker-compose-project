package net.codejava.model;

import java.util.Date;

import lombok.Data;

@Data
public class Profile {
    private String firstName;
    private String lastName;
    private String fathersName;
    private String phoneNumber;
    private Date birthday;
    private String email;
    private String password;

}
