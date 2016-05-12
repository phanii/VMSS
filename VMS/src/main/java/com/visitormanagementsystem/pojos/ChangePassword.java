package com.visitormanagementsystem.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Phani.Gullapalli on 01/12/2015.
 */
public class ChangePassword {
    @SerializedName("OldPassword")
    String OldPassword;
    @SerializedName("Email")
    String Email;
    @SerializedName("NewPassword")
    String NewPassword;
    @SerializedName("ConfirmPassword")
    String ConfirmPassword;

    public ChangePassword(String oldPassword, String email, String newPassword, String confirmPassword) {
        OldPassword = oldPassword;
        Email = email;
        NewPassword = newPassword;
        ConfirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }
}
