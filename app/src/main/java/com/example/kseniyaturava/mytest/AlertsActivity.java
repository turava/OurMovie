package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AlertsActivity extends AppCompatActivity {
   private String user;
    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
                            intent0.putExtra("User", user);
                            startActivity(intent0);
                            return true;
                        case R.id.searchItem:
                            //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                            intent1.putExtra("User", user);
                            startActivity(intent1);
                            return true;
                        case R.id.formItem:
                            //startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            Intent intent2 = new Intent(getApplicationContext(), FormActivity.class);
                            intent2.putExtra("User", user);
                            startActivity(intent2);
                            return true;
                        case R.id.notificationItem:
                            //startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            Intent intent3 = new Intent(getApplicationContext(), AlertsActivity.class);
                            intent3.putExtra("User", user);
                            startActivity(intent3);
                            return true;
                        case R.id.profileItem:
                            // startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent4.putExtra("User", user);
                            startActivity(intent4);
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

        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }

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
