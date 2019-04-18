package com.example.wiss;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class GeoListener implements LocationListener {
    private final String TAG = "GEO_DEBUG";

    @Override
    public void onLocationChanged(Location location){
        String provider = location.getProvider();
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        long time = location.getTime();
        String pattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(pattern));
        String stime = sdf.format(time);

        Log.d(TAG,String.format("(%s) Location by %s + (%f, %f)",stime,provider,lat,lon));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){ int stat = status; }

    @Override
    public void onProviderEnabled(String provider) { Log.d(TAG, "Provider enabled" + provider); }

    @Override
    public void onProviderDisabled(String provider) { Log.d(TAG, "Provider disabled" + provider); }

}
