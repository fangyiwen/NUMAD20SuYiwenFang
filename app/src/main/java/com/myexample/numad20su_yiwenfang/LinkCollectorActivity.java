package com.myexample.numad20su_yiwenfang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {
    // Two ArrayList to store name and URL of links, respectively
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listItemsUrl = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // fab's OnClickListener and onClick
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListItem();
            }
        });
    }

    @Override
    protected void onStart() {
        // Show a list of links
        super.onStart();
        myListView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        myListView.setAdapter(adapter);

        // User taps a link in the list to launch the URL in a web browser
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = listItemsUrl.get(position);
                // try-catch to avoid App crash due to invalid URL
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),
                            url, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid URL: " + url, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Show a new screen for the user to enter a name and a URL for the link
    private static final int request_code = 5;

    private void addListItem() {
        Intent intent = new Intent(this, InputLinkActivity.class);
        startActivityForResult(intent, request_code);
    }

    // Obtain and extract the returned data from the next activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == request_code) &&
                (resultCode == RESULT_OK)) {
            listItems.add(data.getExtras().getString("returnName"));
            listItemsUrl.add(data.getExtras().getString("returnUrl"));
            adapter.notifyDataSetChanged();
            Snackbar.make(findViewById(R.id.fab),
                    "The link was successfully created", Snackbar.LENGTH_SHORT).show();
        }
    }
}
