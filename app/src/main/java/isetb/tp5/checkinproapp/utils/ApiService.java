package isetb.tp5.checkinproapp.utils;

import isetb.tp5.checkinproapp.model.Employee;
import isetb.tp5.checkinproapp.model.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("Employee/profile/{id}")
    Call<Employee> getProfile(@Path("id") Long id);

    @PUT("Employee/profile/{id}")
    Call<Employee> updateProfile(@Path("id") Long id, @Body Employee employee);
    @POST("/Employee/register")
    Call<Employee> registerEmployee(@Body Employee employee);

    @POST("Employee/login") // Ajouter un endpoint de connexion sur votre backend si nécessaire
    Call<Employee> loginEmployee(@Body LoginRequest loginRequest);
}

