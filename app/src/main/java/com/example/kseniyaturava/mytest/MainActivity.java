package com.example.kseniyaturava.mytest;
//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    /*
      @author Kseniyaa Turava
     */
    //Menu & Activities code here
    //method Listener

    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
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
        setContentView(R.layout.activity_home);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Explore");
        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView );


    }
}




