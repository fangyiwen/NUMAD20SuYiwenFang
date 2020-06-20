package com.myexample.numad20su_yiwenfang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class AtYourServiceActivity extends AppCompatActivity {

    private TextView textView11;
    private ApiTask myTask;
    private String pending = "(Pending...)", inputPostCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        textView11 = findViewById(R.id.textView11);
    }

    public void send(View view) {
        EditText postCodeEditText = findViewById(R.id.editText3);
        String postCode = postCodeEditText.getText().toString();
        inputPostCode = "\nInput post code: " + postCode;
        textView11.setText(inputPostCode + "\n\n" + pending);
        // The post code looking up API is provided by Zippopotam.us
        String url = "https://api.zippopotam.us/us/" + postCode;
        // Start AsyncTask
        myTask = new ApiTask();
        myTask.execute(url, postCode);
    }

    private class ApiTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result;
            try {
                URL url = new URL(params[0]);

                // Start HttpURLConnection
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                connect.setDoInput(true);
                connect.connect();

                InputStream inputStream;
                inputStream = connect.getInputStream();

                // Deal with response
                Scanner myScanner = new Scanner(inputStream).useDelimiter("\\A");
                String response = myScanner.hasNext() ? myScanner.next() : "";

                // Deal with JSON response format
                JSONObject myJsonObject = new JSONObject(response);
                String postCode = myJsonObject.getString("post code");
                String country = myJsonObject.getString("country");
                String countryAbbreviation = myJsonObject.getString("country abbreviation");

                JSONObject places = myJsonObject.getJSONArray("places").getJSONObject(0);
                String placeName = places.getString("place name");
                String longitude = places.getString("longitude");
                String state = places.getString("state");
                String stateAbbreviation = places.getString("state abbreviation");
                String latitude = places.getString("latitude");

                result = "\n\nRetrieved Information\n\n"
                        + "Post code:\n" + postCode + "\n\n"
                        + "Location Information:\n"
                        + "Place Name: " + placeName + "\n"
                        + "State: " + state + " (" + stateAbbreviation + ")\n"
                        + "Country: " + country + " (" + countryAbbreviation + ")\n\n"
                        + "Geo Coordinates:\n"
                        + "Latitude: " + latitude + "\n"
                        + "Longitude: " + longitude;
                return result;
            } catch (MalformedURLException e) {
                result = "\n\nError: MalformedURLException";
                return result;
            } catch (ProtocolException e) {
                result = "\n\nError: ProtocolException";
                return result;
            } catch (IOException e) {
                result = "\n\nError: IOException";
                return result;
            } catch (JSONException e) {
                result = "\n\nError: JSONException";
                return result;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // Update and show web service connection status
        }

        @Override
        protected void onPostExecute(String result) {
            // Display the text returned by the doInBackground() method
            textView11.setText(inputPostCode + result);
        }
    }
}
