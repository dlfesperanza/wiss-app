package com.example.wiss;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.fabric.sdk.android.Fabric;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101;
    final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 102;
    final int MY_PERMISSIONS_REQUEST_SEND_SMS = 103;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("News");
                    fragment = new NewsFragment();
                    break;
                case R.id.navigation_dashboard:
                    toolbar.setTitle("Weather");
                    fragment = new WeatherFragment();
                    break;
                case R.id.navigation_notifications:
                    toolbar.setTitle("Emergency");
                    fragment = new EmergencyFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        checkForPermissions("CONTACTS");
//        checkForPermissions("SMS");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = getSupportActionBar();
        toolbar.setTitle("News");


    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }



    void initializeViews(){
        //loading the default fragment
        loadFragment(new NewsFragment());
    }

    private void checkForPermissions(String perm) {
        if (perm.equals("CONTACTS")){
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                initializeViews();
            }
        }else if (perm.equals("SMS")){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            } else {
                initializeViews();
            }
        }else if (perm.equals("LOCATION")){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
            } else {
                initializeViews();
            }
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            System.out.println("Request code 1: " + grantResults);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkForPermissions("SMS");
            } else {
                System.out.println("--------contacts");
                openAlertDialog("CONTACTS");
            }
        }else if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            System.out.println("Request code 2: " + requestCode);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkForPermissions("LOCATION");
            } else {
                System.out.println("--------sms");
//                System.out.println("GRANT RESULTS: "  + grantResults + ", " + grantResults[0] + ", " + PackageManager.PERMISSION_GRANTED);
                openAlertDialog("SMS");
            }
        }else if (requestCode == MY_PERMISSIONS_REQUEST_COARSE_LOCATION) {
            System.out.println("Request code 2: " + requestCode);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeViews();
            } else {
                System.out.println("--------location");
//                System.out.println("GRANT RESULTS: "  + grantResults + ", " + grantResults[0] + ", " + PackageManager.PERMISSION_GRANTED);
                openAlertDialog("LOCATION");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private void openAlertDialog(final String perm) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please allow all the permissions.");
        alertDialogBuilder.setPositiveButton("Try again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        checkForPermissions(perm);
                    }
                });

        alertDialogBuilder.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:dk.redweb.intern.findetlokum"));
                startActivity(i);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



}
