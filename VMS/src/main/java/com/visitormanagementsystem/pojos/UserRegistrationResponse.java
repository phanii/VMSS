package com.visitormanagementsystem.pojos;

/**
 * Created by Phani.Gullapalli on 26/11/2015.
 */
public class UserRegistrationResponse {

    String Id;

    public UserRegistrationResponse(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
