package com.example.wiss;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wiss.data.EmergencyContactsFragment;

import org.json.JSONObject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

//import android.support.annotation.Nullable;


public class EmergencyFragment extends Fragment implements View.OnClickListener {
    Button hotlinebutton,contactsbutton;
    private ActionBar toolbar;
    TextView textView;
//    JSONObject selectedListObj = new JSONObject();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ((Button) view.findViewById(R.id.hotlinebutton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.contactsbutton)).setOnClickListener(this);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_empty);



        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hotlinebutton:
                Fragment fragment_weather_hotline = new WeatherHotlineFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment_weather_hotline);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.contactsbutton:
                Fragment fragment_emergency_contacts = new EmergencyContactsHomeFragment();
                fragmentManager = getFragmentManager();

//                Bundle args=new Bundle();
//                String selectedContacts=selectedListObj.toString();
//                args.putString("selectedContacts", selectedContacts);
//                fragment_emergency_contacts.setArguments(args);


                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container,fragment_emergency_contacts,"tag_emergency_contacts_home");
                fragmentTransaction.replace(R.id.fragment_container, fragment_emergency_contacts,"tag_emergency_contacts_home");
//

                fragmentTransaction.addToBackStack("tag_emergency_contacts_home");

                fragmentTransaction.commit();
                break;
        }
    }
}