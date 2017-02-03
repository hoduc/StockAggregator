package com.stockaggregator;

import android.os.Build;

import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.stockaggregator.Constant.*;

import java.net.URL;

/**
 * Created by ducho on 4/11/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.JELLY_BEAN)
public class GetAutoDataTaskTest {
    GetAutoDataTask autoDataTask;
    //String urlStr = "http://localhost/HW8/server.php?input=AA";

    public static void assertJsonArray( JSONArray a, JSONArray b ) throws Exception{
        assertTrue(a.length() == b.length());
        assertTrue(a.toString().equals(b.toString()));
        /*for( int i  = 0; i < a.length(); i++ ) {
            assertTrue(a.getJSONObject(i).toString().equals(b.getJSONObject(i)));
        }*/
    }
    @Test
    public void testAA() throws Exception{
        final JSONArray expectedAAJsonArray = new JSONArray("[{\"Symbol\":\"AA\",\"Name\":\"Alcoa Inc\",\"Exchange\":\"NYSE\"},{\"Symbol\":\"AA\",\"Name\":\"Alcoa Inc\",\"Exchange\":\"BATS Trading Inc\"},{\"Symbol\":\"AAON\",\"Name\":\"AAON Inc\",\"Exchange\":\"NASDAQ\"},{\"Symbol\":\"AAP\",\"Name\":\"Advance Auto Parts Inc\",\"Exchange\":\"NYSE\"},{\"Symbol\":\"AAPL\",\"Name\":\"Apple Inc\",\"Exchange\":\"NASDAQ\"},{\"Symbol\":\"AAN\",\"Name\":\"Aaron's Inc\",\"Exchange\":\"NYSE\"},{\"Symbol\":\"AIR\",\"Name\":\"AAR Corp\",\"Exchange\":\"NYSE\"},{\"Symbol\":\"AAC\",\"Name\":\"AAC Holdings Inc\",\"Exchange\":\"NYSE\"},{\"Symbol\":\"AIR\",\"Name\":\"AAR Corp\",\"Exchange\":\"BATS Trading Inc\"},{\"Symbol\":\"AAN\",\"Name\":\"Aaron's Inc\",\"Exchange\":\"BATS Trading Inc\"}]");

        autoDataTask = new GetAutoDataTask() {
            @Override
            protected void onPostExecute(JSONArray jsonArray) {
                try {
                    assertJsonArray(expectedAAJsonArray, jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        autoDataTask.execute( new URL( SERVER_URL + "?input=AA" ) );
    }
}
