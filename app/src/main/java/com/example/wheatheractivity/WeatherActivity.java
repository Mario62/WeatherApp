package com.example.wheatheractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    TextView temperatureVal, humidityVal, pressureVal, tempMinVal, tempMaxVal;

    private void saveData(String output){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CITY_KEY",output);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateTime = sdf.format(calendar.getTime());
        TextView time = findViewById(R.id.time);
        time.setText(dateTime);

        final TextView cityName = findViewById(R.id.cityname);

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        cityName.setText(city);
        saveData(city);

        temperatureVal = findViewById(R.id.temp);
        humidityVal = findViewById(R.id.humidity);
        pressureVal = findViewById(R.id.pressure);
        tempMaxVal = findViewById(R.id.tempmax);
        tempMinVal = findViewById(R.id.tempmin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI weatherDataAPI = retrofit.create(JsonPlaceholderAPI.class);
        Call<WeatherData> call = weatherDataAPI.getWeatherData(city, "749561a315b14523a8f5f1ef95e45864","metric");
        call.enqueue(new Callback<WeatherData>() {

            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(!response.isSuccessful()){
                    Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
                    startActivity(intent);
                    return;
                }

                WeatherData weatherData = response.body();
                String temp = weatherData.main.getTemp() + " " + (char) 0x2103;
                temperatureVal.setText(temp);
                String pressure = weatherData.main.getPressure() + " hPa";
                pressureVal.setText(pressure);
                String humidity = weatherData.main.getHumidity() + " %";
                humidityVal.setText(humidity);
                String tempMin = weatherData.main.getTemp_min()+" "+(char) 0x2103;
                tempMinVal.setText(tempMin);
                String tempMax = weatherData.main.getTemp_max()+" "+(char) 0x2103;
                tempMaxVal.setText(tempMax);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //timerHandler.removeCallbacks(updater);
    }
}
