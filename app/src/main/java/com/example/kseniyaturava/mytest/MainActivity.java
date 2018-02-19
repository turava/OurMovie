package com.example.kseniyaturava.mytest;
//import android.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


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
                   // startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                  // startActivity(new Intent(MainActivity.this, HomeActivity.class));

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
        setContentView(R.layout.activity_main);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Explore");
        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
       // Menu menu = BottomNavigationView.getMenu();
       // MenuItem menuItem = menu.getItem(0);
      //  menuItem.setChecked(true);


    }
}


        //When app starts this fragment will be desplayed by default

        //Errors here!
        //BottomNavigationView.setCheckedItem(R.id.bottomNavigationView);

        /*Fragment fragment = null;
        fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();*/

        /*setTitle("Start");//Set the title ActionBar
        HomeFragment FragmentExplore = new HomeFragment();
        FragmentTransaction fragmentTransactionExplore = getSupportFragmentManager().beginTransaction();
        fragmentTransactionExplore.replace(R.id.fragmentContainer, FragmentExplore, "Hola");
        fragmentTransactionExplore.commit();
*/


