package com.sayeed.bloodbank;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import Model.User;
import Utils.Common;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements ValueEventListener {

    private EditText emailid;
    private EditText pass;

    DatabaseReference user_information;
    private static final int MY_REQUEST_CODE = 7117;


    //private EditText act;

    private TextView fbook, sin;

    SignInButton signin;

    FirebaseAuth auth;

    GoogleSignInClient mGoogleSingInClient;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();

    private DatabaseReference mEmailReference = mRootReference.child("Login Details").child("Latest Email");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);

        //Init Firebase

        user_information = FirebaseDatabase.getInstance().getReference(Common.USER_INFORMATION);

        emailid = findViewById(R.id.mal);
        pass = findViewById(R.id.pswd);
        //act = findViewById(R.id.act);
        sin = findViewById(R.id.sin);
        //       sin = (TextView)findViewById(R.id.sin);



        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(it);
            }
        });

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signin = findViewById(R.id.googlebutton);

        mGoogleSingInClient = GoogleSignIn.getClient(this, gso);

       /* act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSingInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });

    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        }
    }*/

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {


        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            AddOnlineUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "User Logged in Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "User Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    public void gosignup(View view)
    {
        Intent signupintent = new Intent(this,SignupActivity.class);

        startActivity(signupintent);
    }

    public void Login(View view) {
        if (emailid.getText().toString().equals("") || pass.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "BLANK NOT ALLOWED", Toast.LENGTH_SHORT).show();

        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Logging in . . .");
            progressDialog.show();
            auth.signInWithEmailAndPassword(emailid.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "User logged in Succesfully", Toast.LENGTH_SHORT).show();

                                finish();

                                AddOnlineUser();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Incorrect Username Or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }

    public void AddOnlineUser()
    {
                //IdpResponse response = IdpResponse.fromResultIntent(data);

                        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                        user_information.orderByKey()
                                .equalTo(firebaseUser.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue() == null) {
                                            if (!dataSnapshot.child(firebaseUser.getUid()).exists()) {
                                                Common.loggedUser = new User(firebaseUser.getUid(), firebaseUser.getEmail());

                                                //DATABASE

                                                user_information.child(Common.loggedUser.getUid())
                                                        .setValue(Common.loggedUser);
                                            }
                                        } else {
                                            Common.loggedUser = dataSnapshot.child(firebaseUser.getUid()).getValue(User.class);
                                        }

                                      //  Paper.book().write(Common.USER_UID_SAVE_KEY, Common.loggedUser.getUid());
                                        //updateToken(firebaseUser);
                                       // setupUI();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

    }


   /* private void setupUI() {

        Intent mapsintent = new Intent(this, AllActivity.class);

        startActivity(mapsintent);


    }*/

    /*private void updateToken(final FirebaseUser firebaseUser) {

        final DatabaseReference tokens = FirebaseDatabase.getInstance()
                .getReference(Common.TOKENS);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {

                        tokens.child(firebaseUser.getUid())
                                .setValue(instanceIdResult.getToken());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() != null)
        {

            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        mEmailReference.addValueEventListener(this);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (dataSnapshot.getValue(String.class) != null) {
            String key = dataSnapshot.getKey();

            if (key.equals("Latest Email")) {
                String Email = dataSnapshot.getValue(String.class);
                emailid.setText(Email);
            }

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
