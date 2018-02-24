package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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


    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ListView lv=(ListView)findViewById(R.id.listViewMovies);
        ArrayList<String> arrayMovies=new ArrayList<>();
        arrayMovies.addAll(Arrays.asList(getResources().getStringArray(R.array.array_movies)));
        lv.setBackgroundColor(Color.WHITE);
        lv.setCacheColorHint(Color.BLACK);


        adapter=new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,arrayMovies);
        lv.setAdapter(adapter);


        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Search");//Set the title ActionBar
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView );

        // Ensure correct menu item is selected
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);


    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item=menu.findItem(R.id.menuSearch);
        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
