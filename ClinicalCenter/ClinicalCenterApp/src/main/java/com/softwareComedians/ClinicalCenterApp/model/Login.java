package com.softwareComedians.ClinicalCenterApp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String userName;
    private String password;
    private String password2;

    public Login(){}
}
