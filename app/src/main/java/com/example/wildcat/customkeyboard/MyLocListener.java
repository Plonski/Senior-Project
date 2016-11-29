package com.example.wildcat.customkeyboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;


public class MyLocListener implements LocationListener {

    Firebase mRef;


    public void onLocationChanged(Location location){

        if(location != null){
            Log.e("Latitutude : ",  "" + location.getLatitude());
            Log.e("Longitude : ",  "" + location.getLongitude());
            Firebase mRefChildLongitude = mRef.child("Longitude");
            Firebase mRefChildLatitude = mRef.child("Latitude");
            mRefChildLongitude.setValue(location.getLongitude());
            mRefChildLatitude.setValue(location.getLongitude());
        }
    }

    public void setFirebase(Firebase mref){
        mRef = mref;
    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onProviderDisabled(String provider){

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }
}
