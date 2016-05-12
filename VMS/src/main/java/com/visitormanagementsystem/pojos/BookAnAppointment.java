package com.visitormanagementsystem.pojos;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Phani.Gullapalli on 30/11/2015.
 */
public class BookAnAppointment {
    @SerializedName("VisitorName")
    String VisitorName;
    @SerializedName("VisitorFirstName")
    String VisitorFirstName;
    @SerializedName("VisitorMiddleName")
    String VisitorMiddleName;
    @SerializedName("VisitorLastName")
    String VisitorLastName;
    @SerializedName("title")
    String title;
    @SerializedName("EmployeeID")
    String EmployeeID;
    @SerializedName("CompanyName")
    String CompanyName;
    @SerializedName("Designation")
    String Designation;
    @SerializedName("Nationality")
    String Nationality;
    @SerializedName("SectorCompCategory")
    String SectorCompCategory;

    @SerializedName("DOB")
    String DOB;
    @SerializedName("Gender")
    String Gender;
    @SerializedName("WorkPermitNumber")
    String WorkPermitNumber;
    @SerializedName("VisitVisaNumber")
    String VisitVisaNumber;
    @SerializedName("NationalID")
    String NationalID;


    @SerializedName("MobileNumber")
    String MobileNumber;
    @SerializedName("LandlinePhoneNumber")
    String LandlinePhoneNumber;
    @SerializedName("EmailAddress")
    String EmailAddress;
    @SerializedName("ReasonForVisit")
    String ReasonForVisit;
    @SerializedName("VisitingDepartmentName")
    String VisitingDepartmentName;
    @SerializedName("VisitingSubDepartmentName")
    String VisitingSubDepartmentName;
    @SerializedName("ContactPersonEmpID")
    String ContactPersonEmpID;
    @SerializedName("ContactPersonName")
    String ContactPersonName;
    @SerializedName("ContactNumber")
    String ContactNumber;

    @SerializedName("ContactPersonEmailId")
    String ContactPersonEmailId;
    @SerializedName("PersonalBelongings")
    String PersonalBelongings;
    @SerializedName("MeetingPrerequisite")
    String MeetingPrerequisite;

    @SerializedName("VisitDate")
    String VisitDate;
    @SerializedName("StartDate")
    String StartDate;
    @SerializedName("EndDate")
    String EndDate;
    @SerializedName("StartTime")
    String StartTime;
    @SerializedName("EndTime")
    String EndTime;
    @SerializedName("NationalIDType")
    String NationalIDType;
    @SerializedName("contactPersonEmailId")
    String contactPersonEmailId;


    @SerializedName("RecurringAppointment")// recurring checked or not
            Boolean RecurringAppointment;
    @SerializedName("RecurringType")// recurring pattrn
            String RecurringType;
    @SerializedName("RecurringInput")//recurring input comma separated values
            String RecurringInput;
    @SerializedName("VisitorCheckInTime")
    String VisitorCheckInTime;
    @SerializedName("VisitorCheckOutTime")
    String VisitorCheckOutTime;
    @SerializedName("RecurringFromDateTime")
    String RecurringFromDateTime;
    @SerializedName("RecurringToDateTime")
    String RecurringToDateTime;
    @SerializedName("AppointmentStatusCode")
    String AppointmentStatusCode;
    @SerializedName("AppointmentStatusName")
    String AppointmentStatusName;
    @SerializedName("Days")
    String Days;
    @SerializedName("Duration")
    String Duration;
    @SerializedName("AccompanyWith")
    String AccompanyWith;
    @SerializedName("Photo")
    String Photo;


    public BookAnAppointment() {
    }

    /**
     * For screen1 of Schedule
     *
     * @param visitorName
     * @param visitorFirstName
     * @param visitorMiddleName
     * @param visitorLastName
     * @param title
     * @param employeeID
     * @param companyName
     * @param designation
     * @param nationality
     * @param sectorCompCategory
     * @param DOB
     * @param gender
     * @param workPermitNumber
     * @param visitVisaNumber
     * @param mobileNumber
     * @param landlinePhoneNumber
     * @param nationalIDType
     * @param nationalID
     */
    public BookAnAppointment(String visitorName, String visitorFirstName, String visitorMiddleName, String visitorLastName, String title, String employeeID, String companyName, String designation, String nationality, String sectorCompCategory, String DOB, String gender, String workPermitNumber, String visitVisaNumber, String mobileNumber, String landlinePhoneNumber, String nationalIDType, String nationalID) {
        VisitorName = visitorName;
        VisitorFirstName = visitorFirstName;
        VisitorMiddleName = visitorMiddleName;
        VisitorLastName = visitorLastName;
        this.title = title;
        EmployeeID = employeeID;
        CompanyName = companyName;
        Designation = designation;
        Nationality = nationality;
        SectorCompCategory = sectorCompCategory;
        this.DOB = DOB;
        Gender = gender;
        WorkPermitNumber = workPermitNumber;
        VisitVisaNumber = visitVisaNumber;
        MobileNumber = mobileNumber;
        LandlinePhoneNumber = landlinePhoneNumber;
        NationalIDType = nationalIDType;
        NationalID = nationalID;
    }

    /**
     * For screen2 of schedule
     *
     * @param contactPersonEmpID
     * @param contactPersonName
     * @param visitingDepartmentName
     * @param visitingSubDepartmentName
     * @param contactNumber
     * @param contactPersonEmailId
     * @param reasonForVisit
     * @param personalBelongings
     * @param meetingPrerequisite
     */
    public BookAnAppointment(String contactPersonEmpID, String contactPersonName, String visitingDepartmentName, String visitingSubDepartmentName, String contactNumber, String contactPersonEmailId, String reasonForVisit, String personalBelongings, String meetingPrerequisite) {
        ContactPersonEmpID = contactPersonEmpID;
        ContactPersonName = contactPersonName;
        VisitingDepartmentName = visitingDepartmentName;
        VisitingSubDepartmentName = visitingSubDepartmentName;
        ContactNumber = contactNumber;
        ContactPersonEmailId = contactPersonEmailId;
        ReasonForVisit = reasonForVisit;
        PersonalBelongings = personalBelongings;
        MeetingPrerequisite = meetingPrerequisite;
    }

    public BookAnAppointment(String visitorName, String visitorFirstName, String visitorMiddleName, String visitorLastName, String title, String employeeID,
                             String companyName, String designation, String nationality, String sectorCompCategory, String DOB, String gender, String workPermitNumber,
                             String visitVisaNumber, String mobileNumber, String landlinePhoneNumber, String nationalIDType, String nationalID, String contactPersonEmpID,
                             String contactPersonName, String visitingDepartmentName, String visitingSubDepartmentName, String contactNumber, String contactPersonEmailId,
                             String reasonForVisit, String personalBelongings, String meetingPrerequisite, Boolean RecurringAppointment, String RecurringType, String RecurringInput,
                             String StartDate, String EndDate, String StartTime, String EndTime, String Days, String Duration, String photo)

    {
        VisitorName = visitorName;
        VisitorFirstName = visitorFirstName;
        VisitorMiddleName = visitorMiddleName;
        VisitorLastName = visitorLastName;
        this.title = title;
        EmployeeID = employeeID;
        CompanyName = companyName;
        Designation = designation;
        Nationality = nationality;
        SectorCompCategory = sectorCompCategory;
        this.DOB = DOB;
        Gender = gender;
        WorkPermitNumber = workPermitNumber;
        VisitVisaNumber = visitVisaNumber;
        MobileNumber = mobileNumber;
        LandlinePhoneNumber = landlinePhoneNumber;
        NationalIDType = nationalIDType;
        NationalID = nationalID;

        ContactPersonEmpID = contactPersonEmpID;
        ContactPersonName = contactPersonName;
        VisitingDepartmentName = visitingDepartmentName;
        VisitingSubDepartmentName = visitingSubDepartmentName;
        ContactNumber = contactNumber;
        ContactPersonEmailId = contactPersonEmailId;
        ReasonForVisit = reasonForVisit;
        PersonalBelongings = personalBelongings;
        MeetingPrerequisite = meetingPrerequisite;


        this.RecurringAppointment = RecurringAppointment;
        this.RecurringType = RecurringType;
        this.RecurringInput = RecurringInput;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.Days = Days;
        this.Duration = Duration;
        Photo = photo;

    }

    /*   public BookAnAppointment(String visitorName, String visitorFirstName, String visitorMiddleName, String visitorLastName, String mobileNumber, String workPermitNumber, String contactPersonEmpID, String contactPersonName, String contactNumber, String contactPersonEmailId) {
           VisitorName = visitorName;
           VisitorFirstName = visitorFirstName;
           VisitorMiddleName = visitorMiddleName;
           VisitorLastName = visitorLastName;
           MobileNumber = mobileNumber;
           WorkPermitNumber = workPermitNumber;
           ContactPersonEmpID = contactPersonEmpID;
           ContactPersonName = contactPersonName;
           ContactNumber = contactNumber;
           ContactPersonEmailId = contactPersonEmailId;
       }
   */
    public BookAnAppointment(String visitorName, String visitorFirstName, String visitorMiddleName, String visitorLastName, String title, String employeeID, String companyName,
                             String designation, String nationality, String sectorCompCategory, String DOB, String gender, String workPermitNumber, String visitVisaNumber,
                             String nationalID, String startDate, String endDate, String startTime, String endTime, String mobileNumber, String landlinePhoneNumber, String emailAddress,
                             String reasonForVisit, String visitingDepartmentName, String visitingSubDepartmentName, String contactPersonEmpID, String contactPersonName, String contactNumber,
                             String contactPersonEmailId, String visitDate, String visitorCheckInTime, String visitorCheckOutTime, Boolean recurringAppointment, String recurringFromDateTime,
                             String recurringToDateTime, String nationalIDType, String recurringType, String recurringInput, String appointmentStatusCode, String appointmentStatusName,
                             String personalBelongings, String meetingPrerequisite, String days, String duration, String accompanyWith, String photo) {
        VisitorName = visitorName;
        VisitorFirstName = visitorFirstName;
        VisitorMiddleName = visitorMiddleName;
        VisitorLastName = visitorLastName;
        this.title = title;
        EmployeeID = employeeID;
        CompanyName = companyName;
        Designation = designation;
        Nationality = nationality;
        SectorCompCategory = sectorCompCategory;
        this.DOB = DOB;
        Gender = gender;
        WorkPermitNumber = workPermitNumber;
        VisitVisaNumber = visitVisaNumber;
        NationalID = nationalID;
        StartDate = startDate;
        EndDate = endDate;
        StartTime = startTime;
        EndTime = endTime;
        MobileNumber = mobileNumber;
        LandlinePhoneNumber = landlinePhoneNumber;
        EmailAddress = emailAddress;
        ReasonForVisit = reasonForVisit;
        VisitingDepartmentName = visitingDepartmentName;
        VisitingSubDepartmentName = visitingSubDepartmentName;
        ContactPersonEmpID = contactPersonEmpID;
        ContactPersonName = contactPersonName;
        ContactNumber = contactNumber;
        ContactPersonEmailId = contactPersonEmailId;
        VisitDate = visitDate;
        VisitorCheckInTime = visitorCheckInTime;
        VisitorCheckOutTime = visitorCheckOutTime;
        RecurringAppointment = recurringAppointment;
        RecurringFromDateTime = recurringFromDateTime;
        RecurringToDateTime = recurringToDateTime;
        NationalIDType = nationalIDType;
        RecurringType = recurringType;
        RecurringInput = recurringInput;
        AppointmentStatusCode = appointmentStatusCode;
        AppointmentStatusName = appointmentStatusName;
        PersonalBelongings = personalBelongings;
        MeetingPrerequisite = meetingPrerequisite;
        Days = days;
        Duration = duration;
        AccompanyWith = accompanyWith;
        Photo = photo;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String visitorName) {
        VisitorName = visitorName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }


    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
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

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getReasonForVisit() {
        return ReasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        ReasonForVisit = reasonForVisit;
    }

    public String getVisitingDepartmentName() {
        return VisitingDepartmentName;
    }

    public void setVisitingDepartmentName(String visitingDepartmentName) {
        VisitingDepartmentName = visitingDepartmentName;
    }

    public String getVisitingSubDepartmentName() {
        return VisitingSubDepartmentName;
    }

    public void setVisitingSubDepartmentName(String visitingSubDepartmentName) {
        VisitingSubDepartmentName = visitingSubDepartmentName;
    }

    public String getContactPersonEmpID() {
        return ContactPersonEmpID;
    }

    public void setContactPersonEmpID(String contactPersonEmpID) {
        ContactPersonEmpID = contactPersonEmpID;
    }

    public String getContactPersonName() {
        return ContactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        ContactPersonName = contactPersonName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getContactPersonEmailId() {
        return ContactPersonEmailId;
    }

    public void setContactPersonEmailId(String contactPersonEmailId) {
        ContactPersonEmailId = contactPersonEmailId;
    }

    public String getVisitDate() {
        return VisitDate;
    }

    public void setVisitDate(String visitDate) {
        VisitDate = visitDate;
    }

    public String getVisitorCheckInTime() {
        return VisitorCheckInTime;
    }

    public void setVisitorCheckInTime(String visitorCheckInTime) {
        VisitorCheckInTime = visitorCheckInTime;
    }

    public String getVisitorCheckOutTime() {
        return VisitorCheckOutTime;
    }

    public void setVisitorCheckOutTime(String visitorCheckOutTime) {
        VisitorCheckOutTime = visitorCheckOutTime;
    }

    public Boolean getRecurringAppointment() {
        return RecurringAppointment;
    }

    public void setRecurringAppointment(Boolean recurringAppointment) {
        RecurringAppointment = recurringAppointment;
    }

    public String getRecurringFromDateTime() {
        return RecurringFromDateTime;
    }

    public void setRecurringFromDateTime(String recurringFromDateTime) {
        RecurringFromDateTime = recurringFromDateTime;
    }

    public String getRecurringToDateTime() {
        return RecurringToDateTime;
    }

    public void setRecurringToDateTime(String recurringToDateTime) {
        RecurringToDateTime = recurringToDateTime;
    }

    public String getNationalIDType() {
        return NationalIDType;
    }

    public void setNationalIDType(String nationalIDType) {
        NationalIDType = nationalIDType;
    }

    public String getRecurringType() {
        return RecurringType;
    }

    public void setRecurringType(String recurringType) {
        RecurringType = recurringType;
    }

    public String getRecurringInput() {
        return RecurringInput;
    }

    public void setRecurringInput(String recurringInput) {
        RecurringInput = recurringInput;
    }

    public String getAppointmentStatusCode() {
        return AppointmentStatusCode;
    }

    public void setAppointmentStatusCode(String appointmentStatusCode) {
        AppointmentStatusCode = appointmentStatusCode;
    }

    public String getAppointmentStatusName() {
        return AppointmentStatusName;
    }

    public void setAppointmentStatusName(String appointmentStatusName) {
        AppointmentStatusName = appointmentStatusName;
    }

    public String getPersonalBelongings() {
        return PersonalBelongings;
    }

    public void setPersonalBelongings(String personalBelongings) {
        PersonalBelongings = personalBelongings;
    }

    public String getMeetingPrerequisite() {
        return MeetingPrerequisite;
    }

    public void setMeetingPrerequisite(String meetingPrerequisite) {
        MeetingPrerequisite = meetingPrerequisite;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getAccompanyWith() {
        return AccompanyWith;
    }

    public void setAccompanyWith(String accompanyWith) {
        AccompanyWith = accompanyWith;
    }
}