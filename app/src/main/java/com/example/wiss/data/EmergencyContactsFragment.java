package com.example.wiss.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.wiss.R;

import androidx.fragment.app.Fragment;

public class EmergencyContactsFragment extends Fragment {
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().setContentView(R.layout.item_contacts);
        View view = inflater.inflate(R.layout.item_contacts, container, false);

//        ContentResolver resolver = getContext().getContentResolver();
//        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
//        while(cursor.moveToNext()){
//            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//            String name = cursor.getString((cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
//
//            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{ id },null);
//
//            while(phoneCursor.moveToNext()){
//                String phoneNumber = phoneCursor.getString((phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//
//                System.out.println("Contact #" + id + ": " + name + " = " + phoneNumber);
//            }
//        }

        listView = (ListView) view.findViewById(R.id.contacts_listview);

        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        getActivity().startManagingCursor(cursor);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone._ID};

        int[] to = {android.R.id.text1,android.R.id.text2};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(view.getContext(),android.R.layout.simple_list_item_2,cursor,from,to);
        listView.setAdapter(simpleCursorAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return view;
    }
}
