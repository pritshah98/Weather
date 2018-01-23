package com.pritshah.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ForecastActivity extends Fragment {

    private final String WEATHER_API_KEY = "&APPID=bedaa1310b02488bd9d2aba085d6cc5f";
    private final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private final String units = "&units=imperial";
    private EditText location;
    private TextView locationLabel;
    private ConstraintLayout cLayout;
    private String parsedData = "";
    public static ArrayList<String> weatherType = new ArrayList<>();
    public static ArrayList<String> weatherDescription = new ArrayList<>();
    public static ArrayList tempArray = new ArrayList();
    public static ArrayList tempMinArray = new ArrayList();
    public static ArrayList tempMaxArray = new ArrayList();
    public static int index = 0;
    public static String cityCountry = "";
    private ListView listview;
    public static List<String> dateList = new ArrayList<>(5);

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_forecast, container, false);
        location = (EditText) rootView.findViewById(R.id.enterLocation);
        locationLabel = (TextView) rootView.findViewById(R.id.forecastLocationLabel);
        cLayout = (ConstraintLayout) rootView.findViewById(R.id.forecastLayout);
        listview = (ListView) rootView.findViewById(R.id.forecastListView);

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 5; i++)
        {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(i, format.format(calendar.getTime()));
        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });

        location.setClickable(true);

        locationLabel.setVisibility(View.GONE);

        final ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, dateList);

        cLayout.setBackgroundColor(Color.parseColor("#FFF8DC"));

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
                    } else {
                        Toast toast = Toast.makeText(getContext(), "Please enter in a city name." , Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    try {
                        if (parsedData != null) {
                            location.setText("");
                            JSONObject obj = new JSONObject(parsedData);
                            JSONArray list = obj.getJSONArray("list");
                            JSONObject cityJSON = (JSONObject) obj.get("city");

                            String country = (String) cityJSON.get("country");
                            String city = (String) cityJSON.get("name");

                            locationLabel.setVisibility(View.VISIBLE);
                            locationLabel.setText(city + ", " + country);

                            cityCountry = city + ", " + country;

                            listview.setAdapter(adapter);

                            for (int i = 0; i < 40; i++) {
                                JSONObject mainObj = (JSONObject) list.getJSONObject(i).get("main");
                                JSONArray weatherObj = (JSONArray) list.getJSONObject(i).get("weather");
                                tempArray.add(mainObj.get("temp"));
                                tempMaxArray.add(mainObj.get("temp_max"));
                                tempMinArray.add(mainObj.get("temp_min"));
                                weatherType.add((String) weatherObj.getJSONObject(0).get("main"));
                                weatherDescription.add((String) weatherObj.getJSONObject(0).get("description"));
                            }

                        } else {
                            Toast toast = Toast.makeText(getContext(), "City not found." , Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                    } else {
                        Toast toast = Toast.makeText(getContext(), "Please enter in a city name." , Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    try {
                        if (parsedData != null) {
                            location.setText("");
                            JSONObject obj = new JSONObject(parsedData);
                            JSONArray list = obj.getJSONArray("list");
                            JSONObject cityJSON = (JSONObject) obj.get("city");

                            String country = (String) cityJSON.get("country");
                            String city = (String) cityJSON.get("name");

                            cityCountry = city + ", " + country;

                            locationLabel.setVisibility(View.VISIBLE);
                            locationLabel.setText(city + ", " + country);

                            listview.setAdapter(adapter);

                            for (int i = 0; i < 40; i++) {
                                JSONObject mainObj = (JSONObject) list.getJSONObject(i).get("main");
                                JSONArray weatherObj = (JSONArray) list.getJSONObject(i).get("weather");
                                tempArray.add(mainObj.get("temp"));
                                tempMaxArray.add(mainObj.get("temp_max"));
                                tempMinArray.add(mainObj.get("temp_min"));
                                weatherType.add((String) weatherObj.getJSONObject(0).get("main"));
                                weatherDescription.add((String) weatherObj.getJSONObject(0).get("description"));
                            }

                        } else {
                            Toast toast = Toast.makeText(getContext(), "City not found." , Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < dateList.size(); i++) {
                    if (position == i) {
                        index = i;
                        Intent intent = new Intent(getContext(), ItemClickedActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }
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
            }
        }
    }

}
