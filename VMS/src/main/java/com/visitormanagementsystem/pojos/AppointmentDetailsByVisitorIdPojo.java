package com.visitormanagementsystem.pojos;

/**
 * Created by Phani.Gullapalli on 08/12/2015.
 */
public class AppointmentDetailsByVisitorIdPojo {
    private Integer Id;
    private String VisitorName;
    private String EmployeeID;
    private String CompanyName;
    private String Designation;
    private String Nationality;
    private String SectorCompCategory;
    private String Photo;
    private String DOB;
    private String Gender;
    private String StartDate;
    private String EndDate;
    private String StartTime;
    private String EndTime;
    private String Duration;
    private String Days;
    private String RecurringType;
    private String RecurringInput;
    private String VisitorCheckInTime;
    private String VisitorCheckOutTime;
    private Boolean RecurringAppointment;
    private String RecurringFromDateTime;
    private String RecurringToDateTime;
    private String ApprovedBy;
    private String ApproveDate;
    private String ApprovalComments;
    private String AppointmentStatusName;
    private String AppointmentID;
    private String AccompanyWith;
    private String MeetingPrerequisite;
    private String PersonalBelongings;
private String ContactMobileNumber;

    public String getContactMobileNumber() {
        return ContactMobileNumber;
    }

    public void setContactMobileNumber(String contactMobileNumber) {
        ContactMobileNumber = contactMobileNumber;
    }

    public String getAccompanyWith() {
        return AccompanyWith;
    }

    public void setAccompanyWith(String accompanyWith) {
        AccompanyWith = accompanyWith;
    }

    public String getMeetingPrerequisite() {
        return MeetingPrerequisite;
    }

    public void setMeetingPrerequisite(String meetingPrerequisite) {
        MeetingPrerequisite = meetingPrerequisite;
    }

    public String getPersonalBelongings() {
        return PersonalBelongings;
    }

    public void setPersonalBelongings(String personalBelongings) {
        PersonalBelongings = personalBelongings;
    }

/*
    public AppointmentDetailsByVisitorIdPojo(Integer id, String visitorName, String employeeID, String companyName, String designation,
                                             String nationality, String sectorCompCategory, String photo, String DOB, String gender,
                                             String startDate, String endDate, String startTime, String endTime, String duraion, String days, String recurringType,
                                             String recurringInput, String visitorCheckInTime, String visitorCheckOutTime, Boolean recurringAppointment,
                                             String recurringFromDateTime, String recurringToDateTime, String approvedBy, String approveDate,
                                             String approvalComments, String appointmentStatusName) {
        Id = id;
        VisitorName = visitorName;
        EmployeeID = employeeID;
        CompanyName = companyName;
        Designation = designation;
        Nationality = nationality;
        SectorCompCategory = sectorCompCategory;
        Photo = photo;
        this.DOB = DOB;
        Gender = gender;
        StartDate = startDate;
        EndDate = endDate;
        StartTime = startTime;
        EndTime = endTime;
        Duration = duraion;
        Days = days;
        RecurringType = recurringType;
        RecurringInput = recurringInput;
        VisitorCheckInTime = visitorCheckInTime;
        VisitorCheckOutTime = visitorCheckOutTime;
        RecurringAppointment = recurringAppointment;
        RecurringFromDateTime = recurringFromDateTime;
        RecurringToDateTime = recurringToDateTime;
        ApprovedBy = approvedBy;
        ApproveDate = approveDate;
        ApprovalComments = approvalComments;
        AppointmentStatusName = appointmentStatusName;
    }*/

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public AppointmentDetailsByVisitorIdPojo(String apid, String visitorName, String employeeID, String companyName, String designation,
                                             String nationality, String sectorCompCategory, String photo, String DOB, String gender,
                                             String startDate, String endDate, String startTime, String endTime, String duraion, String days, String recurringType,
                                             String recurringInput, String visitorCheckInTime, String visitorCheckOutTime, Boolean recurringAppointment,
                                             String recurringFromDateTime, String recurringToDateTime, String approvedBy, String approveDate,
                                             String approvalComments, String appointmentStatusName, String accompanyWith, String meetingPrerequisite, String personalBelongings) {

        AppointmentID = apid;
        VisitorName = visitorName;
        EmployeeID = employeeID;
        CompanyName = companyName;
        Designation = designation;
        Nationality = nationality;
        SectorCompCategory = sectorCompCategory;
        Photo = photo;
        this.DOB = DOB;
        Gender = gender;
        StartDate = startDate;
        EndDate = endDate;
        StartTime = startTime;
        EndTime = endTime;
        Duration = duraion;
        Days = days;
        RecurringType = recurringType;
        RecurringInput = recurringInput;
        VisitorCheckInTime = visitorCheckInTime;
        VisitorCheckOutTime = visitorCheckOutTime;
        RecurringAppointment = recurringAppointment;
        RecurringFromDateTime = recurringFromDateTime;
        RecurringToDateTime = recurringToDateTime;
        ApprovedBy = approvedBy;
        ApproveDate = approveDate;
        ApprovalComments = approvalComments;
        AppointmentStatusName = appointmentStatusName;
        AccompanyWith = accompanyWith;
        MeetingPrerequisite = meetingPrerequisite;
        ContactMobileNumber = personalBelongings;
    }

    public AppointmentDetailsByVisitorIdPojo(String apid, String visitorName, String employeeID, String companyName, String designation,
                                             String nationality, String sectorCompCategory, String photo, String DOB, String gender,
                                             String startDate, String endDate, String startTime, String endTime, String duraion, String days, String recurringType,
                                             String recurringInput, String visitorCheckInTime, String visitorCheckOutTime, Boolean recurringAppointment,
                                             String recurringFromDateTime, String recurringToDateTime, String approvedBy, String approveDate,
                                             String approvalComments, String appointmentStatusName) {

        AppointmentID = apid;
        VisitorName = visitorName;
        EmployeeID = employeeID;
        CompanyName = companyName;
        Designation = designation;
        Nationality = nationality;
        SectorCompCategory = sectorCompCategory;
        Photo = photo;
        this.DOB = DOB;
        Gender = gender;
        StartDate = startDate;
        EndDate = endDate;
        StartTime = startTime;
        EndTime = endTime;
        Duration = duraion;
        Days = days;
        RecurringType = recurringType;
        RecurringInput = recurringInput;
        VisitorCheckInTime = visitorCheckInTime;
        VisitorCheckOutTime = visitorCheckOutTime;
        RecurringAppointment = recurringAppointment;
        RecurringFromDateTime = recurringFromDateTime;
        RecurringToDateTime = recurringToDateTime;
        ApprovedBy = approvedBy;
        ApproveDate = approveDate;
        ApprovalComments = approvalComments;
        AppointmentStatusName = appointmentStatusName;
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

    /**
     * @return The Id
     */
    public Integer getId() {
        return Id;
    }

    /**
     * @param Id The Id
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     * @return The VisitorName
     */
    public String getVisitorName() {
        return VisitorName;
    }

    /**
     * @param VisitorName The VisitorName
     */
    public void setVisitorName(String VisitorName) {
        this.VisitorName = VisitorName;
    }

    /**
     * @return The EmployeeID
     */
    public String getEmployeeID() {
        return EmployeeID;
    }

    /**
     * @param EmployeeID The EmployeeID
     */
    public void setEmployeeID(String EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    /**
     * @return The CompanyName
     */
    public String getCompanyName() {
        return CompanyName;
    }

    /**
     * @param CompanyName The CompanyName
     */
    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    /**
     * @return The Designation
     */
    public String getDesignation() {
        return Designation;
    }

    /**
     * @param Designation The Designation
     */
    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    /**
     * @return The Nationality
     */
    public String getNationality() {
        return Nationality;
    }

    /**
     * @param Nationality The Nationality
     */
    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    /**
     * @return The SectorCompCategory
     */
    public String getSectorCompCategory() {
        return SectorCompCategory;
    }

    /**
     * @param SectorCompCategory The SectorCompCategory
     */
    public void setSectorCompCategory(String SectorCompCategory) {
        this.SectorCompCategory = SectorCompCategory;
    }

    /**
     * @return The Photo
     */
    public String getPhoto() {
        return Photo;
    }

    /**
     * @param Photo The Photo
     */
    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    /**
     * @return The DOB
     */
    public String getDOB() {
        return DOB;
    }

    /**
     * @param DOB The DOB
     */
    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    /**
     * @return The Gender
     */
    public String getGender() {
        return Gender;
    }

    /**
     * @param Gender The Gender
     */
    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    /**
     * @return The StartDate
     */
    public String getStartDate() {
        return StartDate;
    }

    /**
     * @param StartDate The StartDate
     */
    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    /**
     * @return The EndDate
     */
    public String getEndDate() {
        return EndDate;
    }

    /**
     * @param EndDate The EndDate
     */
    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    /**
     * @return The StartTime
     */
    public String getStartTime() {
        return StartTime;
    }

    /**
     * @param StartTime The StartTime
     */
    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    /**
     * @return The EndTime
     */
    public String getEndTime() {
        return EndTime;
    }

    /**
     * @param EndTime The EndTime
     */
    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    /**
     * @return The RecurringType
     */
    public String getRecurringType() {
        return RecurringType;
    }

    /**
     * @param RecurringType The RecurringType
     */
    public void setRecurringType(String RecurringType) {
        this.RecurringType = RecurringType;
    }

    /**
     * @return The RecurringInput
     */
    public String getRecurringInput() {
        return RecurringInput;
    }

    /**
     * @param RecurringInput The RecurringInput
     */
    public void setRecurringInput(String RecurringInput) {
        this.RecurringInput = RecurringInput;
    }

    /**
     * @return The VisitorCheckInTime
     */
    public String getVisitorCheckInTime() {
        return VisitorCheckInTime;
    }

    /**
     * @param VisitorCheckInTime The VisitorCheckInTime
     */
    public void setVisitorCheckInTime(String VisitorCheckInTime) {
        this.VisitorCheckInTime = VisitorCheckInTime;
    }

    /**
     * @return The VisitorCheckOutTime
     */
    public String getVisitorCheckOutTime() {
        return VisitorCheckOutTime;
    }

    /**
     * @param VisitorCheckOutTime The VisitorCheckOutTime
     */
    public void setVisitorCheckOutTime(String VisitorCheckOutTime) {
        this.VisitorCheckOutTime = VisitorCheckOutTime;
    }

    /**
     * @return The RecurringAppointment
     */
    public Boolean getRecurringAppointment() {
        return RecurringAppointment;
    }

    /**
     * @param RecurringAppointment The RecurringAppointment
     */
    public void setRecurringAppointment(Boolean RecurringAppointment) {
        this.RecurringAppointment = RecurringAppointment;
    }

    /**
     * @return The RecurringFromDateTime
     */
    public String getRecurringFromDateTime() {
        return RecurringFromDateTime;
    }

    /**
     * @param RecurringFromDateTime The RecurringFromDateTime
     */
    public void setRecurringFromDateTime(String RecurringFromDateTime) {
        this.RecurringFromDateTime = RecurringFromDateTime;
    }

    /**
     * @return The RecurringToDateTime
     */
    public String getRecurringToDateTime() {
        return RecurringToDateTime;
    }

    /**
     * @param RecurringToDateTime The RecurringToDateTime
     */
    public void setRecurringToDateTime(String RecurringToDateTime) {
        this.RecurringToDateTime = RecurringToDateTime;
    }

    /**
     * @return The ApprovedBy
     */
    public String getApprovedBy() {
        return ApprovedBy;
    }

    /**
     * @param ApprovedBy The ApprovedBy
     */
    public void setApprovedBy(String ApprovedBy) {
        this.ApprovedBy = ApprovedBy;
    }

    /**
     * @return The ApproveDate
     */
    public String getApproveDate() {
        return ApproveDate;
    }

    /**
     * @param ApproveDate The ApproveDate
     */
    public void setApproveDate(String ApproveDate) {
        this.ApproveDate = ApproveDate;
    }

    /**
     * @return The ApprovalComments
     */
    public String getApprovalComments() {
        return ApprovalComments;
    }

    /**
     * @param ApprovalComments The ApprovalComments
     */
    public void setApprovalComments(String ApprovalComments) {
        this.ApprovalComments = ApprovalComments;
    }

    /**
     * @return The AppointmentStatusName
     */
    public String getAppointmentStatusName() {
        return AppointmentStatusName;
    }

    /**
     * @param AppointmentStatusName The AppointmentStatusName
     */
    public void setAppointmentStatusName(String AppointmentStatusName) {
        this.AppointmentStatusName = AppointmentStatusName;
    }


}
