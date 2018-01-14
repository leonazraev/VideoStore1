package com.example.leon_azraev.videostore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HomePage extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 1;
    public Button button2;
    public Button read_me;
    ImageView img;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        HomePage_to_MapsActivity();
        Intent i=getIntent();
        User usr=(User)i.getParcelableExtra("myUSER");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
//        StorageReference spaceRef = storageRef.child("Photos/"+usr.getUserName()+".png");


        TextView t1 = (TextView) findViewById(R.id.usrNameTxt);
        TextView t2 = (TextView) findViewById(R.id.firstNameTxt);
        TextView t3 = (TextView) findViewById(R.id.lastNameTxt);
        TextView t4 = (TextView) findViewById(R.id.emailTxt);
        TextView t5 = (TextView) findViewById(R.id.streetTxt);
        TextView t6 = (TextView) findViewById(R.id.cityTxt);
        img = (ImageView) findViewById(R.id.imageView2);


        storageRef.child("Photos/"+usr.getUserName()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                Picasso.with(HomePage.this).load(uri).fit().centerCrop().into(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        t1.setText(usr.getUserName());
        t2.setText(usr.getFirstName());
        t3.setText(usr.getPassword());
        t4.setText(usr.getEmail());
        t5.setText(usr.getStreet());
        t6.setText(usr.getCity());


    }

    public void HomePage_to_MapsActivity() {
        button2 = findViewById(R.id.button2);
        read_me = findViewById(R.id.read_me);
        read_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, ReadMe.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // requestPermission();
                Intent intent = new Intent(HomePage.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(HomePage.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
            ActivityCompat.requestPermissions(HomePage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}
