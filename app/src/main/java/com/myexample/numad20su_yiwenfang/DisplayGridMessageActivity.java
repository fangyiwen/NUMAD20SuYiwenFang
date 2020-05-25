package com.myexample.numad20su_yiwenfang;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayGridMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_grid_message);
    }

    public void pressA(View view) {
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText("Pressed: A");
    }

    public void pressB(View view) {
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText("Pressed: B");
    }

    public void pressC(View view) {
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText("Pressed: C");
    }

    public void pressD(View view) {
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText("Pressed: D");
    }

    public void pressE(View view) {
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText("Pressed: E");
    }

    public void pressF(View view) {
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setText("Pressed: F");
    }
}
