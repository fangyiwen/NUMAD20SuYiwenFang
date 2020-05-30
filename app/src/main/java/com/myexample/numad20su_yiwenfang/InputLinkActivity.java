package com.myexample.numad20su_yiwenfang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_link);
    }

    String nameInput, urlInput;

    // Take user input and return the data to the parent activity
    public void enterNameUrl(View view) {
        EditText editTextName = findViewById(R.id.editText);
        EditText editTextUrl = findViewById(R.id.editText2);
        Button enter = findViewById(R.id.button11);
        nameInput = editTextName.getText().toString();
        urlInput = editTextUrl.getText().toString();

        // Check empty for name and URL input
        if (!nameInput.isEmpty() && !urlInput.isEmpty()) {
            // Returning data from the sub-activity
            Intent data = new Intent();
            data.putExtra("returnName", nameInput);
            data.putExtra("returnUrl", urlInput);
            setResult(RESULT_OK, data);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Name and URL can't be empty!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
