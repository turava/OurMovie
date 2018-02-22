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

import static android.view.View.*;


public class HomeActivity extends AppCompatActivity implements OnClickListener {
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
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
        //change the title of actionBar
        setTitle("OurMovie");
        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);
        //Popular listeners on images
        ImageView visorImatge = findViewById(R.id.imageView2);
        visorImatge.setOnClickListener(this);
        visorImatge.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));

            }
        });
        //News listeners on images
        ImageView img_new1 = (ImageView) findViewById(R.id.img_new1);
        img_new1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });
        ImageView img_new2 = (ImageView) findViewById(R.id.img_new2);
        img_new2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });
        ImageView img_new3 = (ImageView) findViewById(R.id.img_new3);
        img_new3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });


        //Categories listeners on images
        ImageView img_drama = (ImageView) findViewById(R.id.img_drama);
        img_drama.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));
            }
        });
        ImageView img_comedy = (ImageView) findViewById(R.id.img_comedy);
        img_comedy.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));

        }
        });
        ImageView img_fiction = (ImageView) findViewById(R.id.img_fiction);
        img_fiction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));

            }
        });
        ImageView img_horror = (ImageView) findViewById(R.id.img_horror);
        img_horror.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));

            }
        });

}

    @Override
    public void onClick(View view) {

    }
}
   /* @Override
    public void onClick(View v) {
        ImageView visorImatge = findViewById(R.id.imageView2);

       // Intent intent= new Intent(getApplicationContext() ,MovieActivity.class);
        startActivity(new Intent(getApplicationContext(), MovieActivity.class));
       // startActivity(intent);


    }
}*/
    /*     ImageView visorImatge = findViewById(R.id.imageView2);
            visorImatge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), MovieActivity.class));
                }
            });
    }*/