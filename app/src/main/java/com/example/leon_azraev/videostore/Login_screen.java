package com.example.leon_azraev.videostore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Login_screen extends AppCompatActivity {
    public Button register_;
    public Button submit;
    public void Login_screen_to_Registration() {
        register_ = findViewById(R.id.register_);
        register_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_screen.this, Registration.class);
                startActivity(intent);
            }
        });
    }
    public void Login_screen_to_Homepage() {
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_screen.this, HomePage.class);
                startActivity(intent);
            }
        });
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Login_screen_to_Registration();
        Login_screen_to_Homepage();
    }
}




