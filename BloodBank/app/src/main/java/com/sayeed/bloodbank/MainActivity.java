package com.sayeed.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import Utils.Common;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView nameuser, walletuser, review, network, plugins, myapps, mainmenus, pagesubtitle;

    private static int flag = 0;
    public static TextView pagetitle;

    String id;
    Uri downloadUrl;
    private int REQUEST_CODE =2;

    private StorageReference mStorage;
    Button btnguide;
    Animation atg, atgtwo, atgthree;
    ImageView imageView3;


    private DrawerLayout myDrawer;
//   private ActionBarDrawerToggle myToggle;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
   /* private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();

    String id1 = firebaseAuth.getCurrentUser().getUid();

    private DatabaseReference mEmailReference = mRootReference.child("Login Details").child(id1).child("Email");
    private DatabaseReference mUsernameReference = mRootReference.child("Login Details").child(id1).child("Username");*/

    ImageView profilepic;
    TextView username,email;

    DatabaseReference onlineusereference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MultiDex.install(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mStorage = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        id = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profile Url").child(id);

        onlineusereference = firebaseDatabase.getReference().child(Common.USER_INFORMATION).child(id);


       // username = (TextView) findViewById(R.id.username);
        //email = (TextView) findViewById(R.id.useremail);

        /*mEmailReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Username.setText(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUsernameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                email.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
        /*
        Username.setText(mUsernameReference.toString());
        email.setText(mEmailReference.toString());

       FirebaseUser user = firebaseAuth.getCurrentUser();*/

       /* */
        /*   */

        /*myToggle = new ActionBarDrawerToggle(this,myDrawer,R.string.open,R.string.close);

        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();
*/

        myDrawer = findViewById(R.id.myDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);
        username = headView.findViewById(R.id.username);
        email = headView.findViewById(R.id.useremail);
        profilepic = headView.findViewById(R.id.profilepic);

        if(user != null) {
            if (user.getDisplayName() != null)
                username.setText(user.getDisplayName().toString());
            if (user.getEmail() != null)
                email.setText(user.getEmail().toString());
        }


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, myDrawer, toolbar, R.string.open, R.string.close);
        myDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        imageView3 = findViewById(R.id.imageView3);

        pagetitle = findViewById(R.id.pagetitle);
        pagesubtitle = findViewById(R.id.pagesubtitle);

        btnguide = findViewById(R.id.btnguide);

        LinearLayout linear2 = findViewById(R.id.linearLayout2);

        linear2.startAnimation(atg);

        LinearLayout linear3 = findViewById(R.id.linearLayout3);

        imageView3.startAnimation(atg);
        linear3.startAnimation(atg);

        pagetitle.startAnimation(atgtwo);
        pagesubtitle.startAnimation(atgtwo);

        btnguide.startAnimation(atgthree);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() != null) {

                    downloadUrl = Uri.parse(dataSnapshot.getValue().toString());

                    Picasso.with(MainActivity.this).load(downloadUrl).into(profilepic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                pic.setImageBitmap(bitmap);
*/
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading . . .");
            progressDialog.show();



            StorageReference filepath = mStorage.child("Profile Pic").child(id);
            filepath.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(MainActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                            downloadUrl = taskSnapshot.getDownloadUrl();

                            databaseReference.setValue(downloadUrl.toString());
                            flag = 1;
                           //image = taskSnapshot.getDownloadUrl().toString();

                            Picasso.with(MainActivity.this).load(downloadUrl).into(profilepic);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploading " + (int) progress + "%");
                        }
                    });


        }
    }

    /*    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(myToggle.onOptionsItemSelected(item))
            {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    */
    private void closeDrawer() {
        myDrawer.closeDrawer(GravityCompat.START);
    }


    private void openDrawer() {
        myDrawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (myDrawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        }
        super.onBackPressed();
    }

    public void gologin(View view) {
        Intent loginintent = new Intent(this, LoginActivity.class);

        startActivity(loginintent);
    }

    public void gongo(View view) {
        Intent ngointent = new Intent(this, NGOActivity.class);

        startActivity(ngointent);
    }

    public void Attendance(View view) {
        Intent attendenceintent = new Intent(this, Attendance_Activity.class);

        startActivity(attendenceintent);
    }

    public void Game(View view) {
        Intent gameIntent = getPackageManager().getLaunchIntentForPackage("com.Uphaar.Runner");

        if (gameIntent != null) {
            startActivity(gameIntent);
        } else {
            Toast.makeText(getApplicationContext(), "App Not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void Teach(View view) {
        Intent teachintent = new Intent(this, TeachActivity.class);

        startActivity(teachintent);
    }

    public void Bar(View view) {
        Intent barintent = new Intent(this, BarGenActivity.class);

        startActivity(barintent);
    }

    /*public void gps(View view) {
        Intent gpsintent = new Intent(this, AllPeopleActivity.class);

        startActivity(gpsintent);
    }*/


    public void Register(View view) {
        Intent regintent = new Intent(this, TeacherActivity.class);

        startActivity(regintent);
    }

    public void Volunteer(View view) {
        Intent volinintent = new Intent(this, VolunteerActivity.class);

        startActivity(volinintent);
    }

    public void maps(View view) {
        Intent mapsintent = new Intent(this, AllPeopleActivity.class);

        startActivity(mapsintent);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // String itemName = (String) item.getTitle();


        //  Toast.makeText(getApplicationContext(), "GAAAAAAAAA333333333333333AAAME", Toast.LENGTH_SHORT).show();
        //    closeDrawer();

      /*  String itemName = (String) item.getTitle();

        closeDrawer();

        switch (item.getItemId()){
            case R.id.home:
                break;

            case R.id.register:
                Intent regintent = new Intent(this, TeacherActivity.class);

                startActivity(regintent);
                break;

            case R.id.ngo :
                Intent ngointent = new Intent(this, NGOActivity.class);

                startActivity(ngointent);
                break;

        }
        return true;*/
        int item1 = item.getItemId();

        switch (item1) {
            case R.id.home: {



                break;
            }
            case R.id.game: {

                Intent gameIntent = getPackageManager().getLaunchIntentForPackage("com.Uphaar.Runner");

                if (gameIntent != null) {
                    startActivity(gameIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "App Not Installed", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.register: {
                Intent regintent = new Intent(this, TeacherActivity.class);

                startActivity(regintent);
                break;
            }
            case R.id.attendance: {
                Intent attendenceintent = new Intent(this, Attendance_Activity.class);

                startActivity(attendenceintent);
                break;
            }
            case R.id.ngo: {
                Intent ngointent = new Intent(this, NGOActivity.class);

                startActivity(ngointent);
                break;
            }
            case R.id.volunteer: {

                Intent volinintent = new Intent(this, VolunteerActivity.class);

                startActivity(volinintent);
                break;
            }
            case R.id.logout: {

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent loginintent = new Intent(this, LoginActivity.class);

                onlineusereference.removeValue();
                loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginintent);
                break;
            }
            case R.id.about: {
                break;
            }


        }

        return true;
    }

    }

