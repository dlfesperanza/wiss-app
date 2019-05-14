package com.example.wiss;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.wiss.data.EmergencyContactsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EmergencyContactsHomeFragment extends Fragment {
    private ActionBar toolbar;
    JSONObject jsonObject=new JSONObject();
    JSONObject obj = new JSONObject();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            String phoneContacts=getArguments().getString("phoneContacts");
            obj=new JSONObject(phoneContacts);

            System.out.println("Fragment (All Contacts): "+obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> contacts = gson.fromJson(json,type);

        Log.d("TAG","FRAGMENT = " + contacts);

        View view = inflater.inflate(R.layout.fragment_emergency_contacts, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_contacts);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.button_addcontacts);

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_addcontacts:
                        Fragment fragment_emergency_contacts= new EmergencyContactsFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        Bundle args=new Bundle();
                        String phoneContacts=obj.toString();
                        args.putString("phoneContacts", phoneContacts);

                        fragment_emergency_contacts.setArguments(args);

                        fragmentTransaction.add(R.id.fragment_container,fragment_emergency_contacts,"tag_emergency_contacts_add");
                        fragmentTransaction.replace(R.id.fragment_container, fragment_emergency_contacts,"tag_emergency_contacts_add");
                        fragmentTransaction.addToBackStack("tag_emergency_contacts_add");
                        fragmentTransaction.commit();
                        break;
                }

            }
        });

        if (contacts != null){
            String[] items = contacts.toArray(new String[contacts.size()]);
            Arrays.sort(items);

            ListView listView = (ListView) view.findViewById(R.id.listview_favcontacts);
//        ArrayAdapter<String> simpleCursorAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.item_contactdisplay,R.id.displaytextview,items);
            ArrayAdapter<String> simpleCursorAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.item_contactdisplay,R.id.displaytextview,items){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Cast the list view each item as text view
                    TextView item = (TextView) super.getView(position,convertView,parent);


                    // Change the item text size
                    item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);

                    // return the view
                    return item;
                }
            };

            listView.setAdapter(simpleCursorAdapter);


            for (String temp : contacts) {
                if (obj.has(temp)) {
                    try {
                        System.out.println("in obj home: "+obj.getString(temp));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return view;
    }



}
