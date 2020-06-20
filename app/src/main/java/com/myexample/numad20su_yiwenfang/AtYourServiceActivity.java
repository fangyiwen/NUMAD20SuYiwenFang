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

    private TextView textView10, textView11;
    private ApiTask myTask;
    private String inputPostCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel AsyncTask in the onDestroy() method to avoid memory leak
        if (myTask != null && myTask.getStatus() == AsyncTask.Status.RUNNING) {
            myTask.cancel(true);
        }
    }

    public void send(View view) {
        EditText postCodeEditText = findViewById(R.id.editText3);
        String postCode = postCodeEditText.getText().toString();
        // Check empty input
        if (postCode.equals("")) {
            textView11.setText("\nError: Post code can't be empty!");
            return;
        }
        // Disable "Send" and "Test API" before the retrieved process is finished
        findViewById(R.id.button14).setEnabled(false);
        findViewById(R.id.button17).setEnabled(false);

        inputPostCode = "\nInput post code: " + postCode;
        textView11.setText(inputPostCode + "\n\n(Pending...)");
        // Reset input EditText as empty after button click
        postCodeEditText.setText("");
        // The post code looking up API is provided by Zippopotam.us
        String url = "https://api.zippopotam.us/us/" + postCode;
        // Start AsyncTask
        myTask = new ApiTask();
        myTask.execute(url);
    }

    public void testApi(View view) {
        // Use API predefined post code "90210" to check the API service works well
        EditText postCodeEditText = findViewById(R.id.editText3);
        postCodeEditText.setText("90210");
        findViewById(R.id.button14).performClick();
    }

    private class ApiTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            // Check if cancelled
            if (isCancelled()) {
                return null;
            }

            String result;
            try {
                // Update web service connection status
                publishProgress("Web Service Connection Status: Start connection");

                URL url = new URL(params[0]);
                publishProgress(
                        "Web Service Connection Status: Connecting... (URL is OK)");

                // Start HttpURLConnection
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                connect.setDoInput(true);

                // Check the API service still works
                try {
                    connect.connect();
                } catch (IOException e) {
                    publishProgress("Web Service Connection Status: Disconnected");
                    result = "\n\nError: IOException (Can't connect to the API server)";
                    return result;
                }
                publishProgress("Web Service Connection Status: Connection is successful");

                InputStream inputStream;
                // Check InputStream is successful for the post code request
                try {
                    inputStream = connect.getInputStream();
                } catch (IOException e) {
                    publishProgress("Web Service Connection Status: Disconnected");
                    result = "\n\nError: IOException " +
                            "(API provides no information for this post code)";
                    return result;
                }

                // Deal with response
                Scanner myScanner = new Scanner(inputStream).useDelimiter("\\A");
                String response = myScanner.hasNext() ? myScanner.next() : "";
                publishProgress("Web Service Connection Status: Disconnected " +
                        "(Data is retrieved from API)");

                // Deal with JSON response format
                JSONObject myJsonObject = new JSONObject(response);
                String postCode = myJsonObject.getString("post code");
                String country = myJsonObject.getString("country");
                String countryAbbreviation = myJsonObject.getString("country abbreviation");

                JSONObject places =
                        myJsonObject.getJSONArray("places").getJSONObject(0);
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
                publishProgress("Web Service Connection Status: Disconnected");
                return result;
            } catch (MalformedURLException e) {
                publishProgress("Web Service Connection Status: Disconnected");
                result = "\n\nError: MalformedURLException (A malformed URL has occurred. " +
                        "Either no legal protocol could be found the URL could not be parsed)";
                return result;
            } catch (ProtocolException e) {
                publishProgress("Web Service Connection Status: Disconnected");
                result = "\n\nError: ProtocolException " +
                        "(There is an error in the underlying protocol)";
                return result;
            } catch (IOException e) {
                publishProgress("Web Service Connection Status: Disconnected");
                result = "\n\nError: IOException (An I/O exception of some sort has occurred)";
                return result;
            } catch (JSONException e) {
                publishProgress("Web Service Connection Status: Disconnected");
                result = "\n\nError: JSONException (A problem with the JSON API has occurred)";
                return result;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // Check if cancelled
            if (isCancelled()) {
                return;
            }
            // Update and show web service connection status
            textView10.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // Display the text returned by the doInBackground() method
            textView11.setText(inputPostCode + result);
            // Enable "Send" and "Test API" after the retrieved process is finished
            findViewById(R.id.button14).setEnabled(true);
            findViewById(R.id.button17).setEnabled(true);
        }
    }
}
