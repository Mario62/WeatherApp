package com.example.wheatheractivity;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.GET;
        import retrofit2.http.Query;

public interface JsonPlaceholderAPI {
        @GET("/data/2.5/weather")
        Call<WeatherData> getWeatherData(
                @Query("q") String q,
                @Query("APPID") String appid,
                @Query("units") String units);
    }

