package com.myexample.numad20su_yiwenfang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aboutNameEmail(View view) {
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText("Name: Dr. Yiwen Fang | Email: fang.yiw@husky.neu.edu");
    }

    public void educationEducation(View view) {
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText("Northeastern University 2019 - 2020\nM.S. in Computer Science");
    }

    public void grid3x2(View view) {
        Intent intent = new Intent(this, DisplayGridMessageActivity.class);
        startActivity(intent);
    }

    /**
     * Handle configuration changes including screen orientation
     * Code is cited from https://developer.android.com/guide/topics/resources/runtime-changes
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public void linkCollector(View view) {
        Intent intent = new Intent(this, LinkCollectorActivity.class);
        startActivity(intent);
    }

    public void displayLocation(View view) {
        Intent intent = new Intent(this, DisplayLocationActivity.class);
        startActivity(intent);
    }
}
