package com.pritshah.weatherapp;

import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewForecastActivity extends AppCompatActivity {

    private TextView day;
    private ConstraintLayout cLayout;
    private TextView location;
    private ImageView image;
    private TextView weatherType;
    private TextView description;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView label1;
    private TextView label2;
    private TextView label3;
    private TextView label4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forecast);

        day = (TextView) findViewById(R.id.viewDayNumber);
        location = (TextView) findViewById(R.id.viewLocation);
        image = (ImageView) findViewById(R.id.viewImage);
        weatherType = (TextView) findViewById(R.id.viewWeatherType);
        description = (TextView) findViewById(R.id.viewDescription);
        minTemp = (TextView) findViewById(R.id.viewMinTemp);
        maxTemp = (TextView) findViewById(R.id.viewMaxTemp);
        label1 = (TextView) findViewById(R.id.textView15);
        label2 = (TextView) findViewById(R.id.textView17);
        label3 = (TextView) findViewById(R.id.textView19);
        label4 = (TextView) findViewById(R.id.textView21);
        cLayout = (ConstraintLayout) findViewById(R.id.viewForecastLayout);

        location.setText(ForecastActivity.cityCountry);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                if (ForecastActivity.index == i && ItemClickedActivity.pos == j) {
                    String type = ForecastActivity.weatherType.get(i * 8 + j);
                    weatherType.setText(type);
                    description.setText(ForecastActivity.weatherDescription.get(i * 8 + j));
                    minTemp.setText(String.valueOf(ForecastActivity.tempMinArray.get(i * 8 + j)));
                    maxTemp.setText(String.valueOf(ForecastActivity.tempMaxArray.get(i * 8 + j)));
                    if (!type.toLowerCase().contains("rain") && !type.toLowerCase().contains("storm")) {
                        cLayout.setBackgroundColor(Color.parseColor("#ADD8E6"));
                    } else {
                        cLayout.setBackgroundColor(Color.parseColor("#7B68EE"));
                        location.setTextColor(Color.parseColor("#FFFFFF"));
                        day.setTextColor(Color.parseColor("#FFFFFF"));
                        location.setTextColor(Color.parseColor("#FFFFFF"));
                        weatherType.setTextColor(Color.parseColor("#FFFFFF"));
                        description.setTextColor(Color.parseColor("#FFFFFF"));
                        minTemp.setTextColor(Color.parseColor("#FFFFFF"));
                        maxTemp.setTextColor(Color.parseColor("#FFFFFF"));
                        label1.setTextColor(Color.parseColor("#FFFFFF"));
                        label2.setTextColor(Color.parseColor("#FFFFFF"));
                        label3.setTextColor(Color.parseColor("#FFFFFF"));
                        label4.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    if (type.toLowerCase().contains("cloud")) {
                        image.setImageResource(R.drawable.cloud);
                    } else if (type.toLowerCase().contains("rain")
                            || type.toLowerCase().contains("storm")) {
                        image.setImageResource(R.drawable.rain);
                    } else {
                        image.setImageResource(R.drawable.sun);
                    }
                }
            }
        }

        if (ForecastActivity.index == 0) {
            day.setText(ForecastActivity.dateList.get(0) + ": " + getHour());
        }
        if (ForecastActivity.index == 1) {
            day.setText(ForecastActivity.dateList.get(1) + ": " + getHour());
        }
        if (ForecastActivity.index == 2) {
            day.setText(ForecastActivity.dateList.get(2) + ": " + getHour());
        }
        if (ForecastActivity.index == 3) {
            day.setText(ForecastActivity.dateList.get(3) + ": " + getHour());
        }
        if (ForecastActivity.index == 4) {
            day.setText(ForecastActivity.dateList.get(4) + ": " + getHour());
        }
    }

    public String getHour() {
        if (ItemClickedActivity.pos == 0) {
            return "12am - 3am";
        }
        if (ItemClickedActivity.pos == 1) {
            return "3am - 6am";
        }
        if (ItemClickedActivity.pos == 2) {
            return "6am - 9am";
        }
        if (ItemClickedActivity.pos == 3) {
            return "9am - 12pm";
        }
        if (ItemClickedActivity.pos == 4) {
            return "12pm - 3pm";
        }
        if (ItemClickedActivity.pos == 5) {
            return "3pm - 6pm";
        }
        if (ItemClickedActivity.pos == 6) {
            return "6pm - 9pm";
        }
        if (ItemClickedActivity.pos == 7) {
            return "9pm - 12am";
        }
        return "";
    }
}
