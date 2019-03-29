package com.fiiandroid.lab2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

public class LocationActivity extends Activity {

    private LocationManager locationManager;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION_SERVICE = 2;

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            TextView currentLatitude = findViewById(R.id.current_latitude);
            TextView currentLongitude = findViewById(R.id.current_longitude);
            boolean isNorth = location.getLatitude() >= 0.0;
            boolean isEast = location.getLongitude() >= 0.0;
            String latitudeText = String.valueOf(location.getLatitude()) + "\u00b0" + (isNorth ? "N" : "S");
            String longitudeText = String.valueOf(location.getLongitude()) + "\u00b0" + (isEast ? "E" : "W");
            currentLatitude.setText(latitudeText);
            currentLongitude.setText(longitudeText);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        requestLocationService();
    }

    private void requestLocationService() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION_SERVICE);

            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new MyLocationListener());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION_SERVICE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new MyLocationListener());
                    } catch (SecurityException exception) {
                        Log.e("REQ_DENIED", "GPS service request denied");
                        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                                setMessage("We cannot provide the location coordinates without your permission!")
                                .setTitle("Error Location")
                                .create();
                        alertDialog.show();
                    }
                }
            }
        }
    }
}
