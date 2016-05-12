package com.visitormanagementsystem.pojos;

/**
 * Created by Phani.Gullapalli on 26/11/2015.
 */

public class Visitordetails {
    private String SecurityQuestions;

    private String VisitVisaNumber;

    private String DOB;

    private String Password;

    private String ModifyDate;

    private String EmployeeID;

    private String ModifyBy;

    private String NationalIDType;

    private String SecurityAnswers;

    private String VisitorLastName;

    private String EmailAddress;

    private String VisitorMiddleName;

    private String MobileNumber;

    private String VisitorFirstName;

    private String WorkPermitNumber;

    private String VisitorName;

    private String UserNameID;

    private String VisitorId;

    private String NationalID;

    private String CompanyName;

    private String Photo;

    private String Title;

    private String Nationality;

    private String SectorCompCategory;

    private String LandlinePhoneNumber;

    private String Gender;

    private String CreatedDate;

    private String CreatedBy;

    private String IsDeleted;

    private String Id;

    private String Designation;


    public Visitordetails() {
    }

    public Visitordetails(String VisitorName,String Id, String VisitorId, String employeeID, String companyName, String designation, String nationality, String sectorCompCategory, String DOB, String gender, String visitVisaNumber, String workPermitNumber, String nationalIDType, String nationalID, String mobileNumber, String landlinePhoneNumber, String photo) {
       this.VisitorName=VisitorName;
        this.Id = Id;
        this.VisitorId = VisitorId;
        EmployeeID = employeeID;
        CompanyName = companyName;
        Designation = designation;
        Nationality = nationality;
        SectorCompCategory = sectorCompCategory;
        this.DOB = DOB;
        Gender = gender;
        VisitVisaNumber = visitVisaNumber;
        WorkPermitNumber = workPermitNumber;
        NationalIDType = nationalIDType;
        NationalID = nationalID;
        MobileNumber = mobileNumber;
        LandlinePhoneNumber = landlinePhoneNumber;
        Photo = photo;
    }


    public String getSecurityQuestions() {
        return SecurityQuestions;
    }

    public void setSecurityQuestions(String SecurityQuestions) {
        this.SecurityQuestions = SecurityQuestions;
    }

    public String getVisitVisaNumber() {
        return VisitVisaNumber;
    }

    public void setVisitVisaNumber(String VisitVisaNumber) {
        this.VisitVisaNumber = VisitVisaNumber;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String ModifyDate) {
        this.ModifyDate = ModifyDate;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getModifyBy() {
        return ModifyBy;
    }

    public void setModifyBy(String ModifyBy) {
        this.ModifyBy = ModifyBy;
    }

    public String getNationalIDType() {
        return NationalIDType;
    }

    public void setNationalIDType(String NationalIDType) {
        this.NationalIDType = NationalIDType;
    }

    public String getSecurityAnswers() {
        return SecurityAnswers;
    }

    public void setSecurityAnswers(String SecurityAnswers) {
        this.SecurityAnswers = SecurityAnswers;
    }

    public String getVisitorLastName() {
        return VisitorLastName;
    }

    public void setVisitorLastName(String VisitorLastName) {
        this.VisitorLastName = VisitorLastName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String EmailAddress) {
        this.EmailAddress = EmailAddress;
    }

    public String getVisitorMiddleName() {
        return VisitorMiddleName;
    }

    public void setVisitorMiddleName(String VisitorMiddleName) {
        this.VisitorMiddleName = VisitorMiddleName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

    public String getVisitorFirstName() {
        return VisitorFirstName;
    }

    public void setVisitorFirstName(String VisitorFirstName) {
        this.VisitorFirstName = VisitorFirstName;
    }

    public String getWorkPermitNumber() {
        return WorkPermitNumber;
    }

    public void setWorkPermitNumber(String WorkPermitNumber) {
        this.WorkPermitNumber = WorkPermitNumber;
    }

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String VisitorName) {
        this.VisitorName = VisitorName;
    }

    public String getUserNameID() {
        return UserNameID;
    }

    public void setUserNameID(String UserNameID) {
        this.UserNameID = UserNameID;
    }

    public String getVisitorId() {
        return VisitorId;
    }

    public void setVisitorId(String VisitorId) {
        this.VisitorId = VisitorId;
    }

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String NationalID) {
        this.NationalID = NationalID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public String getSectorCompCategory() {
        return SectorCompCategory;
    }

    public void setSectorCompCategory(String SectorCompCategory) {
        this.SectorCompCategory = SectorCompCategory;
    }

    public String getLandlinePhoneNumber() {
        return LandlinePhoneNumber;
    }

    public void setLandlinePhoneNumber(String LandlinePhoneNumber) {
        this.LandlinePhoneNumber = LandlinePhoneNumber;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    @Override
    public String toString() {
        return "Visitordetails [SecurityQuestions = " + SecurityQuestions + ", VisitVisaNumber = " + VisitVisaNumber + ", DOB = " + DOB + ", Password = " + Password + ", ModifyDate = " + ModifyDate + ", EmployeeID = " + EmployeeID + ", ModifyBy = " + ModifyBy + ", NationalIDType = " + NationalIDType + ", SecurityAnswers = " + SecurityAnswers + ", VisitorLastName = " + VisitorLastName + ", EmailAddress = " + EmailAddress + ", VisitorMiddleName = " + VisitorMiddleName + ", MobileNumber = " + MobileNumber + ", VisitorFirstName = " + VisitorFirstName + ", WorkPermitNumber = " + WorkPermitNumber + ", VisitorName = " + VisitorName + ", UserNameID = " + UserNameID + ", VisitorId = " + VisitorId + ", NationalID = " + NationalID + ", CompanyName = " + CompanyName + ", Photo = " + Photo + ", Title = " + Title + ", Nationality = " + Nationality + ", SectorCompCategory = " + SectorCompCategory + ", LandlinePhoneNumber = " + LandlinePhoneNumber + ", Gender = " + Gender + ", CreatedDate = " + CreatedDate + ", CreatedBy = " + CreatedBy + ", IsDeleted = " + IsDeleted + ", Id = " + Id + ", Designation = " + Designation + "]";
    }
}
