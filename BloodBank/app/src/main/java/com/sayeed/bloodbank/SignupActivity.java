package com.sayeed.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Login;
    private TextView sup;
    private EditText mail;


    private FirebaseAuth auth = FirebaseAuth.getInstance();


    private FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference("Login Details");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /*Name = (EditText)findViewById(R.id.etname);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.button);*/

    //    sup = (TextView)findViewById(R.id.sup);
        sup = findViewById(R.id.sup);
        //fbook = findViewById(R.id.fboook);
        Login = findViewById(R.id.act);
        mail = findViewById(R.id.mal);
        Password = findViewById(R.id.pswd);
        Name = findViewById(R.id.usrusr);

        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // validate(Name.getText().toString(), Password.getText().toString());
                register();


            }
        });
    }

    private void register()
    {
        if(mail.getText().toString().equals("") || Password.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"BLANK NOT ALLOWED",Toast.LENGTH_SHORT).show();

        }
        else
        {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Signing Up . . .");
            progressDialog.show();
            String username = mail.getText().toString();
            String password = Password.getText().toString();
            auth.createUserWithEmailAndPassword(username,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String id = auth.getCurrentUser().getUid();

                                 DatabaseReference mEmailReference = mRootReference.child(id).child("Email");
                                 DatabaseReference mUsernameReference = mRootReference.child(id).child("Username");
                                 DatabaseReference mPasswordReference = mRootReference.child(id).child("Password");

                                String Email = mail.getText().toString();
                                mEmailReference.setValue(Email);

                                mRootReference.child("Latest Email").setValue(Email);

                                String Username = Name.getText().toString();
                                mUsernameReference.setValue(Username);

                                String Pass = Password.getText().toString();
                                mPasswordReference.setValue(Pass);

                                FirebaseUser user = auth.getCurrentUser();
                                if(user!= null)
                                {
                                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(Username)
                                            .build();

                                    user.updateProfile(userProfileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        //Toa
                                                        // st.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }

                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(),"User Registered Succesfully",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"User Could not be Created",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
   /* private void validate(String userName, String UserPassword){
        if((userName.equals("admin")) && (UserPassword.equals("1234")))
        {
            Intent intent = new Intent (SignupActivity.this,SecondActivity.class);
            startActivity(intent);
        }
        else
        {
            counter--;

            Info.setText("No. of Attempts Remaining : " + String.valueOf(counter));

            if(counter == 0)
            {
                Login.setEnabled(false);
            }

        }
    }
    */

    /*
    @Override
    protected void onStart() {
        super.onStart();

        mEmailReference.addValueEventListener(this);
        mUsernameReference.addValueEventListener(this);
        mPasswordReference.addValueEventListener(this);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if(dataSnapshot.getValue(String.class)!=null)
        {
            String key = dataSnapshot.getKey();

            if(key.equals("Email"))
            {
                String Email = dataSnapshot.getValue(String.class);
                mail.setText(Email);
            }
            else if(key.equals("Username"))
            {
                String Username = dataSnapshot.getValue(String.class);
                Name.setText(Username);
            }
            else if(key.equals("Password"))
            {
                String Pass = dataSnapshot.getValue(String.class);
                Password.setText(Pass);
            }
        }

    }*/

}
