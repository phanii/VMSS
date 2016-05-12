package com.visitormanagementsystem.pojos;

/**
 * Created by Phani.Gullapalli on 07/12/2015.
 */
public class AppointmentListByVisitorId {
    String VisitorName;
    String MobileNumber;
    String EmailAddress;
    String Photo;
    String AppointmentID;
    String PublicAppointmentID;

    public AppointmentListByVisitorId(String VisitorName, String appointmentID, String PublicAppointmentID, String mobileNumber, String emailAddress, String photo) {
        this.VisitorName = VisitorName;
        AppointmentID = appointmentID;
        this.PublicAppointmentID = PublicAppointmentID;
        MobileNumber = mobileNumber;
        EmailAddress = emailAddress;
        Photo = photo;
    }

    public String getPublicAppointmentID() {
        return PublicAppointmentID;
    }

    public void setPublicAppointmentID(String publicAppointmentID) {
        PublicAppointmentID = publicAppointmentID;
    }

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String visitorName) {
        VisitorName = visitorName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
