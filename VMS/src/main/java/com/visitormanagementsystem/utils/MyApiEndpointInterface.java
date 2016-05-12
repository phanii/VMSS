package com.visitormanagementsystem.utils;

import com.visitormanagementsystem.pojos.ActivateAccountPojo;
import com.visitormanagementsystem.pojos.AppointmentDetailsByVisitorIdPojo;
import com.visitormanagementsystem.pojos.AppointmentListByVisitorId;
import com.visitormanagementsystem.pojos.BookAnAppointment;
import com.visitormanagementsystem.pojos.ChangePassword;
import com.visitormanagementsystem.pojos.EmployeeDashBoardQuickActionsInformationPojo;
import com.visitormanagementsystem.pojos.Login;
import com.visitormanagementsystem.pojos.Spinner_ValuesBean;
import com.visitormanagementsystem.pojos.User;
import com.visitormanagementsystem.pojos.Visitordetails;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Phani.Gullapalli on 25/11/2015.
 */
public interface MyApiEndpointInterface {
    /*
    to registrer a new user
    */
    @Headers({"Content-Type: application/json"
    })
    @POST("api/Account/Register")
    Call<User> createUser(@Body User user);

    /*
    get profile details
*/
    // Observable<User> createUser(@Body User user);
    @GET("api/visitor/{visitorid}")
    Call<Visitordetails> getVisitorById(@Path("visitorid") String visitorid);


    @GET("api/visitor/{visitorid}")
    Observable<Visitordetails> getVisitordetailsByidRX(@Path("visitorid") String visitorid);

    /*

    to update the user profile

        */
    @PUT("api/Visitor")
    Call<Visitordetails> updateUser(@Body Visitordetails visitordetails);


    /*
    to book an appointment
     */
    @POST("api/Appointment")
    Call<BookAnAppointment> createAnAppointment(@Body BookAnAppointment bookAnAppointment, @Header("Authorization") String token);

    /*
    * For change password
    *
    * */
    @POST("api/Account/ChangePassword")
    Call<ChangePassword> changePassword(@Body ChangePassword changePassword);

    /*
    for accountActivation
     */
    @POST("api/Account/ActivateAccount")
    Call<ActivateAccountPojo> activateAccount(@Body ActivateAccountPojo changePassword);

    /**
     * for user login
     *
     * @param username
     * @param password
     * @param grant_type
     * @return
     */

    @FormUrlEncoded
    @POST("token")
    Observable<Login> UserLogin(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);

    /**
     * to get list of appointments
     *
     * @param VisitorId
     * @return
     */
    @GET("api/Appointment/AppointmentListByVisitorId")
    Call<List<AppointmentListByVisitorId>> getAppointmentDetails(@Query("VisitorId") String VisitorId);

    @GET("api/Appointment/DashboradAppointmentDetailsByActionName")
    Observable<List<AppointmentListByVisitorId>>
    getDashboradAppointmentDetailsByActionNameRx(
            @Query("userID") String userid,
            @Query("UserRoleName") String userrolename, @Query("EmaiId") String emailid, @Query("City") String city, @Query("Location") String location,
            @Query("Building") String building, @Query("Department") String department, @Query("dataActionName") String dataactionname);
    /*

    fetch a single record based on appointment ID
     */

    @GET("api/Appointment/{id}")
    Call<AppointmentDetailsByVisitorIdPojo> getAppointmentDetailsById(@Path("id") String id);

    /**
     * EmployeeDashBoardQuickActionsInformation
     */
    @GET("api/Appointment/DashBoardQuickActionsInformation")
    Call<List<EmployeeDashBoardQuickActionsInformationPojo>> getEmployeeDashBoardQuickActionsInformationPojoCall(@Query("EmployeeID") String empid,
                                                                                                                 @Query("UserRole") String rolecode, @Query("EmployeeEmailId") String employeeEmailId,
                                                                                                                 @Query("City") String City, @Query("Location") String Location,
                                                                                                                 @Query("Building") String Building, @Query("Department") String Department, @Query("IsMyAppointment") boolean isMyAppointment);


    /**
     * GetNationality
     */
    @GET("api/VmsGlobal/GetNationality")
    Call<List<Spinner_ValuesBean>> getSPINNER_NATIONALITY_OBSERVABLE();

    /*

    api/VmsGlobal/ListOfIds  GetListOfIds
     */
    @GET("api/VmsGlobal/ListOfIds")
    Call<List<Spinner_ValuesBean>> getSPINNER_ID_OBSERVABLE();

    /*

 api/VmsGlobal/GetDepartmentName  GetDepartmentName
  */
    @GET("api/VmsGlobal/GetDepartmentName")
    Call<List<Spinner_ValuesBean>> getSPINNER_GetDepartmentName_OBSERVABLE();

    /*

    api/VmsGlobal/GetSubDepartmentName   GetSubDepartmentName
     */
    @GET("api/VmsGlobal/GetSubDepartmentName")
    Call<List<Spinner_ValuesBean>> getSPINNER_GetSubDepartmentName_OBSERVABLE();

    /*

      api/VmsGlobal/PurposeForVisit   PurposeForVisit
       */
    @GET("api/VmsGlobal/PurposeForVisit")
    Call<List<Spinner_ValuesBean>> getSPINNER_PurposeForVisit_OBSERVABLE();
/*

api/VmsGlobal/GetSecurityQuestions
 */

    @GET("api/VmsGlobal/GetSecurityQuestions")
    Observable<List<Spinner_ValuesBean>> getSecurityQuestions();

    /*

   PUT api/Appointment/EmployeeUpdateAppointment
     */
    @PUT("api/Appointment/EmployeeUpdateAppointment")
    Observable<AppointmentDetailsByVisitorIdPojo> rescheduleAppointmentRx(@Body AppointmentDetailsByVisitorIdPojo appointmentListByVisitorId);



}
