package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class SearchActivity extends AppCompatActivity {
    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

                    //we are on the method when the menu's item is selected
                    //type inside the instructions TODO
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            setTitle("Explore");//Set the title ActionBar
                            //instance Activity
                            startActivity(new Intent(SearchActivity.this, HomeActivity.class));
                            // startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                            break;
                        case R.id.searchItem:
                            setTitle("Search");
                            startActivity(new Intent(SearchActivity.this,SearchActivity.class));


                            //startActivity(new Intent(MainActivity.this, SearchActivity.class));
                            finish();
                            break;
                        case R.id.formItem:
                            setTitle("Form");
                            startActivity(new Intent(SearchActivity.this, FormActivity.class));

                            // startActivity(new Intent(MainActivity.this, FormActivity.class));
                            finish();
                            break;

                        case R.id.notificationItem:
                            setTitle("Notifications");
                            startActivity(new Intent(SearchActivity.this, AlertsActivity.class));
                            finish();
                            break;

                        case R.id.profileItem:
                            setTitle("Profile");
                            startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                            finish();

                            break;

                    }
                    //finish();
                    return true;
                }

            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("search.");

        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
        // Menu menu = BottomNavigationView.getMenu();
        //MenuItem menuItem = menu.getItem(1);
        //menuItem.setChecked(true);

    }
}
