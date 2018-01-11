
package com.example.leon_azraev.videostore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login_screen extends AppCompatActivity {
    public Button register_;
    public Button submit;
    public EditText user_name;
    public EditText password;
    private DatabaseReference userDB;

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
                userDB= FirebaseDatabase.getInstance().getReference("Users");
                userDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user_name=(EditText) findViewById(R.id.user_name_);
                        password=(EditText) findViewById(R.id.password_);
                        String u=user_name.getText().toString();
                        String p=password.getText().toString();
                        int count=0;
                        for (DataSnapshot d:dataSnapshot.getChildren()){
                            if(d.getKey().equals("userName")) {
                                if (u.equals(d.getValue().toString())) {
                                    count++;
                                }
                            }
                            if(d.getKey().equals("password")){
                                if (p.equals(d.getValue().toString())) {
                                    count++;
                                }
                            }
                        }
                        if(count==2){
                            Intent intent = new Intent(Login_screen.this, HomePage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Login_screen.this, "No such user in the system", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
