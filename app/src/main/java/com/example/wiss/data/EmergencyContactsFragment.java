package com.example.wiss.data;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wiss.EmergencyContactsHomeFragment;
import com.example.wiss.R;
import com.example.wiss.WeatherHotlineFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EmergencyContactsFragment extends Fragment{
    ListView listView;
    List<String> selectedItems = new ArrayList<>();
    ArrayList<JSONObject> contacts = new ArrayList<>();
    JSONObject obj = new JSONObject();
    JSONObject selectedListObj = new JSONObject();
    JSONObject prevContacts=new JSONObject();
    List<String> list1;
    SharedPreferences appSharedPrefs;
    Gson gson = new Gson();
    SharedPreferences.Editor prefsEditor;
    String savedContactsLocal;
    Type type;
    private ActionBar toolbar;
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            String phoneContacts=getArguments().getString("phoneContacts");
            obj=new JSONObject(phoneContacts);

            System.out.println("Fragment (All Contacts): "+obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//
        View view = inflater.inflate(R.layout.item_contacts, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_addcontacts);

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.button_selectcontacts);

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_selectcontacts:
                        String items="";
                        String value = "";
                        for(String item:selectedItems){
                            try {
                                value = obj.getString(item);

                                selectedListObj.put(item,value);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        savedContactsLocal = gson.toJson(selectedItems);

                        List<String> list2 = gson.fromJson(savedContactsLocal,type);
                        String allContactsLocal;
                        if (list1 != null){
                            list1.addAll(list2);
                            allContactsLocal = gson.toJson(list1);
                        }else allContactsLocal = gson.toJson(list2);

                        prefsEditor.putString("MyObject", allContactsLocal);
                        prefsEditor.commit();

                        System.out.println(list1);
                        getFragmentManager().popBackStackImmediate();
                        break;
                }

            }
        });


        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        prefsEditor = appSharedPrefs.edit();




        String json = appSharedPrefs.getString("MyObject", "");
        type = new TypeToken<List<String>>(){}.getType();
        list1 = gson.fromJson(json,type); //stored



        JSONArray key = obj.names();
        System.out.println(key);

        final List<String> list = new ArrayList<String>();
        for (int i=0; i<key.length(); i++) {
            try {
                list.add( key.getString(i) );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String[] items = list.toArray(new String[list.size()]);
//        Arrays.sort(items);

//        System.out.println(Arrays.toString(items));


            listView = (ListView) view.findViewById(R.id.contacts_listview);


            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayAdapter<String> simpleCursorAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.item_contactselect,R.id.checkedtextview,items);
            listView.setAdapter(simpleCursorAdapter);


            if (list1 != null){
                for (int i=0; i<list.size(); i++){
                    for (int j=0; j<list1.size(); j++) {
                        if (list.get(i).equals(list1.get(j))){
                            System.out.println("TRUE");
                            listView.setItemChecked(i,true);
                            break;
                        }
                    }
                }
            }


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = ((TextView) view).getText().toString();

                    if (list1 != null){
                        if (selectedItems.contains(selectedItem)) {
                            selectedItems.remove(selectedItem);
                            selectedListObj.remove(selectedItem);
                        }else if (list1.contains(selectedItem)) list1.remove(selectedItem);
                        else  selectedItems.add(selectedItem);

                    }


                    if (list1 == null){
                        if (selectedItems.contains(selectedItem)) {
                            selectedItems.remove(selectedItem);
                            selectedListObj.remove(selectedItem);
                        } else  selectedItems.add(selectedItem);
                    }
                }
            });





        return view;
    }
}
