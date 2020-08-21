package com.provider.beautician.api;


import com.provider.beautician.CommonBean.JsonArrayResponse;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.model.AccountSettingModel;
import com.provider.beautician.model.ClientModel;
import com.provider.beautician.model.ClosedDateModel;
import com.provider.beautician.model.DailySalesModel;
import com.provider.beautician.model.ListDataModel;
import com.provider.beautician.model.LoginDataModel;
import com.provider.beautician.model.StaffLocationModel;
import com.provider.beautician.model.StaffMemberDetailModel;
import com.provider.beautician.model.StaffWorkingMainModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @POST("getList")
    @FormUrlEncoded
    Call<JsonArrayResponse<ListDataModel>> getListApi(
            @Field("start_limit") String start_limit
    );

    @POST("updatesaloonprofile")
    @Multipart
    Call<JsonObjectResponse<LoginDataModel>> updateUserProfile(
            @Part List<MultipartBody.Part> image,
            @Part("id") RequestBody id,
            @Part("name") RequestBody name,
            @Part("last_name") RequestBody last_name,
            @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email,
            @Part("old_password") RequestBody old_password,
            @Part("password") RequestBody password
    );

    @POST("addsatffworkinghours")
    @FormUrlEncoded
    Call<JsonObjectResponse> addStaffWorkingHours(
            @Field("saloon_id") String saloon_id,
            @Field("staff_id") String staff_id,
            @Field("first_shift_start") String first_shift_start,
            @Field("first_shift_end") String first_shift_end,
            @Field("second_shift_start") String second_shift_start,
            @Field("second_shift_end") String second_shift_end,
            @Field("repeat_type") String repeat_type,
            @Field("end_repeat") String end_repeat,
            @Field("end_repeat_date") String end_repeat_date,
            @Field("location_id") String location_id,
            @Field("date") String date
    );

    @POST("getsalonstaffworking")
    @FormUrlEncoded
    Call<JsonArrayResponse<StaffWorkingMainModel>> getSaloonStaffWorking(
            @Field("saloon_id") String saloon_id,
            @Field("location_id") String location_id,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("staff_id") String staff_id
    );

    @POST("getclosedatesbysaloonid")
    @FormUrlEncoded
    Call<JsonArrayResponse<ClosedDateModel>> getCloseDatesBySaloonId(
            @Field("saloon_id") String saloon_id
    );


    @POST("addclosedate")
    @FormUrlEncoded
    Call<JsonArrayResponse<ClosedDateModel>> addCloseDate(
            @Field("saloon_id") String saloon_id,
            @Field("from_date") String from_date,
            @Field("to_data") String to_data,
            @Field("description") String description,
            @Field("location_id") String location_id
    );

    @POST("editclosedate")
    @FormUrlEncoded
    Call<JsonArrayResponse<ClosedDateModel>> editCloseDate(
            @Field("close_id") String close_id,
            @Field("saloon_id") String saloon_id,
            @Field("from_date") String from_date,
            @Field("to_data") String to_data,
            @Field("description") String description,
            @Field("location_id") String location_id
    );

    @POST("deletecloasedatesbyid")
    @FormUrlEncoded
    Call<JsonObjectResponse> deleteCloseDate(
            @Field("close_id") String close_id
    );

    @POST("updatebusinessdetails")
    @FormUrlEncoded
    Call<JsonObjectResponse<AccountSettingModel>> updateAccountSetting(
            @Field("saloon_id") String saloon_id,
            @Field("busineess_name") String busineess_name,
            @Field("website") String website,
            @Field("instagram") String instagram,
            @Field("facebook") String facebook
    );

    @POST("getsaloonbusinessdetails")
    @FormUrlEncoded
    Call<JsonObjectResponse<AccountSettingModel>> getAccountSetting(
            @Field("saloon_id") String saloon_id
    );

    @POST("getmystaff")
    @FormUrlEncoded
    Call<JsonArrayResponse<StaffMemberDetailModel>> getMyStaff(
            @Field("saloon_id") String saloon_id
    );

    @POST("addsaloonstaff")
    @FormUrlEncoded
    Call<JsonObjectResponse> addSaloonStaff(
            @Field("saloon_id") String saloon_id,
            @Field("staff_id") String staff_id,
            @Field("staff_name") String staff_name,
            @Field("last_name") String last_name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("staff_title") String staff_title,
            @Field("staff_notes") String staff_notes,
            @Field("staff_start_date") String staff_start_date,
            @Field("staff_end_date") String staff_end_date,
            @Field("staff_appointment_color") String staff_appointment_color,
            @Field("service_commission") String service_commission
    );

    @POST("deletestaffbyid")
    @FormUrlEncoded
    Call<JsonObjectResponse> deleteStaffById(
            @Field("staff_id") String staff_id
    );


    @POST("providerlogin")
    @FormUrlEncoded
    Call<JsonObjectResponse<LoginDataModel>> doLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("android_token") String android_token
    );

    @POST("getsaloonbusinesslocations")
    @FormUrlEncoded
    Call<JsonObjectResponse<StaffLocationModel>> getSaloonLocation(
            @Field("saloon_id") String saloon_id
    );

    @POST("addsaloonlocation")
    @FormUrlEncoded
    Call<JsonObjectResponse<StaffLocationModel>> addSaloonLocation(
            @Field("saloon_id") String saloon_id,
            @Field("location_name") String location_name,
            @Field("contact_number") String contact_number,
            @Field("contact_email") String contact_email,
            @Field("default_address") String default_address,
            @Field("additional_address") String additional_address,
            @Field("city") String city,
            @Field("state") String state,
            @Field("zip_code") String zip_code,
            @Field("location_id") String location_id
    );

    @POST("signup")
    @FormUrlEncoded
    Call<JsonObjectResponse<LoginDataModel>> doSignUp(
            @Field("name") String name,
            @Field("last_name") String last_name,
            @Field("phone") String phone,
            @Field("busineess_name") String company_name,
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("addsaloonclient")
    @FormUrlEncoded
    Call<JsonObjectResponse> addNewClient(
            @Field("saloon_id") String saloon_id,
            @Field("name") String name,
            @Field("last_name") String last_name,
            @Field("mobile_no") String mobile_no,
            @Field("telephone") String telephone,
            @Field("email") String email,
            @Field("birth_day") String birth_day,
            @Field("birth_year") String birth_year,
            @Field("gender") String gender,
            @Field("client_note_show_all_booking") String client_note_show_all_booking,
            @Field("client_note") String client_note,
            @Field("accept_marketing_notification") String accept_marketing_notification,
            @Field("notiication_via") String notiication_via
    );

    @POST("addsaloonclient")
    @FormUrlEncoded
    Call<JsonObjectResponse> editClient(
            @Field("user_id") String user_id,
            @Field("saloon_id") String saloon_id,
            @Field("name") String name,
            @Field("last_name") String last_name,
            @Field("mobile_no") String mobile_no,
            @Field("telephone") String telephone,
            @Field("email") String email,
            @Field("birth_day") String birth_day,
            @Field("birth_year") String birth_year,
            @Field("gender") String gender,
            @Field("client_note_show_all_booking") String client_note_show_all_booking,
            @Field("client_note") String client_note,
            @Field("accept_marketing_notification") String accept_marketing_notification,
            @Field("notiication_via") String notiication_via
    );

    @POST("getsaloonclients")
    @FormUrlEncoded
    Call<JsonArrayResponse<ClientModel>> getClientList(
            @Field("saloon_id") String saloon_id
    );

    @POST("deleteclient")
    @FormUrlEncoded
    Call<JsonObjectResponse<ClientModel>> deleteClient(
            @Field("user_id") String user_id
    );

    @POST("getdailysales")
    @FormUrlEncoded
    Call<JsonObjectResponse<DailySalesModel>> getDailySales(
            @Field("saloon_id") String saloon_id,
            @Field("date") String date
    );

    @POST("sociallogin")
    @FormUrlEncoded
    Call<JsonObjectResponse<LoginDataModel>> doSocialLogin(
            @Field("name") String name,
            @Field("social_id") String social_id,
            @Field("social_type") String social_type,
            @Field("email") String email,
            @Field("android_token") String android_token
    );

    @POST("logout")
    @FormUrlEncoded
    Call<JsonObjectResponse<LoginDataModel>> doLogout(
            @Field("saloon_id") String saloon_id,
            @Field("device_type") String device_type
    );

    @POST("forgotpassword")
    @FormUrlEncoded
    Call<JsonObjectResponse> forgotPassword(
            @Field("email") String email
    );
}
