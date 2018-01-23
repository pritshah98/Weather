package com.pritshah.weatherapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pritshah on 8/12/17.
 */

public class RetrieveAPITask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            URLConnection con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            if ((inputLine = in.readLine()) != null) {
                return inputLine;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
