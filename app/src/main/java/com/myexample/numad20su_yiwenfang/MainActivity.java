package com.myexample.numad20su_yiwenfang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        textView3.setText("Columbia University 2020 - 2021\nM.S. in Computer Science\n\n" +
                "Northeastern University 2019 - 2020\nM.S. in Computer Science\n\n" +
                "University of Oxford 2013 - 2019\nPh.D. in Inorganic Chemistry\n\n" +
                "Fudan University 2009 - 2013\nB.Sc. in Biological Sciences");
    }
}
