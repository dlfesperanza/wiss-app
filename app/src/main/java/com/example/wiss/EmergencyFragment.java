package com.example.wiss;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

//import android.support.annotation.Nullable;


public class EmergencyFragment extends Fragment implements View.OnClickListener {
    Button hotlinebutton;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ((Button) view.findViewById(R.id.hotlinebutton)).setOnClickListener(this);


        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return view;
    }

    public void onClick(View view) {
        Fragment fragment_weather_hotline = new WeatherHotlineFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment_weather_hotline);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}