
package com.example.leon_azraev.videostore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    public static int count1 = 0;
    public static int count2 = 0;
    public Button register_;
    public Button submit;
    public EditText user_name;
    public EditText password;
    private DatabaseReference userDB;
    private DatabaseReference userDB2;

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
                userDB2 =  FirebaseDatabase.getInstance().getReference("Manager");
                userDB2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user_name=(EditText) findViewById(R.id.user_name_);
                        password=(EditText) findViewById(R.id.password_);
                        String u=user_name.getText().toString();
                        String p=password.getText().toString();
                        count1 = 0;

                        for (DataSnapshot k:dataSnapshot.getChildren()) {

                            if (k.getKey().equals("userName")) {
                                if (u.equals(k.getValue().toString())) {
                                    count1++;
                                }
                            }
                            if (k.getKey().equals("password")) {
                                if (p.equals(k.getValue().toString())) {
                                    count1++;
                                }
                            }

                        }

                        if (count1 == 2) {
                            Intent intent = new Intent(Login_screen.this, homepageactivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if (count1 != 2) {
                    userDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User usr = new User();
                            user_name = (EditText) findViewById(R.id.user_name_);
                            password = (EditText) findViewById(R.id.password_);
                            String u = user_name.getText().toString();
                            String p = password.getText().toString();
                            count2 = 0;
                            for (DataSnapshot k : dataSnapshot.getChildren()) {
                                count2 = 0;
                                for (DataSnapshot d : k.getChildren()) {

                                    if (d.getKey().equals("userName")) {
                                        if (u.equals(d.getValue().toString())) {
                                            count2++;
                                        }
                                    }
                                    if (d.getKey().equals("password")) {
                                        if (p.equals(d.getValue().toString())) {
                                            count2++;
                                        }
                                    }
                                    if (count2 == 2) {
                                        for (DataSnapshot m : k.getChildren()) {
                                            if (m.getKey().equals("userName")) {
                                                usr.setUserName(m.getValue().toString());
                                            }
                                            if (m.getKey().equals("password")) {
                                                usr.setPassword(m.getValue().toString());
                                            }
                                            if (m.getKey().equals("firstName")) {
                                                usr.setFirstName(m.getValue().toString());
                                            }
                                            if (m.getKey().equals("lastName")) {
                                                usr.setLastName(m.getValue().toString());
                                            }
                                            if (m.getKey().equals("street")) {
                                                usr.setStreet(m.getValue().toString());
                                            }
                                            if (m.getKey().equals("city")) {
                                                usr.setCity(m.getValue().toString());
                                            }
                                            if (m.getKey().equals("email")) {
                                                usr.setEmail(m.getValue().toString());
                                            }

                                        }
                                        Intent intent = new Intent(Login_screen.this, HomePage.class);
                                        intent.putExtra("myUSER", usr);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                }
                                if (count2 == 2)
                                    break;
                            }
                            if (count2 != 2 && count1 != 2) {
                                Toast.makeText(Login_screen.this, "No such user in the system", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
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
