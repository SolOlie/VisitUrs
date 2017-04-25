package com.example.patrick.visiturs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {
    private Button btnMap, btnList;
    private Location l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("fejl 40", "Welcome");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        btnList = (Button)findViewById(R.id.btnList);

        btnMap = (Button)findViewById(R.id.btnMap);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView();
            }
        });

    }
    private void mapView()
    {
        Log.d("fejl 40", "Knappe tryk");
        Intent i = new Intent(this, MapMarker.class);
        startActivity(i);
    }
    private void listView()
    {
        Log.d("fejl 40", "List tryk");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}
