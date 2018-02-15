package com.capgemini.hackaton.hackaton2018.retrofit;

import java.io.Serializable;

/**
 * Created by MARBEL on 15/02/2018.
 */

public class DoorDTO {

    private String username;
    private String password;

    public DoorDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
