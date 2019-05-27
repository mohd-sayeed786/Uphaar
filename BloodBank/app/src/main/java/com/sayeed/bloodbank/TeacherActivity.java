package com.sayeed.bloodbank;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TeacherActivity extends AppCompatActivity {

    private EditText name, gender,age,address,quali,pref,clas,exp;


    private int REQUEST_CODE1 =1;
    private int REQUEST_CODE2 =2;

    private StorageReference mStorage;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        firebaseAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference().child("Teacher Documents");


        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        quali = findViewById(R.id.quali);
        pref = findViewById(R.id.are);
        clas = findViewById(R.id.classes);
        exp = findViewById(R.id.exp);

    }

    public void Second(View view)
    {
        if (name.getText().toString().equals("") && gender.getText().toString().equals("") && age.getText().toString().equals("") && address.getText().toString().equals("")&& quali.getText().toString().equals("") && pref.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "BLANK NOT ALLOWED", Toast.LENGTH_SHORT).show();

        } else {

            Intent intent = new Intent(getApplicationContext(),SecondActivity.class);

            startActivity(intent);
            Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

        }
    }

    public void Upload1(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE1);
    }
    public void Upload2(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE1 && resultCode == RESULT_OK && data !=null &&data.getData()!=null) {

            Uri uri = data.getData();
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                pic.setImageBitmap(bitmap);
*/
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading . . .");
            progressDialog.show();

            String id = firebaseAuth.getCurrentUser().getUid();

            StorageReference filepath = mStorage.child(id).child("Teacher Id");
            filepath.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(TeacherActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            String image = taskSnapshot.getDownloadUrl().toString();

                            // Picasso.with(VolunteerActivity.this).load(downloadUrl).into(pic);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(TeacherActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
           else if (requestCode == REQUEST_CODE2 && resultCode == RESULT_OK && data !=null &&data.getData()!=null)
            {

                Uri uri = data.getData();
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                pic.setImageBitmap(bitmap);
*/
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading . . .");
                progressDialog.show();

                String id = firebaseAuth.getCurrentUser().getUid();

                StorageReference filepath = mStorage.child(id).child("Teacher Qualifications");
                filepath.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();

                                Toast.makeText(TeacherActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                String image = taskSnapshot.getDownloadUrl().toString();

                                // Picasso.with(VolunteerActivity.this).load(downloadUrl).into(pic);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(TeacherActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
