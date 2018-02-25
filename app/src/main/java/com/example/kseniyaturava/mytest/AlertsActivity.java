package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class AlertsActivity extends AppCompatActivity {
    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    //we are on the method when the menu's item is selected
                    //type inside the instructions TODO
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            //setTitle("Explore");//Set the title ActionBar
                            //instance Activity
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            // startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            return true;
                        case R.id.searchItem:
                            // setTitle("Search");
                            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                            //startActivity(new Intent(MainActivity.this, SearchActivity.class));
                            return true;
                        case R.id.formItem:
                            // setTitle("Form");
                            startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            // startActivity(new Intent(MainActivity.this, FormActivity.class));
                            return true;
                        case R.id.notificationItem:
                            // setTitle("My alerts");
                            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            return true;
                        case R.id.profileItem:
                            // setTitle("Profile");
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            return true;
                    }
                   // finish();
                    return false;
                }

            };

    private LinearLayout la1;
    private LinearLayout la2;
    private LinearLayout la4;
    private LinearLayout la5;
    private ImageButton im;
    private ImageButton im4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("My alerts");
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);

        la1=(LinearLayout) findViewById(R.id.layout_alert1);
        la2=(LinearLayout) findViewById(R.id.layout_alert2);
        la4=(LinearLayout) findViewById(R.id.layout_alert4);
        la5=(LinearLayout) findViewById(R.id.layout_alert5);
        im=(ImageButton) findViewById(R.id.button_comments);
        im4=(ImageButton) findViewById(R.id.button_comments4);

        la1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la1.setBackgroundColor(Color.rgb(218,236,241));
                im.setBackgroundColor(Color.rgb(218,236,241));
                Intent intent = new Intent(AlertsActivity.this, ForoActivity.class);
                startActivity(intent);
            }
        });

        la2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la2.setBackgroundColor(Color.rgb(218,236,241));
            }
        });

        la4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la4.setBackgroundColor(Color.rgb(218,236,241));
                im4.setBackgroundColor(Color.rgb(218,236,241));
                Intent intent = new Intent(AlertsActivity.this, ForoActivity.class);
                startActivity(intent);
            }
        });

        la5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la5.setBackgroundColor(Color.rgb(218,236,241));
            }
        });

    }

}
