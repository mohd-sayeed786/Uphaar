package com.sayeed.bloodbank;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.core.view.View;
import com.google.firebase.messaging.FirebaseMessaging;
import android.view.View;

import java.util.ArrayList;

public class TeachActivity extends AppCompatActivity {

    MyAdapter adapter;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Profile> list;

    EditText classname,date,time,location;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    LinearLayout linear1;


    Button addclass;

    Boolean stat;
    String clas,dat,tim,locat;
    TextView add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);

        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addclass = findViewById(R.id.addclass);
        add = findViewById(R.id.add);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        linear1 = findViewById(R.id.linear1);

        classname = findViewById(R.id.classname);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);

        //check = (CheckBox) findViewById(R.id.check);



        reference = FirebaseDatabase.getInstance().getReference().child("Classes");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                       // Toast.makeText(TeachActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void Show(View view)
    {
        linear1.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<Profile>();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Profile p = dataSnapshot1.getValue(Profile.class);
                    list.add(p);
                }

                adapter = new MyAdapter(TeachActivity.this,list);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(TeachActivity.this,"Error ",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void Update(View view)
    {
        if(user.getEmail().toString().equals("mohdsayeed711@gmail.com"))
        {
            recyclerView.setVisibility(View.INVISIBLE);
            linear1.setVisibility(View.VISIBLE);

            addclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clas = classname.getText().toString();
                    dat = date.getText().toString();
                    tim = time.getText().toString();
                    locat = location.getText().toString();
                    stat = false;

                    if(clas.equals("") || dat.equals("") || tim.equals("") || locat.equals("")) {

                        Toast.makeText(getApplicationContext(), "BLANK NOT ALLOWED", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Profile profile = new Profile(clas, dat, tim, locat,stat);
                        reference.child(clas).setValue(profile);
                        linear1.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Class Added", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "This Option is Only Available for Admins", Toast.LENGTH_LONG).show();
        }



    }

}

