package com.example.wiss;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.example.wiss.data.WeatherFunction;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherFragment extends Fragment{
    // Project Created by Ferdousur Rahman Shajib
    // www.androstock.com

    TextView selectCity, getLocation, cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField, day1, day2, day3, day4, day5, day1_icon, day2_icon, day3_icon, day4_icon, day5_icon, day1_temp, day2_temp, day3_temp, day4_temp, day5_temp;
    ProgressBar loader;
    Typeface weatherFont;
//    String city = "Manila, PH";
    String savedCity;
    String city;
    /* Please Put your API KEY here */
    String OPEN_WEATHER_MAP_API = "1a8b15bb29c60aa92524f6939e91b100";
    /* Please Put your API KEY here */
    LocationManager locationMn;
    public Double latitude;
    public Double longitude;
    List<String> list1;
    SharedPreferences appSharedPrefs;
    Gson gson = new Gson();
    SharedPreferences.Editor prefsEditor;
    LocationListener locListener;
    List<Double> savedLoc;

    //    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        setContentView(R.layout.activity_main);
        View view = inflater.inflate(R.layout.fragment_weather, null);

        loader = (ProgressBar) view.findViewById(R.id.loader);
        selectCity = (TextView) view.findViewById(R.id.selectCity);
        getLocation = (TextView) view.findViewById(R.id.getLocation);
        cityField = (TextView) view.findViewById(R.id.city_field);
        updatedField = (TextView) view.findViewById(R.id.updated_field);
        detailsField = (TextView) view.findViewById(R.id.details_field);
        currentTemperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) view.findViewById(R.id.humidity_field);
        pressure_field = (TextView) view.findViewById(R.id.pressure_field);
        weatherIcon = (TextView) view.findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);

        day1 = (TextView) view.findViewById(R.id.day1);
        day2 = (TextView) view.findViewById(R.id.day2);
        day3 = (TextView) view.findViewById(R.id.day3);
        day4 = (TextView) view.findViewById(R.id.day4);
        day5 = (TextView) view.findViewById(R.id.day5);

        day1_icon = (TextView) view.findViewById(R.id.day1_icon);
        day2_icon = (TextView) view.findViewById(R.id.day2_icon);
        day3_icon = (TextView) view.findViewById(R.id.day3_icon);
        day4_icon = (TextView) view.findViewById(R.id.day4_icon);
        day5_icon = (TextView) view.findViewById(R.id.day5_icon);

        day1_icon.setTypeface(weatherFont);
        day2_icon.setTypeface(weatherFont);
        day3_icon.setTypeface(weatherFont);
        day4_icon.setTypeface(weatherFont);
        day5_icon.setTypeface(weatherFont);

        day1_temp = (TextView) view.findViewById(R.id.day1_temp);
        day2_temp = (TextView) view.findViewById(R.id.day2_temp);
        day3_temp = (TextView) view.findViewById(R.id.day3_temp);
        day4_temp = (TextView) view.findViewById(R.id.day4_temp);
        day5_temp = (TextView) view.findViewById(R.id.day5_temp);


//        city = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("savedCity", "Manila, PH");


        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyLocation", "");
        Type type = new TypeToken<List<Double>>(){}.getType();
        savedLoc = gson.fromJson(json,type);

        Log.d("TAG","LOCATION = " + savedLoc);

        if (savedLoc == null) city = "Manila, PH";



        taskLoadUp(city);



        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Change City");
                loader.setVisibility(View.GONE);
                final EditText input = new EditText(getContext());
                input.setText(city);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alertDialog.setView(input);

                alertDialog.setPositiveButton("Change",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                city = input.getText().toString();
                                savedCity = city;
//                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("savedCity", city).apply();
                                taskLoadUp(city);
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });
//


        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return view;
//        return inflater.inflate(R.layout.fragment_weather, null);
    }

    public void taskLoadUp(String query) {
        if (WeatherFunction.isNetworkAvailable(getActivity().getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();


            task.execute(query);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class DownloadWeather extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(String... args) {


            String xml;




            if (city==null){
//                while (latitude == null && longitude == null){
//                    latitude = getLatitude();
//                    longitude = getLongitude();
//                    System.out.println("IN BACKGROUND: " + latitude + ". " + longitude);
//                }
                latitude = getLatitude();
                longitude = getLongitude();
                xml = WeatherFunction.excuteGet("http://api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&lon="+longitude+"&appid=" + OPEN_WEATHER_MAP_API);
            }else{
                xml = WeatherFunction.excuteGet("http://api.openweathermap.org/data/2.5/forecast?q=" + args[0] + "&units=metric&appid=" + OPEN_WEATHER_MAP_API);

            }


//
           return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("E");


                    JSONObject dayone = json.getJSONArray("list").getJSONObject(3);
                    JSONObject daytwo = json.getJSONArray("list").getJSONObject(11);
                    JSONObject daythree = json.getJSONArray("list").getJSONObject(19);
                    JSONObject dayfour = json.getJSONArray("list").getJSONObject(27);
                    JSONObject dayfive = json.getJSONArray("list").getJSONObject(35);

                    Double subVal;
                    if ( main.getDouble("temp") > 100){
                        subVal = 273.15;
                    }else subVal = 0.0;

                    selectCity.setText("Change City");
//                    getLocation.setText("Get Location");
                    cityField.setText(json.getJSONObject("city").getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("city").getString("country"));
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")-subVal) + "°");
                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    updatedField.setText(df.format(new Date(json.getJSONArray("list").getJSONObject(0).getLong("dt") * 1000)));
                    weatherIcon.setText(Html.fromHtml(WeatherFunction.setWeatherIcon(details.getInt("id"))));

                    day1.setText(sdf.format(new Date(dayone.getLong("dt") * 1000)));
                    day2.setText(sdf.format(new Date(daytwo.getLong("dt") * 1000)));
                    day3.setText(sdf.format(new Date(daythree.getLong("dt") * 1000)));
                    day4.setText(sdf.format(new Date(dayfour.getLong("dt") * 1000)));
                    day5.setText(sdf.format(new Date(dayfive.getLong("dt") * 1000)));

                    day1_icon.setText(Html.fromHtml(WeatherFunction.setWeatherIcon(dayone.getJSONArray("weather").getJSONObject(0).getInt("id"))));
                    day2_icon.setText(Html.fromHtml(WeatherFunction.setWeatherIcon(daytwo.getJSONArray("weather").getJSONObject(0).getInt("id"))));
                    day3_icon.setText(Html.fromHtml(WeatherFunction.setWeatherIcon(daythree.getJSONArray("weather").getJSONObject(0).getInt("id"))));
                    day4_icon.setText(Html.fromHtml(WeatherFunction.setWeatherIcon(dayfour.getJSONArray("weather").getJSONObject(0).getInt("id"))));
                    day5_icon.setText(Html.fromHtml(WeatherFunction.setWeatherIcon(dayfive.getJSONArray("weather").getJSONObject(0).getInt("id"))));

                    day1_temp.setText(String.format("%.2f", dayone.getJSONObject("main").getDouble("temp")-subVal) + "°");
                    day2_temp.setText(String.format("%.2f", daytwo.getJSONObject("main").getDouble("temp")-subVal) + "°");
                    day3_temp.setText(String.format("%.2f", daythree.getJSONObject("main").getDouble("temp")-subVal) + "°");
                    day4_temp.setText(String.format("%.2f", dayfour.getJSONObject("main").getDouble("temp")-subVal) + "°");
                    day5_temp.setText(String.format("%.2f", dayfive.getJSONObject("main").getDouble("temp")-subVal) + "°");

                    loader.setVisibility(View.GONE);

//

                }
            } catch (JSONException e) {
                Toast.makeText(getContext().getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }

        }
    }


    Double getLatitude(){
        return this.savedLoc.get(1);
    }

    Double getLongitude(){
        return this.savedLoc.get(0);
    }


}