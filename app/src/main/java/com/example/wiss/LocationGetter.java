package com.example.wiss;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationGetter {
    private Timer timer;
    private LocationManager locationManager;
    public LocationResult locationResult;
    private boolean gpsEnabled = false;
    private boolean networkEnabled = false;

    public boolean getLocation(Context context, LocationResult result) {
        locationResult = result;

        if (locationManager == null) {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
        }

        try {
            gpsEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            networkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        // stop if no providers are enabled
        if (!gpsEnabled && !networkEnabled) {
            System.out.println("no providers enabled");
            return false;
        }

        if (gpsEnabled) {
            try {
                System.out.println("gps enabled");
                locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0,locationListenerGps);
            }catch (SecurityException e) {}

        }

        if (networkEnabled) {
            try {
                System.out.println("network enabled");
                locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
            }catch (SecurityException e) {}

        }

        timer = new Timer();
        System.out.println("timer");
        timer.schedule(new GetLastLocation(), 20000);

        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            System.out.println("--------location changed gps---------");
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            System.out.println("--------location changed gps---------");
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            System.out.println("run");
            locationManager.removeUpdates(locationListenerGps);
            locationManager.removeUpdates(locationListenerNetwork);

            Location netLocation = null, gpsLocation = null;

            if (gpsEnabled)
                try{
                    gpsLocation = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }catch(SecurityException e){
                    gpsLocation = null;
                }


            if (networkEnabled)
                try{
                    netLocation = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }catch(SecurityException e){
                    netLocation = null;
                }


            // if there are both values use the latest one
            if (gpsLocation != null && netLocation != null) {
                if (gpsLocation.getTime() > netLocation.getTime())
                    locationResult.gotLocation(gpsLocation);
                else
                    locationResult.gotLocation(netLocation);

                return;
            }

            if (gpsLocation != null) {
                locationResult.gotLocation(gpsLocation);
                return;
            }

            if (netLocation != null) {
                locationResult.gotLocation(netLocation);
                return;
            }

            locationResult.gotLocation(null);
        }
    }

    // Represents a Location Result
    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}