package com.example.wheatheractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    private EditText city;

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        String city = sharedPreferences.getString("CITY_KEY","");
        EditText cityName = findViewById(R.id.city);
        cityName.setText(city);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        loadData();
    }
    public void goToWeatherActivity(View view) {
        EditText city = findViewById(R.id.city);
        String cityName = city.getText().toString();

        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("city", cityName);
        startActivity(intent);
    }




}
