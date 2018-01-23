package com.pritshah.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemClickedActivity extends AppCompatActivity {

    private ArrayList<String> hoursSplit = new ArrayList<>(8);
    private ConstraintLayout cLayout;
    private ListView listview;
    private TextView day;
    public static int pos = 0;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_clicked);
        cLayout = (ConstraintLayout) findViewById(R.id.itemClickedLayout);
        day = (TextView) findViewById(R.id.dayNumber);
        listview = (ListView) findViewById(R.id.itemClickedView);

        hoursSplit.add(0, "12am - 3am");
        hoursSplit.add(1, "3am - 6am");
        hoursSplit.add(2, "6am - 9am");
        hoursSplit.add(3, "9am - 12pm");
        hoursSplit.add(4, "12pm - 3pm");
        hoursSplit.add(5, "3pm - 6pm");
        hoursSplit.add(6, "6pm - 9pm");
        hoursSplit.add(7, "9pm - 12am");

        cLayout.setBackgroundColor(Color.parseColor("#D3D3D3"));

        final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, hoursSplit);

        listview.setAdapter(adapter);

        if (ForecastActivity.index == 0) {
            day.setText(ForecastActivity.dateList.get(0));
        }
        if (ForecastActivity.index == 1) {
            day.setText(ForecastActivity.dateList.get(1));
        }
        if (ForecastActivity.index == 2) {
            day.setText(ForecastActivity.dateList.get(2));
        }
        if (ForecastActivity.index == 3) {
            day.setText(ForecastActivity.dateList.get(3));
        }
        if (ForecastActivity.index == 4) {
            day.setText(ForecastActivity.dateList.get(4));
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < hoursSplit.size(); i++) {
                    if (position == i) {
                        pos = i;
                        Intent intent = new Intent(getApplicationContext(), ViewForecastActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }
            }
        });
    }
}
