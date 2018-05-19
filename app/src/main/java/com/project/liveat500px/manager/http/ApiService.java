package com.project.liveat500px.manager.http;

import com.project.liveat500px.Dao.Appointment;
import com.project.liveat500px.Dao.BloodSample;
import com.project.liveat500px.Dao.Dialysis;
import com.project.liveat500px.Dao.DialysisAlarm;
import com.project.liveat500px.Dao.Doctor;
import com.project.liveat500px.Dao.Food;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.Dao.RecordFood;
import com.project.liveat500px.Dao.recAllFood;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;


/**
 * Created by baboat on 12/9/2560.
 */

public interface ApiService {

    /* Patient*/
    @POST("patient/login/{username}/{password}")
    Call<Patient> login(@Path("username") String username, @Path("password") String password);

    @POST("patient/register")
    Call<Integer> register(@Body Patient patient);

    @POST("patient/updateProfile")
    Call<Patient> updateProfile(@Body Patient patient);

    @GET("patient/findProfile/{id}")
    Call<Patient> findPatient(@Path("id") String id);

    @Multipart
    @POST("patient/uploadImage/{patId}")
    Call<Patient> uploadImage(@Part MultipartBody.Part image, @Path("patId") String patId);

    @POST("patient/changePassword/{patId}/{oldPass}/{newPass}")
    Call<Boolean> changePassword(@Path("patId") String patId , @Path("oldPass") String oldPass ,@Path("newPass") String newPass);

    @POST("patient/forgetPass/send/{email}")
    Call<String> sendEmail(@Path("email") String email );

    /* Record*/
    @POST("record/create")
    Call<Boolean> createRecord(@Body Record record);

    @GET("record/find/{recDate}/{recRound}/{patId}")
    Call<String> findRecId(@Path("recDate") String recDate, @Path("recRound") int round, @Path("patId") String patId);

    @GET("record/find/diaTime/{id}")
    Call<List<Record>> findDateAndRound(@Path("id") String id);

    @GET("record/find/date/{id}/{month}")
    Call<List<Record>> findRecordByByte(@Path("id") String id, @Path("month") int month);

    @GET("record/delete/{id}")
    Call<Record> deleteRecord(@Path("id") String id);

    /* Dialysis*/
    @POST("dialysis/createDiaIn")
    Call<Void> createDialysis(@Body Dialysis dialysis);

    @GET("dialysis/find/dia/{recId}")
    Call<Dialysis> findDialysis(@Path("recId") String recId);

    @POST("dialysis/updateDiaInEnd/{record_recId_fk}")
    Call<Void> updateDiaInEnd(@Body Dialysis dialysis, @Path("record_recId_fk") String recId);

    @POST("dialysis/updateDiaOutSta/{record_recId_fk}")
    Call<Void> updateDiaOutSta(@Body Dialysis dialysis, @Path("record_recId_fk") String recId);

    @POST("dialysis/updateDiaOutEnd/{record_recId_fk}")
    Call<Void> updateDiaOutEnd(@Body Dialysis dialysis ,@Path("record_recId_fk") String recId);

    @GET("dialysis/findAll/{recDate}/{patId}")
    Call <List<Dialysis>> findAll(@Path("recDate") String recDate , @Path ("patId") String patId);

    @GET("dialysis/find/{id}")
    Call<Dialysis> find(@Path("id") String id);

    @GET("dialysis/delete/{id}")
    Call<Dialysis> deleteDialysis(@Path("id") String id);


    @POST("patient/uploadImage")
    Call<Patient> uploadImage(@Part MultipartBody.Part image, @Body Patient patient);

    @GET("appointment/find/{patId}")
    Call<List<Appointment>> findAppointment(@Path("patId") String id);


    @GET ("food/find/{typeId}")
    Call<List<Food>> findFoodByTypeId(@Path("typeId") String typeId);

    @GET("doctor/find/hospName")
    Call<List<String>> findHospName();

    @POST ("record/food/{foodId}/{unit}/{patId}")
    Call<RecordFood> save(@Path("foodId") String foodId, @Path("unit") int unit,@Path("patId") String patId);

    @POST ("record/food/{foodId}/{recFoodId}/{unit}/{total}")
    Call<RecordFood> updateRecordFood(@Path("foodId") String foodId,@Path("recFoodId") String recFoodId,@Path("unit") int unit,@Path("total") String total);

    @POST ("food/find/food")
    Call<List<Food>> findFoodByFoodId(@Body List<recAllFood> recAllFood);

    @GET ("find/recAllFood/{patId}")
    Call<List<recAllFood>> findFoodByPatId(@Path ("patId") String patId);

    @GET ("record/food/find/{patId}")
    Call<RecordFood> findTotal(@Path ("patId") String patId);

    @POST ("recordFood/delete")
    Call<Void> deleteFood(@Body recAllFood recAllFood);

    @GET ("blood/input/{potass}/{phos}/{alb}/{patId}")
    Call <BloodSample> inputBloodSample (@Path("potass") float potass, @Path("phos") float phos,
                                         @Path("alb") float alb, @Path("patId") String patId);

    @GET ("blood/input/fix/{gPotass}/{gPhos}/{gAlb}/{patId}")
    Call <BloodSample> fixBloodSample (@Path("gPotass") String gPotass, @Path("gPhos") String gPhos,
                                       @Path("gAlb") String gAlb, @Path("patId") String patId);

    @GET ("blood/input/fix/{patId}")
    Call <BloodSample> findBloodSample (@Path("patId") String patId);

    /* Noti*/
    @GET("noti/find/{patId}")
    Call<List<DialysisAlarm>> findNotiList(@Path("patId") String patId);

    @POST("noti/save")
    Call<DialysisAlarm> saveNoti(@Body DialysisAlarm alarm);

    @POST("patient/noti/graph/one/{patId}")
    Call<Void> setNotiGraphOne(@Path("patId") String patId);

    @POST("patient/noti/graph/zero/{patId}")
    Call<Void> setNotiGraphZero(@Path("patId") String patId);

    @GET("appointment/find/orderBy/{patId}")
    Call<List<Appointment>> findAppointmentSort(@Path("patId") String id);

    @GET("doctor/find/one/{doctorId}")
    Call<Doctor> findHospname(@Path("doctorId") String doctorId);

    @GET("patient/find/list/hosp")
    Call<List<String>> findHospital();

    @POST("doctor/find/list")
    Call<List<Doctor>> findByDocHospName(@Body Patient patient);
}
