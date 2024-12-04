package isetb.tp5.checkinproapp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.63.121:5000/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Log.d("Retrofit", "Initialisation de Retrofit avec l'URL de base : " + BASE_URL);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            Log.d("Retrofit", "Initialisation de Retrofit avec l'URL de base : " + BASE_URL);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

