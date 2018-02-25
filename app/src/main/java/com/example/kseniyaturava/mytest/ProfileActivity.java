package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

public class ProfileActivity extends AppCompatActivity {
    TabHost Tabs;
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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            break;
                        case R.id.searchItem:
                            // setTitle("Search");
                            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                            //startActivity(new Intent(MainActivity.this, SearchActivity.class));
                            break;
                        case R.id.formItem:
                            // setTitle("Form");
                            startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            // startActivity(new Intent(MainActivity.this, FormActivity.class));
                            break;
                        case R.id.notificationItem:
                            // setTitle("Notifications");
                            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            break;
                        case R.id.profileItem:
                            // setTitle("Profile");
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            break;
                    }
                    finish();
                    return true;
                }

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Profile");
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView );
        //Functionality of TabsHost here
        Tabs = (TabHost) findViewById(R.id.tabs); //llamamos al Tabhost
        Tabs.setup();  //lo activamos

        TabHost.TabSpec tab1 = Tabs.newTabSpec("tab2");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = Tabs.newTabSpec("tab1");

        tab1.setIndicator("Foros");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.tab2); //definimos el id de cada Tab (pestaña)

        tab2.setIndicator("Favoritas");
        tab2.setContent(R.id.tab1);



        Tabs.addTab(tab1); //añadimos los tabs ya programados
        Tabs.addTab(tab2);

        /*Tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < Tabs.getTabWidget().getChildCount(); i++) {
                    Tabs.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#FF0000")); // unselected
                }

                Tabs.getTabWidget().getChildAt(Tabs.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#0000FF")); // selected

            }
        });*/

        //Listeners on click list "foros" selected
        ImageView img_goForo = (ImageView) findViewById(R.id.img_GoForo);
        img_goForo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForoActivity.class));

            }
        });

        //Listeners on click list "Favoritos" selected
        ImageView img_goMovie = (ImageView) findViewById(R.id.img_favourite1);
        img_goMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));

            }
        });
    }
}
