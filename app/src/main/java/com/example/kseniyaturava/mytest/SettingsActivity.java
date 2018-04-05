package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout layout_redactProfile;
    private LinearLayout layout_changePass;
    private String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Configuración");

        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }
        //Eventos en layout
        layout_redactProfile = (LinearLayout) findViewById(R.id.layout_redactProfile);

        layout_redactProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsProfile.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        layout_changePass = (LinearLayout) findViewById(R.id.layout_changePass);
        layout_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsPassword.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });



    }
}
