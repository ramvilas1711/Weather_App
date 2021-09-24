package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Activity.R;
import com.example.Model.TemperatureData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Weather_Today extends AppCompatActivity {

    EditText cityName;
    Button showResult;
    TextView tempInFah,tempIncent,latitude,longitude;
    String temp,celsius,fahrenheit;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_today);

        cityName = findViewById(R.id.cityNameID);
        showResult = findViewById(R.id.showResultID);
        tempInFah = findViewById(R.id.tempFahrenValue);
        tempIncent = findViewById(R.id.tempCentrigradeValueID);
        latitude = findViewById(R.id.latitudeValueID);
        longitude = findViewById(R.id.longitudeValueID);

        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cityName != null){
                    temperatureData();
                }else{
                    Toast.makeText(getApplicationContext(),"Please Enter CityName",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void temperatureData() {
        String city = cityName.getText().toString().trim();
        String URL = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=79a8eeb1b19ded9e1bd1215fce0d896c";

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = new JSONObject(String.valueOf(response));
                    JSONObject jsonObject = object.getJSONObject("main");
                    JSONObject jsonObject1 = object.getJSONObject("coord");
                    TemperatureData temperatureData = new TemperatureData();
                     temp = jsonObject.getString("temp");
                    temperatureData.setLatitude(jsonObject1.getString("lat"));
                    temperatureData.setLongitude(jsonObject1.getString("lon"));
                    convert(temp);
                    latitude.setText(temperatureData.getLatitude());
                    longitude.setText(temperatureData.getLongitude());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
            requestQueue.add(jsonObjectRequest);
    }

    private void convert(String temp) {
        String celsius = df2.format((Double.parseDouble(temp)-273.15));

     //   String fahtem = Double.toString((Double.parseDouble(temp)-273.15));
        String tem = df2.format( ((9*((Double.parseDouble(temp))-273.15)))/5 + 32);

        tempInFah.setText(tem);
        tempIncent.setText(celsius);
    }
}