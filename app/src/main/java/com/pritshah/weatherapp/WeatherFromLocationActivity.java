package com.pritshah.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class WeatherFromLocationActivity extends Fragment {

    private ConstraintLayout cLayout;
    private double longitude = 0;
    private double latitude = 0;
    private final String WEATHER_API_KEY = "&APPID=bedaa1310b02488bd9d2aba085d6cc5f";
    private final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?";
    private final String units = "&units=imperial";
    private final String lat = "lat=";
    private final String lon = "&lon=";
    private Location location;
    private LocationManager manager;
    private String parsedData;
    private TextView locationLabel;
    private ImageView image;
    private TextView weatherT;
    private TextView weatherD;
    private TextView current;
    private TextView minT;
    private TextView maxT;
    private TextView label1;
    private TextView label2;
    private TextView label3;
    private TextView label4;
    private TextView label5;
    private int lonRound = 0;
    private int latRound = 0;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_weather_from_location, container, false);
        cLayout = (ConstraintLayout) rootView.findViewById(R.id.weatherFromLocLayout);
        cLayout.setBackgroundColor(Color.parseColor("#FFF8DC"));
        locationLabel = (TextView) rootView.findViewById(R.id.weatherLocationLabel);
        image = (ImageView) rootView.findViewById(R.id.weatherImage);
        current = (TextView) rootView.findViewById(R.id.textView3);
        weatherT = (TextView) rootView.findViewById(R.id.textView5);
        weatherD = (TextView) rootView.findViewById(R.id.textView7);
        minT = (TextView) rootView.findViewById(R.id.textView9);
        maxT = (TextView) rootView.findViewById(R.id.textView11);
        label1 = (TextView) rootView.findViewById(R.id.label1);
        label2 = (TextView) rootView.findViewById(R.id.label2);
        label3 = (TextView) rootView.findViewById(R.id.label3);
        label4 = (TextView) rootView.findViewById(R.id.label4);
        label5 = (TextView) rootView.findViewById(R.id.label5);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        try {
//            if (!checkPermission(getContext())) {
//                showPermissionDialog();
//            } else {
//                if (getLocation() != null) {
//                    longitude = Double.parseDouble(String.format("%.0f", getLocation().getLongitude()));
//                    latitude = Double.parseDouble(String.format("%.0f", getLocation().getLatitude()));
//                    lonRound = (int) Math.round(longitude);
//                    latRound = (int) Math.round(latitude);
//                }
//                Log.d("LOCATION", String.valueOf(longitude) + " " + String.valueOf(latitude));
//            }
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }

        try {
            if (!checkPermission(getContext())) {
                showPermissionDialog();
            } else {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    longitude = location.getLongitude();
                                    latitude = location.getLatitude();
                                }
                            }
                        });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (latitude != 0 & longitude != 0) {
            try {
                parsedData = new RetrieveAPITask().execute(OPEN_WEATHER_MAP_API + lat + latRound
                        + lon + lonRound + WEATHER_API_KEY + units).get();
                getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rootView;
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
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

    public static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void showPermissionDialog() {
        ActivityCompat.requestPermissions(
                getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    private void getData() {
        try {
            if (parsedData != null) {

                Log.d("PARSED DATA", parsedData);

                JSONObject obj = new JSONObject(parsedData);
                JSONObject coordObj = obj.getJSONObject("coord");
                JSONArray weatherObj = obj.getJSONArray("weather");
                JSONObject mainObj = obj.getJSONObject("main");
                JSONObject sysObj = obj.getJSONObject("sys");

                JSONObject weatherJSON = (JSONObject) weatherObj.get(0);
                String weatherType = (String) weatherJSON.get("main");
                String weatherDescription = (String) weatherJSON.get("description");
                Object minTemp = mainObj.get("temp_min");
                Object maxTemp = mainObj.get("temp_max");
                String country = "";
                if (sysObj.has("country")) {
                    country = (String) sysObj.get("country");
                }
                String city = (String) (obj.get("name"));
                Object currentTemp = mainObj.get("temp");

                if (!country.equals("")) {
                    locationLabel.setText(city + ", " + country);
                } else {
                    locationLabel.setText(city);
                }
                current.setText(String.valueOf(currentTemp));
                weatherT.setText(weatherType);
                weatherD.setText(weatherDescription);
                minT.setText(String.valueOf(minTemp));
                maxT.setText(String.valueOf(maxTemp));

                label1.setVisibility(View.VISIBLE);
                label2.setVisibility(View.VISIBLE);
                label3.setVisibility(View.VISIBLE);
                label4.setVisibility(View.VISIBLE);
                label5.setVisibility(View.VISIBLE);
                locationLabel.setVisibility(View.VISIBLE);
                current.setVisibility(View.VISIBLE);
                weatherT.setVisibility(View.VISIBLE);
                weatherD.setVisibility(View.VISIBLE);
                minT.setVisibility(View.VISIBLE);
                maxT.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);

                if (!weatherType.toLowerCase().contains("rain") && !weatherType.toLowerCase().contains("storm")) {
                    cLayout.setBackgroundColor(Color.parseColor("#ADD8E6"));
                } else {
                    cLayout.setBackgroundColor(Color.parseColor("#7B68EE"));
                    label1.setTextColor(Color.parseColor("#FFFFFF"));
                    label2.setTextColor(Color.parseColor("#FFFFFF"));
                    label3.setTextColor(Color.parseColor("#FFFFFF"));
                    label4.setTextColor(Color.parseColor("#FFFFFF"));
                    label5.setTextColor(Color.parseColor("#FFFFFF"));
                    locationLabel.setTextColor(Color.parseColor("#FFFFFF"));
                    current.setTextColor(Color.parseColor("#FFFFFF"));
                    weatherT.setTextColor(Color.parseColor("#FFFFFF"));
                    weatherD.setTextColor(Color.parseColor("#FFFFFF"));
                    minT.setTextColor(Color.parseColor("#FFFFFF"));
                    maxT.setTextColor(Color.parseColor("#FFFFFF"));
                }

                if (weatherType.toLowerCase().contains("cloud")) {
                    image.setImageResource(R.drawable.cloud);
                } else if (weatherType.toLowerCase().contains("rain")
                        || weatherType.toLowerCase().contains("storm")) {
                    image.setImageResource(R.drawable.rain);
                } else {
                    image.setImageResource(R.drawable.sun);
                }

            } else {
                label1.setVisibility(View.GONE);
                label2.setVisibility(View.GONE);
                label3.setVisibility(View.GONE);
                label4.setVisibility(View.GONE);
                label5.setVisibility(View.GONE);
                locationLabel.setVisibility(View.GONE);
                current.setVisibility(View.GONE);
                weatherT.setVisibility(View.GONE);
                weatherD.setVisibility(View.GONE);
                minT.setVisibility(View.GONE);
                maxT.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                Toast toast = Toast.makeText(getContext(), "Current location unknown." , Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location getLocation() {
        try {
            manager = (LocationManager) getContext()
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = manager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = manager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                showPermissionDialog();
            } else {
                boolean canGetLocation = true;
                if (isNetworkEnabled) {
                    try {
                        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    Log.d("Network", "Network Enabled");
                    if (manager != null) {
                        try {
                            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        try {
                            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                        Log.d("GPS", "GPS Enabled");
                        if (manager != null) {
                            try {
                                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

}
