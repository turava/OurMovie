package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
                            // setTitle("Notifications");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Notifications");
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }
}
