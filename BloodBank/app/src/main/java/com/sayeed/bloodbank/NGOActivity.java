package com.sayeed.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NGOActivity extends AppCompatActivity {

    private EditText name, gender, age, address, quali, pref, clas, exp;
    DatabaseReference Ngodatabase;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo);



        firebaseAuth = FirebaseAuth.getInstance();
        Ngodatabase = FirebaseDatabase.getInstance().getReference("NGO");
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        quali = findViewById(R.id.ngo);
        pref = findViewById(R.id.are);
        clas = findViewById(R.id.classes);
        exp = findViewById(R.id.exp);
    }


    public void Second(View view) {

        String nam = name.getText().toString();
        String gen = gender.getText().toString();
        String ag = age.getText().toString();
        String addr = address.getText().toString();
        String qual = quali.getText().toString();
        String pre = pref.getText().toString();
        String cla = clas.getText().toString();
        String ex = exp.getText().toString();

        if (nam.equals("") || gen.equals("") || ag.equals("") || addr.equals("") || qual.equals("") || pre.equals("") ||  ex.equals("") ||  cla.equals("")) {
            Toast.makeText(getApplicationContext(), "BLANK NOT ALLOWED", Toast.LENGTH_SHORT).show();

        } else {

            String id = firebaseAuth.getCurrentUser().getUid();

            Ngodata ngo = new Ngodata(id,nam,gen,ag,addr,qual,pre,cla,ex);

            Ngodatabase.child(id).setValue(ngo);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
            Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

        }
    }

}
