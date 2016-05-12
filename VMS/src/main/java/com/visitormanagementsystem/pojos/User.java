package com.visitormanagementsystem.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Phani.Gullapalli on 25/11/2015.
 */
public class User {
    @SerializedName("Email")
    String Email;
    @SerializedName("Password")
    String Password;
    @SerializedName("ConfirmPassword")
    String ConfirmPassword;


    @SerializedName("VisitorFirstName")
    String VisitorFirstName;
    @SerializedName("VisitorMiddleName")
    String VisitorMiddleName;
    @SerializedName("VisitorLastName")
    String VisitorLastName;
    @SerializedName("EmployeeID")
    String EmployeeID;
    @SerializedName("Designation")
    String Designation;
    @SerializedName("CompanyName")
    String CompanyName;
    @SerializedName("SectorCompCategory")
    String SectorCompCategory;
    @SerializedName("DOB")
    String DOB;
    @SerializedName("WorkPermitNumber")
    String WorkPermitNumber;
    @SerializedName("VisitVisaNumber")
    String VisitVisaNumber;
    @SerializedName("Id")
    String Id;
    @SerializedName("MobileNumber")
    String MobileNumber;

    @SerializedName("LandlinePhoneNumber")
    String LandlinePhoneNumber;

    @SerializedName("strSpnrNationality")
    String strSpnrNationality;

    @SerializedName("Photo")
    String Photo;

    @SerializedName("EmailConfirmed")
    String EmailConfirmed;
    @SerializedName("Roles")
    List<String> Roles;


    public User(String email, String password, String confirmPassword, String dob, String visitorFirstName, String visitorMiddleName, String visitorLastName, String employeeID, String designation, String companyName, String sectorCompCategory, String workPermitNumber, String visitVisaNumber, String mobileNumber, String landlinePhoneNumber, String strspnrNationality, String photo
                 ) {
        Email = email;
        Password = password;
        ConfirmPassword = confirmPassword;
        DOB = dob;
        VisitorFirstName = visitorFirstName;
        VisitorMiddleName = visitorMiddleName;
        VisitorLastName = visitorLastName;
        EmployeeID = employeeID;
        Designation = designation;
        CompanyName = companyName;
        SectorCompCategory = sectorCompCategory;

        WorkPermitNumber = workPermitNumber;
        VisitVisaNumber = visitVisaNumber;

        MobileNumber = mobileNumber;
        LandlinePhoneNumber = landlinePhoneNumber;
        strSpnrNationality = strspnrNationality;
        Photo = photo;

    }


    public User(String email, String password, String confirmPassword) {
        Email = email;
        Password = password;
        ConfirmPassword = confirmPassword;
    }

    public String getEmailConfirmed() {
        return EmailConfirmed;
    }

    public void setEmailConfirmed(String emailConfirmed) {
        EmailConfirmed = emailConfirmed;
    }

    public List<String> getRoles() {
        return Roles;
    }

    public void setRoles(List<String> roles) {
        Roles = roles;
    }

    public String getStrSpnrNationality() {
        return strSpnrNationality;
    }

    public void setStrSpnrNationality(String strSpnrNationality) {
        this.strSpnrNationality = strSpnrNationality;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getVisitorFirstName() {
        return VisitorFirstName;
    }

    public void setVisitorFirstName(String visitorFirstName) {
        VisitorFirstName = visitorFirstName;
    }

    public String getVisitorMiddleName() {
        return VisitorMiddleName;
    }

    public void setVisitorMiddleName(String visitorMiddleName) {
        VisitorMiddleName = visitorMiddleName;
    }

    public String getVisitorLastName() {
        return VisitorLastName;
    }

    public void setVisitorLastName(String visitorLastName) {
        VisitorLastName = visitorLastName;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getSectorCompCategory() {
        return SectorCompCategory;
    }

    public void setSectorCompCategory(String sectorCompCategory) {
        SectorCompCategory = sectorCompCategory;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getWorkPermitNumber() {
        return WorkPermitNumber;
    }

    public void setWorkPermitNumber(String workPermitNumber) {
        WorkPermitNumber = workPermitNumber;
    }

    public String getVisitVisaNumber() {
        return VisitVisaNumber;
    }

    public void setVisitVisaNumber(String visitVisaNumber) {
        VisitVisaNumber = visitVisaNumber;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getLandlinePhoneNumber() {
        return LandlinePhoneNumber;
    }

    public void setLandlinePhoneNumber(String landlinePhoneNumber) {
        LandlinePhoneNumber = landlinePhoneNumber;
    }
}
