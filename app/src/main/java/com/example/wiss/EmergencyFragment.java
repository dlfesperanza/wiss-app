package com.example.wiss;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wiss.data.EmergencyContactsFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

//import android.support.annotation.Nullable;


public class EmergencyFragment extends Fragment implements View.OnClickListener {
    Button hotlinebutton,contactsbutton;
    Context context;
    private ActionBar toolbar;
    TextView textView;
    JSONObject obj = new JSONObject();
    List<String> contactList = new ArrayList<>();
    SharedPreferences appSharedPrefs;
    Gson gson = new Gson();
    SharedPreferences.Editor prefsEditor;
    String savedContactsLocal;
    Type type;
//    JSONObject selectedListObj = new JSONObject();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ((Button) view.findViewById(R.id.hotlinebutton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.contactsbutton)).setOnClickListener(this);
        view.findViewById(R.id.panicbutton).setOnClickListener(this);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_empty);
        context = getActivity();


        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> contacts = gson.fromJson(json,type);

        Log.d("TAG","FRAGMENT = " + contacts);



        try {
            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                obj.put(name, phoneNumber);
//                contacts.add(obj);

            }
            phones.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        assert contacts != null;
        for (String temp : contacts) {
            if (obj.has(temp)) {
                try {
                    System.out.println("in obj: "+obj.getString(temp));
                    contactList.add(obj.getString(temp));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("contact list array:" + contactList);



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

                Bundle args=new Bundle();
                String phoneContacts=obj.toString();
                args.putString("phoneContacts", phoneContacts);

                fragment_emergency_contacts.setArguments(args);


                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container,fragment_emergency_contacts,"tag_emergency_contacts_home");
                fragmentTransaction.replace(R.id.fragment_container, fragment_emergency_contacts,"tag_emergency_contacts_home");

                fragmentTransaction.addToBackStack("tag_emergency_contacts_home");

                fragmentTransaction.commit();
                break;
            case R.id.panicbutton:

                SharedPreferences appSharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("MyLocation", "");
                Type type = new TypeToken<List<String>>(){}.getType();
                final List<String> savedLocation = gson.fromJson(json,type);

                System.out.println("--Location saved in Emergency: " + savedLocation);

                final Date currentTime = Calendar.getInstance().getTime();
                new AlertDialog.Builder(getContext())
                        .setTitle("Emergency Button")
                        .setMessage("Send SMS alert to your specified contacts?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                String messageToSend = "EMERGENCY ALERT: I'm at risk. Pls send help or assistance. My location coordinates are: "+savedLocation.get(0)+", "+savedLocation.get(1)+". Sent "+currentTime;
//                                String messageToSend = "EMERGENCY ALERT!";
                                System.out.println(messageToSend);
                                for (String number : contactList){
                                    SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
                                    System.out.println("Sent to " + number);
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
//


                break;
        }
    }
}