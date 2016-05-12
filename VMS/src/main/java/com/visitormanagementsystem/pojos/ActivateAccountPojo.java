package com.visitormanagementsystem.pojos;

/**
 * Created by Phani.Gullapalli on 04/12/2015.
 */
public class ActivateAccountPojo {
    String Email;

    public ActivateAccountPojo(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
