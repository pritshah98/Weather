package com.pritshah.weatherapp;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchFragment extends Fragment {

    private final String WEATHER_API_KEY = "&APPID=bedaa1310b02488bd9d2aba085d6cc5f";
    private final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final String units = "&units=imperial";
    private EditText location;
    private TextView weatherT;
    private TextView weatherD;
    private TextView current;
    private TextView minT;
    private TextView maxT;
    private TextView locationLabel;
    private TextView label1;
    private TextView label2;
    private TextView label3;
    private TextView label4;
    private TextView label5;
    private ImageView image;
    private String parsedData = "";
    private ConstraintLayout cLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View rootView = inflater.inflate(R.layout.activity_search_fragment, container, false);
        location = (EditText) rootView.findViewById(R.id.enterLocation);
        weatherT = (TextView) rootView.findViewById(R.id.weatherType);
        weatherD = (TextView) rootView.findViewById(R.id.description);
        current = (TextView) rootView.findViewById(R.id.currentTemp);
        minT = (TextView) rootView.findViewById(R.id.minTemp);
        maxT = (TextView) rootView.findViewById(R.id.maxTemp);
        locationLabel = (TextView) rootView.findViewById(R.id.forecastLocationLabel);
        cLayout = (ConstraintLayout) rootView.findViewById(R.id.layout);
        label1 = (TextView) rootView.findViewById(R.id.label1);
        label2 = (TextView) rootView.findViewById(R.id.label2);
        label3 = (TextView) rootView.findViewById(R.id.label3);
        label4 = (TextView) rootView.findViewById(R.id.label4);
        label5 = (TextView) rootView.findViewById(R.id.label5);
        image = (ImageView) rootView.findViewById(R.id.sunImage);

        cLayout.setBackgroundColor(Color.parseColor("#FFF8DC"));

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

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cLayout.setBackgroundColor(Color.parseColor("#FFF8DC"));
                location.setTextColor(Color.parseColor("#000000"));
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
            }
        });

        location.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String loc = location.getText().toString();

                    if (loc.length() > 0) {
                        try {
                            parsedData = new RetrieveAPITask().execute(OPEN_WEATHER_MAP_API + loc + WEATHER_API_KEY + units).get();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (parsedData != null && loc.length() > 0) {

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
                                String country = (String) sysObj.get("country");
                                String city = (String) (obj.get("name"));
                                Object currentTemp = mainObj.get("temp");

                                if (!weatherType.toLowerCase().contains("rain") && !weatherType.toLowerCase().contains("storm")) {
                                    cLayout.setBackgroundColor(Color.parseColor("#ADD8E6"));
                                    location.setTextColor(Color.parseColor("#000000"));
                                    label1.setTextColor(Color.parseColor("#000000"));
                                    label2.setTextColor(Color.parseColor("#000000"));
                                    label3.setTextColor(Color.parseColor("#000000"));
                                    label4.setTextColor(Color.parseColor("#000000"));
                                    label5.setTextColor(Color.parseColor("#000000"));
                                    locationLabel.setTextColor(Color.parseColor("#000000"));
                                    current.setTextColor(Color.parseColor("#000000"));
                                    weatherT.setTextColor(Color.parseColor("#000000"));
                                    weatherD.setTextColor(Color.parseColor("#000000"));
                                    minT.setTextColor(Color.parseColor("#000000"));
                                    maxT.setTextColor(Color.parseColor("#000000"));
                                } else {
                                    cLayout.setBackgroundColor(Color.parseColor("#7B68EE"));
                                    location.setTextColor(Color.parseColor("#FFFFFF"));
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

                                location.setText("");
                                locationLabel.setText(city + ", " + country);
                                current.setText(String.valueOf(currentTemp) + (char) 0x00B0 + 'F');
                                weatherT.setText(weatherType);
                                weatherD.setText(weatherDescription);
                                minT.setText(String.valueOf(minTemp) + (char) 0x00B0 + 'F');
                                maxT.setText(String.valueOf(maxTemp) + (char) 0x00B0 + 'F');

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
                            } else {
                                Toast toast = Toast.makeText(getContext(), "City not found." , Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
                        Toast toast = Toast.makeText(getContext(), "Please enter in a city name." , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    return true;
                }
                return false;
            }
        });

        location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String loc = location.getText().toString();

                    if (loc.length() > 0) {
                        try {
                            parsedData = new RetrieveAPITask().execute(OPEN_WEATHER_MAP_API + loc + WEATHER_API_KEY + units).get();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (parsedData != null && loc.length() > 0) {

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
                                String country = (String) sysObj.get("country");
                                String city = (String) (obj.get("name"));
                                Object currentTemp = mainObj.get("temp");

                                if (!weatherType.contains("rain")) {
                                    cLayout.setBackgroundColor(Color.parseColor("#ADD8E6"));
                                } else {
                                    cLayout.setBackgroundColor(Color.parseColor("#00008B"));
                                    location.setTextColor(Color.parseColor("#FFFFFF"));
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

                                location.setText("");
                                locationLabel.setText(city + ", " + country);
                                current.setText(String.valueOf(currentTemp) + (char) 0x00B0 + 'F');
                                weatherT.setText(weatherType);
                                weatherD.setText(weatherDescription);
                                minT.setText(String.valueOf(minTemp) + (char) 0x00B0 + 'F');
                                maxT.setText(String.valueOf(maxTemp) + (char) 0x00B0 + 'F');

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
                            } else {
                                Toast toast = Toast.makeText(getContext(), "City not found." , Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
                        Toast toast = Toast.makeText(getContext(), "Please enter in a city name." , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    return true;
                }


                return false;
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager.isAcceptingText()) {
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                cLayout.setBackgroundColor(Color.parseColor("#FFF8DC"));
                location.setTextColor(Color.parseColor("#000000"));
                label1.setTextColor(Color.parseColor("#000000"));
                label2.setTextColor(Color.parseColor("#000000"));
                label3.setTextColor(Color.parseColor("#000000"));
                label4.setTextColor(Color.parseColor("#000000"));
                label5.setTextColor(Color.parseColor("#000000"));
                locationLabel.setTextColor(Color.parseColor("#000000"));
                current.setTextColor(Color.parseColor("#000000"));
                weatherT.setTextColor(Color.parseColor("#000000"));
                weatherD.setTextColor(Color.parseColor("#000000"));
                minT.setTextColor(Color.parseColor("#000000"));
                maxT.setTextColor(Color.parseColor("#000000"));
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
            }
        }
    }
}
