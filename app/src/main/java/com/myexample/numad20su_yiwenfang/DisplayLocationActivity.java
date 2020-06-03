package com.myexample.numad20su_yiwenfang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DisplayLocationActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_location);
    }

    public void displayLocation(View view) {
        // Check permission for GPS location, network location, and network access
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
            // Get location information if permission granted
            locationAssistant();
        } else {
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                    MY_PERMISSIONS_REQUEST_CODE);
        }
    }

    // Handle the permissions request response
    // Code is cited from https://developer.android.com/training/permissions/requesting
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length == 3
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, get location information
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    locationAssistant();
                } else {
                    // Permission denied
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void locationAssistant() {
        LocationManager myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String currentProvider;
        // Get enabled location providers
        List<String> myProviders = myLocationManager.getProviders(true);

        // Choose appropriate location providers, the preference can be changed if necessary
        if (myProviders.contains(LocationManager.NETWORK_PROVIDER)) {
            currentProvider = LocationManager.NETWORK_PROVIDER;
        } else if (myProviders.contains(LocationManager.GPS_PROVIDER)) {
            currentProvider = LocationManager.GPS_PROVIDER;
        } else {
            // Enable location providers in the Android Setting if no location providers found
            Toast.makeText(this, "No location providers! " +
                    "Enable location providers!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            return;
        }

        Location myLocation = myLocationManager.getLastKnownLocation(currentProvider);
        if (myLocation != null) {
            // Not the first time to run
            display(myLocation);
        } else {
            // First time to run
            myLocationManager.requestLocationUpdates(currentProvider,
                    0, 0, myListener);
        }
    }

    // Display the latitude and longitude values in the TextViews
    private void display(Location location) {
        TextView textView7 = findViewById(R.id.textView7);
        textView7.setText(String.valueOf(location.getLatitude()));
        TextView textView8 = findViewById(R.id.textView8);
        textView8.setText(String.valueOf(location.getLongitude()));
    }

    // Listen to the location updates
    LocationListener myListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            display(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
