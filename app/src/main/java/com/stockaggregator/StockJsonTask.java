package com.stockaggregator;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by ducho on 4/11/16.
 */
public class StockJsonTask extends AsyncTask<URL, Integer, JSONArray> {
    @Override
    protected JSONArray doInBackground(URL... params) {
        JSONArray jsons = null;
        for (URL url : params) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection)url.openConnection();
                InputStream in = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                jsons = new JSONArray( result.toString( ) );

            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                if ( connection != null )
                    connection.disconnect();
                return jsons;
            }
        }
        return jsons;

    }

}
