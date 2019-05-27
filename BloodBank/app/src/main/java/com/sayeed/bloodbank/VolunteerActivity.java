package com.sayeed.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class VolunteerActivity extends AppCompatActivity {

    //private static final int CHOOSE_IMAGE = 101;
    private int REQUEST_CODE =2;

    private StorageReference mStorage;

    private EditText name, gender, age, address, pref,tim,joi,mobile;

  //  ProgressBar progressBar;
    Uri uri;

    String image;

    DatabaseReference voldatabase;

    FirebaseAuth firebaseAuth;

    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

       //progressBar = (ProgressBar)findViewById(R.id.bar);

        firebaseAuth = FirebaseAuth.getInstance();

        voldatabase = FirebaseDatabase.getInstance().getReference("Volunteer");

        //profile = (ImageView) findViewById(R.id.profilepic);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        mobile = findViewById(R.id.mob);
        address = findViewById(R.id.address);
        pref = findViewById(R.id.pref);
        tim = findViewById(R.id.time);
        joi = findViewById(R.id.join);

        mStorage = FirebaseStorage.getInstance().getReference();


     /*   profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showimagechooser();
            }
        });
*/

    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                profile.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadImageToFirebaseStorage()
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("VolunteerPics").child(id);

        if(uri != null)
        {
           // progressBar.setVisibility(View.VISIBLE);
            storageReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                           // progressBar.setVisibility(View.GONE);
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                          //  Picasso.with(VolunteerActivity.this).load(downloadUrl).into(profile);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // progressBar.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show();

                        }
                    });

        }


    }

    public void showimagechooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select com.sayeed.bloodbank.Profile Image"), CHOOSE_IMAGE);
    }
*/
    public void Second(View view) {

        String nam = name.getText().toString();
        String gen = gender.getText().toString();
        String ag = age.getText().toString();
        String addr = address.getText().toString();
        String mob = mobile.getText().toString();
        String pre = pref.getText().toString();
        String time = tim.getText().toString();
        String join = joi.getText().toString();

        if (nam.equals("") || gen.equals("") || ag.equals("") || addr.equals("") || mob.equals("") || pre.equals("") ||  time.equals("") || join.equals("")) {
            Toast.makeText(getApplicationContext(), "BLANK NOT ALLOWED", Toast.LENGTH_SHORT).show();

        } else {

            String id = firebaseAuth.getCurrentUser().getUid();

            VolunteerData volunteerData = new VolunteerData(id,nam,gen,ag,addr,mob,pre,time,join);

            voldatabase.child(id).setValue(volunteerData);

            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.putExtra("image",image);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

        }
    }

    public void UploadImage(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data !=null &&data.getData()!=null)
        {

            Uri uri = data.getData();
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                pic.setImageBitmap(bitmap);
*/
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading . . .");
            progressDialog.show();

            String id = firebaseAuth.getCurrentUser().getUid();

            StorageReference filepath = mStorage.child("Volunteer Id").child(id);
            filepath.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(VolunteerActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            image = taskSnapshot.getDownloadUrl().toString();

                            // Picasso.with(VolunteerActivity.this).load(downloadUrl).into(pic);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(VolunteerActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });


        }
    }
}
