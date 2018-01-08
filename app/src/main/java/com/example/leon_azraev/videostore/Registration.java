package com.example.leon_azraev.videostore;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.leon_azraev.videostore.R.layout.activity_registration;


public class Registration extends Activity {
    public Button submit;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;
    private EditText editTextUser;
    private EditText editTextPassword;
    private EditText editTextFN;
    private EditText editTextLN;
    private EditText editTextCity;
    private EditText editTextEmail;
    private EditText editTextStreet;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    DatabaseReference userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_registration);
        userDB = FirebaseDatabase.getInstance().getReference("Users");
         firebaseAuth = FirebaseAuth.getInstance();
        editTextUser = (EditText) findViewById(R.id.user_name);
        editTextPassword = (EditText) findViewById(R.id.password_);
        editTextFN = (EditText)findViewById(R.id.first_name);
        editTextLN = (EditText)findViewById(R.id.last_name);
        editTextCity = (EditText)findViewById(R.id.city);
        editTextEmail = (EditText)findViewById(R.id.email);
        editTextStreet = (EditText)findViewById(R.id.street);
        progressDialog = new ProgressDialog(this);
        Registration_to_Homepage();
        btnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }
    public void Registration_to_Homepage() {
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
                AddUserToDB();
            }
        });
    }
    private void AddUserToDB()
    {
        String UserName = editTextUser.getText().toString();
        String Password = editTextPassword.getText().toString();
        String FirstName = editTextFN.getText().toString();
        String LastName = editTextLN.getText().toString();
        String City = editTextCity.getText().toString();
        String Street = editTextStreet.getText().toString();
        String Email = editTextEmail.getText().toString();
        if(TextUtils.isEmpty(UserName))
        {
            Toast.makeText(this,"You must enter a user name!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Password))
        {
            Toast.makeText(this,"You must enter a Password!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(FirstName))
        {
            Toast.makeText(this,"You must enter a first name!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(LastName))
        {
            Toast.makeText(this,"You must enter a last name!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(City))
        {
            Toast.makeText(this,"You must enter a city!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Street))
        {
            Toast.makeText(this,"You must enter a street!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Email))
        {
            Toast.makeText(this,"You must enter a email!",Toast.LENGTH_LONG).show();
            return;
        }
        String id = userDB.push().getKey();
        User user = new  User(UserName,Password,Email,FirstName,LastName,City,Street);
        userDB.child(id).setValue(user);
        Intent intent = new Intent(Registration.this, HomePage.class);
        startActivity(intent);
        finish();
    }
    private void RegisterUser()
    {
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString().trim();
        progressDialog.setMessage("Register User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Registration.this,"Register Successful!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Registration.this,"Register Faild!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Registration.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                if (check_if_jpg_png(data) == true) {
                    onSelectFromGalleryResult(data);
                }
                else
                {
                    Toast.makeText(this, "Error: You must choose png or jpg type", Toast.LENGTH_LONG).show();

                }
            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;

        if (data != null) {
            //if(check_if_jpg_png(data)==true) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //}
        }

        ivImage.setImageBitmap(bm);
    }

    public boolean check_if_jpg_png(Intent result) {
        Uri selectedImageUri = result.getData();
        String s = selectedImageUri.getPath();

        if (Build.VERSION.SDK_INT < 11) {
            if (RealPathUtil.getRealPathFromURI_BelowAPI11(this,selectedImageUri).toLowerCase().endsWith("png") || RealPathUtil.getRealPathFromURI_BelowAPI11(this,selectedImageUri).toLowerCase().endsWith("jpg")) {
                // if(data1.getLastPathSegment().endsWith("png") || data1.getLastPathSegment().endsWith("jpg") || data1.getLastPathSegment().endsWith("PNG"))

                return true;
            }

            return false;
        } else if(Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT<= 19) {
            if (RealPathUtil.getRealPathFromURI_API11to19(this,selectedImageUri).toLowerCase().endsWith("png") || RealPathUtil.getRealPathFromURI_API11to19(this,selectedImageUri).toLowerCase().endsWith("jpg"))

            // if(data1.getLastPathSegment().endsWith("png") || data1.getLastPathSegment().endsWith("jpg") || data1.getLastPathSegment().endsWith("PNG"))
            {
                return true;
            }
        } else if(Build.VERSION.SDK_INT > 19){
            if (RealPathUtil.getRealPathFromURI_API20(this,selectedImageUri).toLowerCase().endsWith("png") || RealPathUtil.getRealPathFromURI_API20(this,selectedImageUri).toLowerCase().endsWith("jpg"))

            // if(data1.getLastPathSegment().endsWith("png") || data1.getLastPathSegment().endsWith("jpg") || data1.getLastPathSegment().endsWith("PNG"))
            {
                return true;
            }
        }
        return false;

    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private String getRealPathFromURI1(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
 /*   public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }
*/


/*    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String filePath = "";
        if (contentUri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(contentUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            return  filePath;
        }

    }*/


}

